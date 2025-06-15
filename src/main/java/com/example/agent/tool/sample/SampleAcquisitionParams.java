package com.example.agent.tool.sample;

import lombok.Data;

/**
 * 样本获取工具的参数类
 */
@Data
public class SampleAcquisitionParams {
    
    /**
     * 数据源类型（公开数据集、API、爬虫等）
     */
    private String sourceType;
    
    /**
     * 数据源地址或名称
     */
    private String source;
    
    /**
     * 样本数量
     */
    private Integer sampleCount;
    
    /**
     * 数据格式
     */
    private String format;
    
    /**
     * 保存路径
     */
    private String savePath;
} 