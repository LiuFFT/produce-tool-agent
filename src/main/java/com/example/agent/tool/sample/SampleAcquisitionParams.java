package com.example.agent.tool.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 样本获取工具参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleAcquisitionParams {
    
    /**
     * 通用输入字段
     */
    private String input;
    
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