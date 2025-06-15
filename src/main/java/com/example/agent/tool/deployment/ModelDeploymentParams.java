package com.example.agent.tool.deployment;

import lombok.Data;

import java.util.Map;

/**
 * 模型部署工具的参数类
 */
@Data
public class ModelDeploymentParams {
    
    /**
     * 模型包路径
     */
    private String packagePath;
    
    /**
     * 部署环境（生产、测试等）
     */
    private String environment;
    
    /**
     * 部署配置
     */
    private Map<String, Object> config;
} 