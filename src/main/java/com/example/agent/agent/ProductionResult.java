package com.example.agent.agent;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 模型生产结果
 * 包含模型生产流程的各阶段结果和最终部署信息
 */
@Data
@NoArgsConstructor
public class ProductionResult {
    
    /**
     * 整体生产流程是否成功
     */
    private boolean success;
    
    /**
     * 生产开始时间
     */
    private LocalDateTime startTime = LocalDateTime.now();
    
    /**
     * 生产结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 样本获取结果
     */
    private AgentExecutionResult sampleAcquisitionResult;
    
    /**
     * 样本标注结果
     */
    private AgentExecutionResult sampleAnnotationResult;
    
    /**
     * 模型训练结果
     */
    private AgentExecutionResult modelTrainingResult;
    
    /**
     * 模型部署结果
     */
    private AgentExecutionResult modelDeploymentResult;
    
    /**
     * 部署模型信息，包含模型版本、部署位置等
     */
    private String deployedModelInfo;
    
    /**
     * 模型评估指标
     */
    private String evaluationMetrics;
    
    /**
     * 生产过程中的关键日志
     */
    private String productionLogs;
    
    /**
     * 获取生产总耗时（秒）
     */
    public long getTotalDuration() {
        if (endTime == null) {
            endTime = LocalDateTime.now();
        }
        return java.time.Duration.between(startTime, endTime).getSeconds();
    }
} 