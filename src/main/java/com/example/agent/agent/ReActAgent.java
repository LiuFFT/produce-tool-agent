package com.example.agent.agent;

/**
 * ReAct Agent接口
 * 实现思考(Reasoning)和行动(Acting)交替执行的Agent
 */
public interface ReActAgent {
    
    /**
     * 获取Agent名称
     * @return Agent名称
     */
    String getName();
    
    /**
     * 获取Agent描述
     * @return Agent描述
     */
    String getDescription();
    
    /**
     * 执行一个完整的思考-行动周期
     * @return 执行结果
     */
    AgentExecutionResult executeStep();
    
    /**
     * 一次性执行所有步骤直到完成或出错
     * @param input 输入内容
     * @param context 执行上下文
     * @return 执行结果
     */
    default AgentExecutionResult execute(String input, String context) {
        // 可以根据输入和上下文进行初始化
        
        // 执行直到完成或出错
        AgentExecutionResult result;
        do {
            result = executeStep();
        } while (result.getState() == AgentState.IN_PROGRESS);
        
        return result;
    }
    
    /**
     * 获取当前Agent状态
     * @return Agent状态
     */
    AgentState getState();
    
    /**
     * 设置Agent状态
     * @param state 要设置的状态
     */
    void setState(AgentState state);
    
    /**
     * 重置Agent状态
     */
    void reset();
} 