package com.example.agent.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 模型生产请求
 * 包含模型生产所需的各种参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionRequest {
    
    /**
     * 模型类型，如"文本分类"、"图像识别"等
     */
    private String modelType;
    
    /**
     * 生产目标，描述模型生产的具体目标
     */
    private String objective;
    
    /**
     * 数据需求，描述样本获取的具体要求
     */
    private String dataRequirements;
    
    /**
     * 训练参数，如批量大小、学习率等
     */
    private Map<String, Object> trainingParameters;
    
    /**
     * 评估标准，如精度、召回率等
     */
    private Map<String, Object> evaluationCriteria;
    
    /**
     * 部署环境，如"生产环境"、"测试环境"等
     */
    private String deploymentEnvironment;
    
    /**
     * 是否需要在线监控
     */
    private boolean requiresMonitoring;
} 