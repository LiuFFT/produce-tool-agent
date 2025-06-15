package com.example.agent.tool.deployment;

import lombok.Data;

/**
 * 模型打包工具的参数类
 */
@Data
public class ModelPackagingParams {
    
    /**
     * 模型路径
     */
    private String modelPath;
    
    /**
     * 目标格式
     */
    private String targetFormat;
    
    /**
     * 目标环境
     */
    private String targetEnvironment;
    
    /**
     * 是否优化性能
     */
    private Boolean optimizeForPerformance;
} 