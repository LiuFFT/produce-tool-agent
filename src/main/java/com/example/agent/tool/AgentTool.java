package com.example.agent.tool;

import org.springframework.ai.chat.model.ToolContext;

/**
 * Agent工具接口
 */
public interface AgentTool {
    
    /**
     * 获取工具名称
     * @return 工具名称
     */
    String getName();
    
    /**
     * 获取工具描述
     * @return 工具描述
     */
    String getDescription();
    
    /**
     * 获取工具参数定义
     * @return JSON格式的参数定义
     */
    String getParameterSchema();
    
    /**
     * 执行工具
     * @param input 输入参数（JSON格式）
     * @param toolContext 工具上下文
     * @return 执行结果
     */
    ToolExecutionResult execute(String input, ToolContext toolContext);
    
    /**
     * 获取工具状态
     * @return 工具状态描述
     */
    String getStatus();
} 