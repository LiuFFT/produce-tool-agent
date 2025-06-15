package com.example.agent.tool.training;

import lombok.Data;

import java.util.Map;

/**
 * 模型训练工具的参数类
 */
@Data
public class ModelTrainingParams {
    
    /**
     * 数据集路径
     */
    private String datasetPath;
    
    /**
     * 模型类型
     */
    private String modelType;
    
    /**
     * 学习率
     */
    private Double learningRate;
    
    /**
     * 批量大小
     */
    private Integer batchSize;
    
    /**
     * 训练轮数
     */
    private Integer epochs;
    
    /**
     * 其他训练参数
     */
    private Map<String, Object> additionalParams;
} 