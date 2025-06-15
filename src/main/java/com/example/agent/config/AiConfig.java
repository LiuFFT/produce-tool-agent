package com.example.agent.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Spring AI配置类
 */
@Configuration
public class AiConfig {

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    @Value("${spring.ai.openai.model}")
    private String model;

    /**
     * 配置ChatModel
     */
    @Bean
    public ChatModel chatModel() {
        OpenAiApi openAiApi = OpenAiApi.builder().apiKey(openAiApiKey).baseUrl(baseUrl).build();
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder().model(model).temperature(0.7).build();
        return OpenAiChatModel.builder().openAiApi(openAiApi).defaultOptions(openAiChatOptions).build();
    }

    /**
     * 配置聊天内存
     */
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(100)
                .build();
    }

    /**
     * 配置ChatClient
     */
    @Bean
    @Primary
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultOptions(DashScopeChatOptions.builder()
                        .withInternalToolExecutionEnabled(true)
                        .build())
                .build();
    }
} 