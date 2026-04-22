package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.service.IOptimizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/algorithm")
public class OptimizationAlgorithmController extends BaseController {

    private final IOptimizationService optimizationService;

    @PostMapping("/lp")
    public R<Map<String, Object>> solveLP(@RequestBody Map<String, Object> params) {
        double[] c = (double[]) params.get("c");
        double[][] A = (double[][]) params.get("A");
        double[] b = (double[]) params.get("b");
        boolean minimize = (Boolean) params.getOrDefault("minimize", true);
        return R.ok(optimizationService.solveLinearProgramming(c, A, b, minimize));
    }

    @PostMapping("/ga")
    public R<Map<String, Object>> solveGA(@RequestBody Map<String, Object> params) {
        double[] c = (double[]) params.get("c");
        double[][] A = (double[][]) params.get("A");
        double[] b = (double[]) params.get("b");
        int populationSize = (Integer) params.getOrDefault("populationSize", 50);
        int maxIterations = (Integer) params.getOrDefault("maxIterations", 100);
        double crossoverRate = (Double) params.getOrDefault("crossoverRate", 0.8);
        double mutationRate = (Double) params.getOrDefault("mutationRate", 0.1);
        boolean minimize = (Boolean) params.getOrDefault("minimize", true);
        return R.ok(optimizationService.solveGeneticAlgorithm(c, A, b, populationSize, maxIterations, crossoverRate, mutationRate, minimize));
    }

    @PostMapping("/pso")
    public R<Map<String, Object>> solvePSO(@RequestBody Map<String, Object> params) {
        double[] c = (double[]) params.get("c");
        double[][] A = (double[][]) params.get("A");
        double[] b = (double[]) params.get("b");
        int swarmSize = (Integer) params.getOrDefault("swarmSize", 30);
        int maxIterations = (Integer) params.getOrDefault("maxIterations", 100);
        double inertia = (Double) params.getOrDefault("inertia", 0.7);
        double c1 = (Double) params.getOrDefault("c1", 1.5);
        double c2 = (Double) params.getOrDefault("c2", 1.5);
        boolean minimize = (Boolean) params.getOrDefault("minimize", true);
        return R.ok(optimizationService.solvePSO(c, A, b, swarmSize, maxIterations, inertia, c1, c2, minimize));
    }

    @PostMapping("/milp")
    public R<Map<String, Object>> solveMILP(@RequestBody Map<String, Object> params) {
        double[] c = (double[]) params.get("c");
        int[] integerVars = (int[]) params.get("integerVars");
        double[][] A = (double[][]) params.get("A");
        double[] b = (double[]) params.get("b");
        boolean minimize = (Boolean) params.getOrDefault("minimize", true);
        return R.ok(optimizationService.solveMILP(c, integerVars, A, b, minimize));
    }
}
