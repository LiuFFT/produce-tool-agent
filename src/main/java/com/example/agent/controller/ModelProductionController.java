package com.example.agent.controller;

import com.example.agent.agent.ModelProductionOrchestrator;
import com.example.agent.agent.ProductionRequest;
import com.example.agent.agent.ProductionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 模型生产控制器
 * 提供模型生产相关的REST API
 */
@Slf4j
@RestController
@RequestMapping("/api/production")
@RequiredArgsConstructor
public class ModelProductionController {
    
    private final ModelProductionOrchestrator orchestrator;
    
    /**
     * 启动模型生产流程
     * @param request 生产请求
     * @return 生产结果
     */
    @PostMapping("/start")
    public ResponseEntity<ProductionResult> startProduction(@RequestBody ProductionRequest request) {
        log.info("收到模型生产请求: {}", request);
        
        ProductionResult result = orchestrator.executeProduction(request);
        
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    /**
     * 查询生产状态
     * @param productionId 生产ID
     * @return 生产状态
     */
    @GetMapping("/{productionId}/status")
    public ResponseEntity<String> getProductionStatus(@PathVariable String productionId) {
        log.info("查询生产状态: {}", productionId);
        // 实际项目中会查询数据库或缓存获取状态
        return ResponseEntity.ok("生产中");
    }
    
    /**
     * 取消生产流程
     * @param productionId 生产ID
     * @return 取消结果
     */
    @PostMapping("/{productionId}/cancel")
    public ResponseEntity<String> cancelProduction(@PathVariable String productionId) {
        log.info("取消生产流程: {}", productionId);
        // 实际项目中会发送取消信号并更新状态
        return ResponseEntity.ok("已取消生产流程");
    }
    
    /**
     * 系统健康检查
     * @return 健康状态
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("系统正常运行");
    }
} 