package com.example.agent.agent;

import com.example.agent.config.AgentConfig;
import com.example.agent.config.AgentPrompts;
import com.example.agent.tool.ToolRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 模型部署Agent实现
 */
@Component
public class ModelDeploymentAgent extends BaseReActAgent {
    
    private static final String AGENT_NAME = "modelDeployment";
    private static final String AGENT_DESCRIPTION = "负责将训练好的模型部署到生产环境";
    private static final List<String> AVAILABLE_TOOLS = Arrays.asList(
            "modelPackaging",
            "modelDeployment",
            "modelMonitoring",
            "terminate"
    );
    
    /**
     * 构造函数
     */
    public ModelDeploymentAgent(ChatClient chatClient,
                           AgentConfig agentConfig,
                           ToolRegistry toolRegistry) {
        super(AGENT_NAME, 
              AGENT_DESCRIPTION, 
              AgentPrompts.MODEL_DEPLOYMENT_PROMPT,
              AVAILABLE_TOOLS,
              chatClient,
              agentConfig,
              toolRegistry);
    }
    
    @Override
    protected String getTaskDescription() {
        return "将训练好的模型部署到生产环境。请确保模型正确打包，选择合适的部署策略，并设置必要的监控。";
    }
} 