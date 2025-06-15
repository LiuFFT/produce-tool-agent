package com.example.agent.tool;

import lombok.Getter;

/**
 * 工具执行结果
 */
@Getter
public class ToolExecutionResult {
    
    /**
     * 是否成功
     */
    private final boolean success;
    
    /**
     * 结果数据
     */
    private final String result;
    
    /**
     * 错误消息
     */
    private final String errorMessage;
    
    /**
     * 创建成功结果
     * @param result 结果数据
     * @return 成功的执行结果
     */
    public static ToolExecutionResult success(String result) {
        return new ToolExecutionResult(true, result, null);
    }
    
    /**
     * 创建失败结果
     * @param errorMessage 错误消息
     * @return 失败的执行结果
     */
    public static ToolExecutionResult failure(String errorMessage) {
        return new ToolExecutionResult(false, null, errorMessage);
    }
    
    private ToolExecutionResult(boolean success, String result, String errorMessage) {
        this.success = success;
        this.result = result;
        this.errorMessage = errorMessage;
    }
} 