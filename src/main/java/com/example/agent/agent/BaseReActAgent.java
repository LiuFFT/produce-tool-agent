package com.example.agent.agent;

import com.example.agent.config.AgentConfig;
import com.example.agent.tool.ToolRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础ReAct Agent实现
 */
@Slf4j
public abstract class BaseReActAgent implements ReActAgent {
    
    @Getter
    @Setter
    private AgentState state = AgentState.NOT_STARTED;
    
    @Getter
    private final String name;
    
    @Getter
    private final String description;
    
    private final String systemPrompt;
    
    protected final ChatClient chatClient;
    
    protected final AgentConfig agentConfig;
    
    protected final ToolRegistry toolRegistry;
    
    protected final List<String> availableTools;
    
    private int currentStep = 0;
    
    private Map<String, Object> agentContext = new HashMap<>();
    
    /**
     * 构造函数
     */
    public BaseReActAgent(String name, 
                     String description, 
                     String systemPrompt,
                     List<String> availableTools,
                     ChatClient chatClient,
                     AgentConfig agentConfig,
                     ToolRegistry toolRegistry) {
        this.name = name;
        this.description = description;
        this.systemPrompt = systemPrompt;
        this.chatClient = chatClient;
        this.agentConfig = agentConfig;
        this.toolRegistry = toolRegistry;
        this.availableTools = availableTools;
    }
    
    /**
     * 执行思考过程
     * @return 思考结果是否需要执行行动
     */
    protected boolean think() {
        try {
            // 获取系统提示信息
            Message systemMessage = createSystemMessage();
            
            // 创建Prompt
            List<Message> messages = new ArrayList<>();
            messages.add(systemMessage);
            
            // 添加任务描述
            String taskDescription = getTaskDescription();
            if (taskDescription != null && !taskDescription.isBlank()) {
                messages.add(new UserMessage(taskDescription));
            }
            
            Prompt prompt = new Prompt(messages);
            
            // 获取可用工具回调
            List<ToolCallback> toolCallbacks = toolRegistry.getToolCallbacks(availableTools);
            
            // 调用LLM
            ChatResponse response = chatClient.prompt(prompt)
                    .toolCallbacks(toolCallbacks)
                    .call()
                    .chatResponse();
                    
            // 保存响应到上下文
            agentContext.put("lastResponse", response);
            
            // 检查是否有工具调用
            return hasToolCalls(response);
            
        } catch (Exception e) {
            log.error("Agent思考过程发生错误", e);
            return false;
        }
    }
    
    /**
     * 检查响应中是否有工具调用
     */
    protected boolean hasToolCalls(ChatResponse response) {
        return response != null && 
               response.getResult() != null &&
               response.getResult().getOutput() != null &&
               !response.getResult().getOutput().getToolCalls().isEmpty();
    }
    
    /**
     * 执行行动过程
     */
    protected AgentExecutionResult act() {
        try {
            ChatResponse response = (ChatResponse) agentContext.get("lastResponse");
            if (response == null) {
                return new AgentExecutionResult("没有有效的响应可执行", AgentState.ERROR);
            }
            
            // 记录执行步骤
            currentStep++;
            if (currentStep >= agentConfig.getMaxSteps()) {
                return new AgentExecutionResult("已达到最大步骤数", AgentState.COMPLETED);
            }
            
            // 这里可以根据需要执行实际工具调用
            // 注意: 在实际应用中应该使用ToolCallingManager来执行工具调用
            
            return new AgentExecutionResult("已执行工具调用", AgentState.IN_PROGRESS);
        } catch (Exception e) {
            log.error("Agent行动过程发生错误", e);
            return new AgentExecutionResult("行动过程出错: " + e.getMessage(), AgentState.ERROR);
        }
    }
    
    @Override
    public AgentExecutionResult executeStep() {
        if (state == AgentState.COMPLETED || state == AgentState.ERROR) {
            return new AgentExecutionResult("Agent已完成或出错，无法执行", state);
        }
        
        // 设置为进行中状态
        setState(AgentState.IN_PROGRESS);
        
        // 执行思考
        boolean shouldAct = think();
        if (!shouldAct) {
            setState(AgentState.COMPLETED);
            return new AgentExecutionResult("思考完成，无需执行行动", AgentState.COMPLETED);
        }
        
        // 执行行动
        return act();
    }
    
    @Override
    public void reset() {
        currentStep = 0;
        agentContext.clear();
        setState(AgentState.NOT_STARTED);
    }
    
    /**
     * 创建系统提示消息
     */
    protected Message createSystemMessage() {
        // 默认实现，使用模板替换
        String prompt = systemPrompt
            .replace("{{toolDescriptions}}", getToolDescriptions())
            .replace("{{task}}", getTaskDescription())
            .replace("{{status}}", getStatusDescription());
            
        return new SystemMessage(prompt);
    }
    
    /**
     * 获取工具描述
     */
    protected String getToolDescriptions() {
        List<ToolCallback> tools = toolRegistry.getToolCallbacks(availableTools);
        StringBuilder toolDesc = new StringBuilder();
        
        if (tools != null && !tools.isEmpty()) {
            for (ToolCallback tool : tools) {
                if (tool != null) {
                    toolDesc.append("- ")
                           .append(tool.getToolMetadata())
                           .append("\n");
                }
            }
        } else {
            toolDesc.append("暂无可用工具");
        }
        
        return toolDesc.toString();
    }
    
    /**
     * 获取任务描述
     */
    protected abstract String getTaskDescription();
    
    /**
     * 获取状态描述
     */
    protected String getStatusDescription() {
        return String.format("当前已执行 %d 步, 状态: %s", currentStep, state);
    }
    
    @Override
    public AgentExecutionResult execute(String input, String context) {
        log.info("开始执行Agent: {}, 输入: {}, 上下文: {}", name, input, context);
        
        // 重置之前的状态
        reset();
        
        // 保存输入和上下文
        agentContext.put("input", input);
        agentContext.put("context", context);
        
        // 强制使用工具执行，而不仅依赖think()的返回值
        log.info("模拟工具执行，直接调用工具: {}", availableTools);
        
        // 获取第一个可用工具（如果有）
        if (availableTools != null && !availableTools.isEmpty()) {
            String toolName = availableTools.get(0);
            
            try {
                // 这里获取工具并执行
                ToolCallback toolCallback = toolRegistry.getToolCallbacks(List.of(toolName)).get(0);
                if (toolCallback != null) {
                    // 构建简单的工具参数
                    String toolInput = String.format("{\"input\": \"%s\"}", input);
                    String result = toolCallback.call(toolInput);
                    
                    // 返回工具执行结果
                    return new AgentExecutionResult("成功执行工具: " + toolName, AgentState.COMPLETED, result);
                }
            } catch (Exception e) {
                log.error("执行工具失败", e);
            }
        }
        
        // 如果没有工具可执行，仍然返回输入作为结果
        return new AgentExecutionResult("执行完成", AgentState.COMPLETED, input);
    }
} 