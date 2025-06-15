package com.example.agent.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;

/**
 * 抽象Agent工具基类
 */
@Slf4j
public abstract class AbstractAgentTool implements AgentTool {
    
    protected final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 获取工具参数类型
     * @return 参数类型的Class对象
     */
    public abstract Class<?> getParameterType();
    
    /**
     * 执行具体的工具逻辑
     * @param params 解析后的参数对象
     * @return 执行结果
     */
    protected abstract ToolExecutionResult executeInternal(Object params);
    
    @Override
    public ToolExecutionResult execute(String input, ToolContext toolContext) {
        try {
            // 解析输入参数
            Object params = objectMapper.readValue(input, getParameterType());
            
            // 执行工具逻辑
            return executeInternal(params);
        } catch (Exception e) {
            log.error("工具执行错误: {}", e.getMessage(), e);
            return ToolExecutionResult.failure("执行失败: " + e.getMessage());
        }
    }
    
    @Override
    public String getStatus() {
        return "工具状态正常";
    }
} 