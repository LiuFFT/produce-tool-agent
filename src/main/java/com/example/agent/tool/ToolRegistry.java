package com.example.agent.tool;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具注册器
 */
@Component
@Slf4j
public class ToolRegistry {
    
    private final Map<String, AgentToolCallback> toolCallbacks = new HashMap<>();
    
    @Autowired
    private ApplicationContext applicationContext;
    
    /**
     * 初始化方法，自动扫描并注册所有工具
     */
    @PostConstruct
    public void init() {
        log.info("开始自动扫描并注册工具");
        Map<String, AgentTool> toolBeans = applicationContext.getBeansOfType(AgentTool.class);
        
        for (AgentTool tool : toolBeans.values()) {
            registerTool(tool);
        }
        
        log.info("工具注册完成，共注册了 {} 个工具", toolCallbacks.size());
    }
    
    /**
     * 注册工具
     * @param tool 工具实例
     */
    public void registerTool(AgentTool tool) {
        String name = tool.getName();
        log.info("注册工具: {}", name);
        
        SimpleToolDefinition toolDefinition = new SimpleToolDefinition(
                name,
                tool.getDescription(),
                tool.getParameterSchema()
        );
        
        AgentToolCallback callback = new AgentToolCallback(tool, toolDefinition);
        
        toolCallbacks.put(name, callback);
    }
    
    /**
     * 获取所有工具回调
     * @return 工具回调列表
     */
    public List<ToolCallback> getAllToolCallbacks() {
        return new ArrayList<>(toolCallbacks.values());
    }
    
    /**
     * 获取指定工具回调
     * @param toolNames 工具名称列表
     * @return 工具回调列表
     */
    public List<ToolCallback> getToolCallbacks(List<String> toolNames) {
        List<ToolCallback> callbacks = new ArrayList<>();
        
        if (toolNames == null || toolNames.isEmpty()) {
            return callbacks;
        }
        
        for (String name : toolNames) {
            AgentToolCallback callback = toolCallbacks.get(name);
            if (callback != null) {
                callbacks.add(callback);
            } else {
                log.warn("工具 {} 未注册", name);
            }
        }
        
        return callbacks;
    }
} 