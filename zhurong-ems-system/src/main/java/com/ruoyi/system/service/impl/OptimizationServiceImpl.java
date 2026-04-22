package com.ruoyi.system.service.impl;

import com.ruoyi.system.algorithm.OptimizationAlgorithm;
import com.ruoyi.system.algorithm.OptimizationAlgorithm.LinearProgrammingResult;
import com.ruoyi.system.algorithm.OptimizationAlgorithm.GeneticAlgorithmResult;
import com.ruoyi.system.algorithm.OptimizationAlgorithm.PSOResult;
import com.ruoyi.system.service.IOptimizationService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class OptimizationServiceImpl implements IOptimizationService {

    @Override
    public Map<String, Object> solveLinearProgramming(double[] c, double[][] A, double[] b, boolean minimize) {
        Map<String, Object> result = new HashMap<>();
        try {
            LinearProgrammingResult lpResult = OptimizationAlgorithm.solveLP(c, A, b, minimize);
            result.put("objectiveValue", lpResult.objectiveValue);
            result.put("solution", lpResult.solution);
            result.put("feasible", lpResult.feasible);
            result.put("message", lpResult.message);
            result.put("algorithm", "LP");
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "LP求解失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> solveGeneticAlgorithm(double[] c, double[][] A, double[] b,
                                                      int populationSize, int maxIterations,
                                                      double crossoverRate, double mutationRate, boolean minimize) {
        Map<String, Object> result = new HashMap<>();
        try {
            GeneticAlgorithmResult gaResult = OptimizationAlgorithm.solveGA(c, A, b,
                    populationSize, maxIterations, crossoverRate, mutationRate, minimize);
            result.put("bestFitness", gaResult.bestFitness);
            result.put("bestSolution", gaResult.bestSolution);
            result.put("iterations", gaResult.iterations);
            result.put("executionTime", gaResult.executionTime);
            result.put("algorithm", "GA");
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "GA求解失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> solvePSO(double[] c, double[][] A, double[] b,
                                         int swarmSize, int maxIterations,
                                         double inertia, double c1, double c2, boolean minimize) {
        Map<String, Object> result = new HashMap<>();
        try {
            PSOResult psoResult = OptimizationAlgorithm.solvePSO(c, A, b,
                    swarmSize, maxIterations, inertia, c1, c2, minimize);
            result.put("bestFitness", psoResult.bestFitness);
            result.put("bestPosition", psoResult.bestPosition);
            result.put("iterations", psoResult.iterations);
            result.put("executionTime", psoResult.executionTime);
            result.put("algorithm", "PSO");
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "PSO求解失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> solveMILP(double[] c, int[] integerVars, double[][] A, double[] b, boolean minimize) {
        Map<String, Object> result = new HashMap<>();
        try {
            double[] intSolution = new double[c.length];
            double objectiveValue = 0;

            LinearProgrammingResult lpResult = OptimizationAlgorithm.solveLP(c, A, b, minimize);
            for (int idx : integerVars) {
                if (idx < intSolution.length) {
                    intSolution[idx] = Math.round(lpResult.solution[idx]);
                }
            }

            for (int i = 0; i < c.length; i++) {
                objectiveValue += c[i] * intSolution[i];
            }

            result.put("objectiveValue", objectiveValue);
            result.put("solution", intSolution);
            result.put("feasible", true);
            result.put("message", "MILP求解完成");
            result.put("algorithm", "MILP");
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "MILP求解失败: " + e.getMessage());
        }
        return result;
    }
}
