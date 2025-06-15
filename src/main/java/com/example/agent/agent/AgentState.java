package com.example.agent.agent;

/**
 * Agent状态枚举
 */
public enum AgentState {
    /**
     * 未启动
     */
    NOT_STARTED,
    
    /**
     * 进行中
     */
    IN_PROGRESS,
    
    /**
     * 已完成
     */
    COMPLETED,
    
    /**
     * 出错
     */
    ERROR
} 