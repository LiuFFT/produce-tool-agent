package com.example.agent.tool.training;

import lombok.Data;

/**
 * 模型评估工具的参数类
 */
@Data
public class ModelEvaluationParams {
    
    /**
     * 模型路径
     */
    private String modelPath;
    
    /**
     * 测试数据路径
     */
    private String testDataPath;
    
    /**
     * 要计算的评估指标
     */
    private String[] metrics;
    
    /**
     * 是否保存评估报告
     */
    private Boolean saveReport;
} 