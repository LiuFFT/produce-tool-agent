package com.example.agent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import lombok.Data;

/**
 * Agent配置类
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "agent")
@Data
public class AgentConfig {
    
    /**
     * 模型配置
     */
    private ModelConfig model = new ModelConfig();
    
    /**
     * 最大步骤数
     */
    private int maxSteps = 20;
    
    /**
     * 模型配置
     */
    @Data
    public static class ModelConfig {
        /**
         * 模型名称
         */
        private String name = "qwen-max";
        
        /**
         * 模型温度参数
         */
        private float temperature = 0.7f;
    }
} 