package com.example.agent.agent;

import com.example.agent.config.AgentConfig;
import com.example.agent.config.AgentPrompts;
import com.example.agent.tool.ToolRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 样本标注Agent实现
 */
@Component
public class SampleAnnotationAgent extends BaseReActAgent {
    
    private static final String AGENT_NAME = "sampleAnnotation";
    private static final String AGENT_DESCRIPTION = "负责对获取的样本进行标注";
    private static final List<String> AVAILABLE_TOOLS = Arrays.asList(
            "sampleAnnotation",
            "terminate"
    );
    
    /**
     * 构造函数
     */
    public SampleAnnotationAgent(ChatClient chatClient,
                            AgentConfig agentConfig,
                            ToolRegistry toolRegistry) {
        super(AGENT_NAME, 
              AGENT_DESCRIPTION, 
              AgentPrompts.SAMPLE_ANNOTATION_PROMPT,
              AVAILABLE_TOOLS,
              chatClient,
              agentConfig,
              toolRegistry);
    }
    
    @Override
    protected String getTaskDescription() {
        return "对获取的数据样本进行标注。请确保标注的准确性和一致性，并处理边缘情况。";
    }
} 