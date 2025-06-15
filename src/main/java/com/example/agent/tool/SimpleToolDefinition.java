package com.example.agent.tool;

import org.springframework.ai.tool.definition.ToolDefinition;

/**
 * 简单工具定义实现
 */
public class SimpleToolDefinition implements ToolDefinition {
    
    private final String name;
    private final String description;
    private final String inputSchema;
    
    /**
     * 构造函数
     * @param name 工具名称
     * @param description 工具描述
     * @param inputSchema 输入参数schema
     */
    public SimpleToolDefinition(String name, String description, String inputSchema) {
        this.name = name;
        this.description = description;
        this.inputSchema = inputSchema;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    @Override
    public String description() {
        return description;
    }
    
    @Override
    public String inputSchema() {
        return inputSchema;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
 