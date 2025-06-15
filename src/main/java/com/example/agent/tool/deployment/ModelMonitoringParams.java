package com.example.agent.tool.deployment;

import lombok.Data;

/**
 * 模型监控工具的参数类
 */
@Data
public class ModelMonitoringParams {
    
    /**
     * 部署ID
     */
    private String deploymentId;
    
    /**
     * 监控时长（分钟）
     */
    private Integer durationMinutes;
    
    /**
     * 要监控的指标
     */
    private String[] metrics;
} 