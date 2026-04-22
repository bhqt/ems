package com.ruoyi.system.service;

import com.ruoyi.system.algorithm.OptimizationAlgorithm.*;
import java.util.Map;

public interface IOptimizationService {

    Map<String, Object> solveLinearProgramming(double[] c, double[][] A, double[] b, boolean minimize);

    Map<String, Object> solveGeneticAlgorithm(double[] c, double[][] A, double[] b,
                                               int populationSize, int maxIterations,
                                               double crossoverRate, double mutationRate, boolean minimize);

    Map<String, Object> solvePSO(double[] c, double[][] A, double[] b,
                                   int swarmSize, int maxIterations,
                                   double inertia, double c1, double c2, boolean minimize);

    Map<String, Object> solveMILP(double[] c, int[] integerVars, double[][] A, double[] b, boolean minimize);
}
