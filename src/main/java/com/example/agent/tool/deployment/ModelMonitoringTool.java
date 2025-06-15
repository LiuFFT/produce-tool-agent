package com.example.agent.tool.deployment;

import com.example.agent.tool.AbstractAgentTool;
import com.example.agent.tool.ToolExecutionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 模型监控工具
 * 负责监控已部署模型的性能和状态
 */
@Slf4j
@Component
public class ModelMonitoringTool extends AbstractAgentTool {

    private static final String TOOL_NAME = "modelMonitoring";
    private static final String TOOL_DESCRIPTION = "监控已部署模型的性能、请求量、延迟等指标，支持设置告警规则。";
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private String name;
    private String description;
    
    public ModelMonitoringTool() {
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
            + "\"deploymentId\": {\"type\": \"string\"},"
            + "\"durationMinutes\": {\"type\": \"integer\"},"
            + "\"metrics\": {\"type\": \"array\", \"items\": {\"type\": \"string\"}}"
            + "},"
            + "\"required\": [\"deploymentId\"]"
            + "}";
    }

    @Override
    public Class<?> getParameterType() {
        return ModelMonitoringParams.class;
    }
    
    @Override
    protected ToolExecutionResult executeInternal(Object params) {
        log.info("开始执行模型监控: {}", params);
        
        try {
            ModelMonitoringParams monitoringParams = (ModelMonitoringParams) params;
            
            // 获取部署ID
            String deploymentId = monitoringParams.getDeploymentId();
            if (deploymentId == null) {
                deploymentId = "deploy-default";
            }
            log.info("监控部署ID: {}", deploymentId);
            
            // 获取监控时间段
            int durationMinutes = monitoringParams.getDurationMinutes() != null 
                ? monitoringParams.getDurationMinutes() : 30;
            log.info("监控时长: {} 分钟", durationMinutes);
            
            // 模拟监控过程
            log.info("开始收集监控数据...");
            simulateMonitoring(durationMinutes);
            
            // 生成监控数据
            Map<String, Object> monitoringData = generateMonitoringData(deploymentId);
            
            // 返回监控结果
            StringBuilder result = new StringBuilder(String.format(
                "模型监控完成。部署ID: %s，监控时间: %s 分钟\n\n", 
                deploymentId, durationMinutes
            ));
            
            result.append("### 性能指标\n");
            Map<String, Object> metrics = (Map<String, Object>) monitoringData.get("metrics");
            metrics.forEach((k, v) -> {
                if (v instanceof Double) {
                    result.append(String.format("- %s: %.4f\n", k, v));
                } else {
                    result.append(String.format("- %s: %s\n", k, v));
                }
            });
            
            result.append("\n### 健康状态\n");
            result.append(String.format("- 状态: %s\n", monitoringData.get("status")));
            result.append(String.format("- 上线时间: %s\n", monitoringData.get("uptime")));
            result.append(String.format("- 实例数: %s\n", monitoringData.get("replicas")));
            
            result.append("\n### 告警\n");
            if (RANDOM.nextDouble() > 0.7) {
                result.append("- 告警: 请求延迟超过阈值 (P99 > 500ms)\n");
            } else {
                result.append("- 无告警\n");
            }
            
            return ToolExecutionResult.success(result.toString());
            
        } catch (Exception e) {
            log.error("模型监控失败", e);
            return ToolExecutionResult.failure("模型监控失败: " + e.getMessage());
        }
    }
    
    private void simulateMonitoring(int duration) {
        int steps = Math.min(duration, 10); // 最多10个步骤
        
        for (int i = 0; i < steps; i++) {
            try {
                int progress = (i + 1) * 100 / steps;
                Thread.sleep(300);
                log.info("监控进度 {}%: 收集数据中 ({}/{})", progress, i + 1, steps);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private Map<String, Object> generateMonitoringData(String deploymentId) {
        Map<String, Object> data = new HashMap<>();
        
        // 生成基础信息
        data.put("deploymentId", deploymentId);
        data.put("timestamp", LocalDateTime.now().format(FORMATTER));
        data.put("status", RANDOM.nextDouble() > 0.9 ? "警告" : "正常");
        data.put("uptime", String.format("%dh %dm", RANDOM.nextInt(240), RANDOM.nextInt(60)));
        data.put("replicas", 2 + RANDOM.nextInt(3));
        
        // 生成性能指标
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("请求总数", 10000 + RANDOM.nextInt(90000));
        metrics.put("平均延迟(ms)", 50 + RANDOM.nextInt(200));
        metrics.put("P95延迟(ms)", 100 + RANDOM.nextInt(400));
        metrics.put("P99延迟(ms)", 200 + RANDOM.nextInt(800));
        metrics.put("吞吐量(rps)", 10 + RANDOM.nextInt(90));
        metrics.put("错误率(%)", RANDOM.nextDouble() * 2.0);
        metrics.put("CPU使用率(%)", 20 + RANDOM.nextInt(60));
        metrics.put("内存使用率(%)", 30 + RANDOM.nextInt(50));
        metrics.put("在线准确率", 0.85 + RANDOM.nextDouble() * 0.1);
        
        data.put("metrics", metrics);
        
        return data;
    }
} 