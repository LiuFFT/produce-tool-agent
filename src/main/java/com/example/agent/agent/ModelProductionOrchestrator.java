package com.example.agent.agent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 模型生产流程协调器
 * 负责协调各个Agent的工作流程，确保模型生产顺利进行
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ModelProductionOrchestrator {

    private final SampleAcquisitionAgent sampleAcquisitionAgent;
    private final SampleAnnotationAgent sampleAnnotationAgent;
    private final ModelTrainingAgent modelTrainingAgent;
    private final ModelDeploymentAgent modelDeploymentAgent;
    
    /**
     * 执行完整的模型生产流程
     * @param request 生产请求，包含模型类型、数据需求等信息
     * @return 生产结果，包含各阶段执行状态和最终模型信息
     */
    public ProductionResult executeProduction(ProductionRequest request) {
        log.info("开始执行模型生产流程，请求：{}", request);
        ProductionResult result = new ProductionResult();
        
        // 1. 样本获取
        log.info("阶段1：开始样本获取");
        AgentExecutionResult sampleResult = sampleAcquisitionAgent.execute(
            request.getDataRequirements(),
            createContext("数据获取", request)
        );
        result.setSampleAcquisitionResult(sampleResult);
        
        if (!sampleResult.isSuccess()) {
            log.error("样本获取失败，终止生产流程");
            return result;
        }
        
        // 2. 样本标注
        log.info("阶段2：开始样本标注");
        AgentExecutionResult annotationResult = sampleAnnotationAgent.execute(
            sampleResult.getOutput(),
            createContext("样本标注", request)
        );
        result.setSampleAnnotationResult(annotationResult);
        
        if (!annotationResult.isSuccess()) {
            log.error("样本标注失败，终止生产流程");
            return result;
        }
        
        // 3. 模型训练与评估
        log.info("阶段3：开始模型训练与评估");
        AgentExecutionResult trainingResult = modelTrainingAgent.execute(
            annotationResult.getOutput(),
            createContext("模型训练", request)
        );
        result.setModelTrainingResult(trainingResult);
        
        if (!trainingResult.isSuccess()) {
            log.error("模型训练失败，终止生产流程");
            return result;
        }
        
        // 4. 模型部署
        log.info("阶段4：开始模型部署");
        AgentExecutionResult deploymentResult = modelDeploymentAgent.execute(
            trainingResult.getOutput(),
            createContext("模型部署", request)
        );
        result.setModelDeploymentResult(deploymentResult);
        
        // 设置最终结果
        result.setSuccess(deploymentResult.isSuccess());
        if (deploymentResult.isSuccess()) {
            result.setDeployedModelInfo(deploymentResult.getOutput());
            log.info("模型生产流程成功完成，模型已部署");
        } else {
            log.error("模型部署失败，生产流程未完全成功");
        }
        
        return result;
    }
    
    /**
     * 创建Agent执行上下文
     */
    private String createContext(String stage, ProductionRequest request) {
        return String.format("阶段：%s，模型类型：%s，目标：%s", 
            stage, request.getModelType(), request.getObjective());
    }
} 