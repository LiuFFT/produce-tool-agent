package com.example.agent.tool;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * Agent工具回调实现
 */
@Getter
public class AgentToolCallback implements ToolCallback {
    
    private final AgentTool tool;
    private final ToolDefinition toolDefinition;
    private final ToolMetadata toolMetadata;
    
    /**
     * 构造函数
     * @param tool Agent工具
     * @param toolDefinition 工具定义
     */
    public AgentToolCallback(AgentTool tool, ToolDefinition toolDefinition) {
        this.tool = tool;
        this.toolDefinition = toolDefinition;
        this.toolMetadata = new ToolMetadata() {
            public String getName() {
                return tool.getName();
            }
            
            public String getDescription() {
                return tool.getDescription();
            }
        };
    }
    
    @Override
    public ToolDefinition getToolDefinition() {
        return toolDefinition;
    }
    
    @Override
    public ToolMetadata getToolMetadata() {
        return toolMetadata;
    }
    
    /**
     * 实现ToolCallback接口的call方法
     * @param toolCall 工具调用
     * @return 工具调用的结果
     */
    @NotNull
    @Override
    public String call(String toolCall) {
        return execute(toolCall, null);
    }
    
    /**
     * 实现ToolCallback接口的call方法（带参数）
     * @param toolCall 工具调用
     * @return 工具调用的结果
     */
    @Override
    public String call(String toolCall, ToolContext toolContext) {
        return execute(toolCall, toolContext.getContext());
    }

    /**
     * 执行工具
     * @param input 输入参数
     * @param parameters 附加参数
     * @return 执行结果
     */
    public String execute(String input, Map<String, Object> parameters) {
        // 构建工具上下文
        Map<String, Object> contextMap = parameters != null ? parameters : new HashMap<>();
        ToolContext toolContext = new ToolContext(contextMap);
        
        // 执行工具
        ToolExecutionResult result = tool.execute(input, toolContext);
        
        // 返回结果
        if (result.isSuccess()) {
            return result.getResult();
        } else {
            return "执行失败: " + result.getErrorMessage();
        }
    }
    
    /**
     * 报告工具执行进度
     * @param progress 进度值（0-100）
     * @param message 进度消息
     */
    public void onProgress(int progress, String message) {
        // 实际应用中可以将进度通知发送给调用方
        // 这里只是一个空实现
    }
} 