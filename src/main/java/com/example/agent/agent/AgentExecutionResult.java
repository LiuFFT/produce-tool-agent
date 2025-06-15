package com.example.agent.agent;

import lombok.Getter;
import lombok.Setter;

/**
 * Agent执行结果
 */
@Getter
public class AgentExecutionResult {
    
    /**
     * 结果消息
     */
    private final String message;
    
    /**
     * Agent状态
     */
    private final AgentState state;
    
    /**
     * 输出内容
     */
    @Setter
    private String output;
    
    /**
     * 构造函数
     * @param message 结果消息
     * @param state Agent状态
     */
    public AgentExecutionResult(String message, AgentState state) {
        this.message = message;
        this.state = state;
    }
    
    /**
     * 构造函数（带输出）
     * @param message 结果消息
     * @param state Agent状态
     * @param output 输出内容
     */
    public AgentExecutionResult(String message, AgentState state, String output) {
        this.message = message;
        this.state = state;
        this.output = output;
    }
    
    /**
     * 检查执行是否成功
     * @return 如果状态是COMPLETED，则返回true，否则返回false
     */
    public boolean isSuccess() {
        return this.state == AgentState.COMPLETED;
    }
    
    /**
     * 获取输出内容，如果没有设置输出，则返回消息
     * @return 输出内容或消息
     */
    public String getOutput() {
        return output != null ? output : message;
    }
} 