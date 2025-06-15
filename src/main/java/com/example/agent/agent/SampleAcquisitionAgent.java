package com.example.agent.agent;

import com.example.agent.config.AgentConfig;
import com.example.agent.config.AgentPrompts;
import com.example.agent.tool.ToolRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 样本获取Agent实现
 */
@Component
public class SampleAcquisitionAgent extends BaseReActAgent {
    
    private static final String AGENT_NAME = "sampleAcquisition";
    private static final String AGENT_DESCRIPTION = "负责获取机器学习模型训练所需的样本数据";
    private static final List<String> AVAILABLE_TOOLS = Arrays.asList(
            "sampleAcquisition",
            "terminate"
    );
    
    /**
     * 构造函数
     */
    public SampleAcquisitionAgent(ChatClient chatClient,
                              AgentConfig agentConfig,
                              ToolRegistry toolRegistry) {
        super(AGENT_NAME, 
              AGENT_DESCRIPTION, 
              AgentPrompts.SAMPLE_ACQUISITION_PROMPT,
              AVAILABLE_TOOLS,
              chatClient,
              agentConfig,
              toolRegistry);
    }
    
    @Override
    protected String getTaskDescription() {
        return "获取机器学习项目所需的数据样本。请选择合适的数据源，并确保获取的数据质量符合要求。";
    }
} 