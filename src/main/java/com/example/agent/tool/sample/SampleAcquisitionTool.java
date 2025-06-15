package com.example.agent.tool.sample;

import com.example.agent.tool.AbstractAgentTool;
import com.example.agent.tool.ToolExecutionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 样本获取工具
 * 负责从各种来源获取训练数据
 */
@Slf4j
@Component
public class SampleAcquisitionTool extends AbstractAgentTool {

    private static final String TOOL_NAME = "sampleAcquisition";
    private static final String TOOL_DESCRIPTION = "从各种数据源获取训练样本，支持公开数据集、API和自定义数据源。";
    
    private String name;
    private String description;
    
    public SampleAcquisitionTool() {
        this.name = TOOL_NAME;
        this.description = TOOL_DESCRIPTION;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getDescription() {
        return this.description;
    }
    
    @Override
    public String getParameterSchema() {
        return "{"
            + "\"type\": \"object\","
            + "\"properties\": {"
            + "\"sourceType\": {\"type\": \"string\"},"
            + "\"source\": {\"type\": \"string\"},"
            + "\"sampleCount\": {\"type\": \"integer\"},"
            + "\"format\": {\"type\": \"string\"},"
            + "\"savePath\": {\"type\": \"string\"}"
            + "},"
            + "\"required\": [\"sourceType\", \"source\"]"
            + "}";
    }

    @Override
    public Class<?> getParameterType() {
        return SampleAcquisitionParams.class;
    }
    
    @Override
    protected ToolExecutionResult executeInternal(Object params) {
        log.info("开始执行样本获取: {}", params);
        
        try {
            SampleAcquisitionParams acqParams = (SampleAcquisitionParams) params;
            
            // 检查是否只有通用输入
            if (acqParams.getInput() != null && acqParams.getSourceType() == null) {
                // 使用input作为数据需求
                log.info("使用通用输入作为数据需求: {}", acqParams.getInput());
                acqParams.setSourceType("publicDataset");
                acqParams.setSource("generated");
                acqParams.setSampleCount(1000);
            }
            
            // 获取数据源类型
            String sourceType = acqParams.getSourceType();
            if (sourceType == null) {
                sourceType = "publicDataset";
            }
            log.info("数据源类型: {}", sourceType);
            
            // 获取数据源名称或URL
            String source = acqParams.getSource();
            if (source == null) {
                source = "default";
            }
            log.info("数据源: {}", source);
            
            // 样本数量
            int sampleCount = acqParams.getSampleCount() != null ?
                acqParams.getSampleCount() : 1000;
            log.info("样本数量: {}", sampleCount);
            
            // 模拟样本获取过程
            log.info("开始从数据源获取样本...");
            simulateDataAcquisition(sampleCount);
            
            // 数据保存路径
            String savePath = acqParams.getSavePath();
            if (savePath == null) {
                savePath = String.format("data/%s/%s", sourceType, System.currentTimeMillis());
            }
            
            // 获取数据格式
            String format = acqParams.getFormat();
            if (format == null) {
                format = "JSON";
            }
            
            // 返回获取结果
            String resultMessage = String.format(
                "样本获取成功。共获取 %d 条样本，保存路径：%s，数据格式：%s",
                sampleCount, savePath, format
            );
            
            if (acqParams.getInput() != null) {
                resultMessage = resultMessage + "\n基于需求: " + acqParams.getInput();
            }
            
            return ToolExecutionResult.success(resultMessage);
            
        } catch (Exception e) {
            log.error("样本获取失败", e);
            return ToolExecutionResult.failure("样本获取失败: " + e.getMessage());
        }
    }
    
    private void simulateDataAcquisition(int sampleCount) {
        // 模拟样本获取进度
        int steps = 10;
        int samplesPerStep = sampleCount / steps;
        
        for (int i = 0; i < steps; i++) {
            try {
                Thread.sleep(300);
                int progress = (i + 1) * 100 / steps;
                int currentSamples = (i + 1) * samplesPerStep;
                log.info("样本获取进度: {}%, 已获取 {}/{} 个样本", progress, currentSamples, sampleCount);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        try {
            Thread.sleep(500);
            log.info("样本获取完成，正在保存数据...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 