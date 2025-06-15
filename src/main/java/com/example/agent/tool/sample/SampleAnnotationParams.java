package com.example.agent.tool.sample;

import lombok.Data;

/**
 * 样本标注工具的参数类
 */
@Data
public class SampleAnnotationParams {
    
    /**
     * 数据路径
     */
    private String dataPath;
    
    /**
     * 标注方法（自动、人工等）
     */
    private String method;
    
    /**
     * 标签集合
     */
    private String[] labelSet;
    
    /**
     * 标注置信度阈值
     */
    private Double confidence;
} 