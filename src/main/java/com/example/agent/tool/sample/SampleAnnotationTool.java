package com.example.agent.tool.sample;

import com.example.agent.tool.AbstractAgentTool;
import com.example.agent.tool.ToolExecutionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 样本标注工具
 * 负责对原始数据进行标注
 */
@Slf4j
@Component
public class SampleAnnotationTool extends AbstractAgentTool {

    private static final String TOOL_NAME = "sampleAnnotation";
    private static final String TOOL_DESCRIPTION = "对获取的样本数据进行标注，支持自动标注和人工标注流程。";
    
    private String name;
    private String description;
    
    public SampleAnnotationTool() {
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
            + "\"dataPath\": {\"type\": \"string\"},"
            + "\"method\": {\"type\": \"string\"},"
            + "\"labelSet\": {\"type\": \"array\", \"items\": {\"type\": \"string\"}},"
            + "\"confidence\": {\"type\": \"number\"}"
            + "},"
            + "\"required\": [\"dataPath\"]"
            + "}";
    }

    @Override
    public Class<?> getParameterType() {
        return SampleAnnotationParams.class;
    }
    
    @Override
    protected ToolExecutionResult executeInternal(Object params) {
        log.info("开始执行样本标注: {}", params);
        
        try {
            SampleAnnotationParams annotParams = (SampleAnnotationParams) params;
            
            // 获取数据路径
            String dataPath = annotParams.getDataPath();
            if (dataPath == null) {
                dataPath = "data/default";
            }
            log.info("数据路径: {}", dataPath);
            
            // 获取标注方式
            String annotationMethod = annotParams.getMethod();
            if (annotationMethod == null) {
                annotationMethod = "automatic";
            }
            log.info("标注方式: {}", annotationMethod);
            
            // 获取标签集
            String[] labelSet = annotParams.getLabelSet();
            if (labelSet == null) {
                labelSet = new String[]{"正面", "负面", "中性"};
            }
            log.info("标签集: {}", String.join(", ", labelSet));
            
            // 模拟样本标注过程
            log.info("开始标注样本...");
            simulateAnnotation();
            
            // 标注结果保存路径
            String savePath = String.format("data/annotated/%s", System.currentTimeMillis());
            
            // 返回标注结果
            int totalSamples = 1000;
            int annotatedSamples = 950;
            return ToolExecutionResult.success(String.format(
                "样本标注完成。共标注 %d/%d 条样本，标注准确率：%.2f，标注结果保存路径：%s",
                annotatedSamples, totalSamples, 0.97, savePath
            ));
            
        } catch (Exception e) {
            log.error("样本标注失败", e);
            return ToolExecutionResult.failure("样本标注失败: " + e.getMessage());
        }
    }
    
    private void simulateAnnotation() {
        String[] stages = {
            "准备数据...",
            "预处理样本...",
            "应用标注模型...",
            "人工审核边缘案例...",
            "汇总结果..."
        };
        
        for (int i = 0; i < stages.length; i++) {
            try {
                int progress = (i + 1) * 100 / stages.length;
                Thread.sleep(700);
                log.info("标注阶段 {}/{}: {}, 进度: {}%", i + 1, stages.length, stages[i], progress);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
} 