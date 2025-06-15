package com.example.agent.agent;

import com.example.agent.config.AgentConfig;
import com.example.agent.config.AgentPrompts;
import com.example.agent.tool.ToolRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 模型训练与评测Agent实现
 */
@Component
public class ModelTrainingAgent extends BaseReActAgent {
    
    private static final String AGENT_NAME = "modelTraining";
    private static final String AGENT_DESCRIPTION = "负责训练机器学习模型并评估其性能";
    private static final List<String> AVAILABLE_TOOLS = Arrays.asList(
            "modelTraining",
            "modelEvaluation",
            "terminate"
    );
    
    /**
     * 构造函数
     */
    public ModelTrainingAgent(ChatClient chatClient,
                          AgentConfig agentConfig,
                          ToolRegistry toolRegistry) {
        super(AGENT_NAME, 
              AGENT_DESCRIPTION, 
              AgentPrompts.MODEL_TRAINING_PROMPT,
              AVAILABLE_TOOLS,
              chatClient,
              agentConfig,
              toolRegistry);
    }
    
    @Override
    protected String getTaskDescription() {
        return "训练机器学习模型并评估其性能。请选择合适的模型架构、超参数，并通过评估指标验证模型质量。";
    }
} 