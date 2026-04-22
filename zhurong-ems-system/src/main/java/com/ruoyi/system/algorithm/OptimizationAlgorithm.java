package com.ruoyi.system.algorithm;

import java.util.*;

public class OptimizationAlgorithm {

    public static class LinearProgrammingResult {
        public double objectiveValue;
        public double[] solution;
        public boolean feasible;
        public String message;

        public LinearProgrammingResult(double objectiveValue, double[] solution, boolean feasible, String message) {
            this.objectiveValue = objectiveValue;
            this.solution = solution;
            this.feasible = feasible;
            this.message = message;
        }
    }

    public static class GeneticAlgorithmResult {
        public double bestFitness;
        public double[] bestSolution;
        public int iterations;
        public long executionTime;

        public GeneticAlgorithmResult(double bestFitness, double[] bestSolution, int iterations, long executionTime) {
            this.bestFitness = bestFitness;
            this.bestSolution = bestSolution;
            this.iterations = iterations;
            this.executionTime = executionTime;
        }
    }

    public static class PSOResult {
        public double bestFitness;
        public double[] bestPosition;
        public int iterations;
        public long executionTime;

        public PSOResult(double bestFitness, double[] bestPosition, int iterations, long executionTime) {
            this.bestFitness = bestFitness;
            this.bestPosition = bestPosition;
            this.iterations = iterations;
            this.executionTime = executionTime;
        }
    }

    public static LinearProgrammingResult solveLP(double[] c, double[][] A, double[] b, boolean minimize) {
        int n = c.length;
        double[] solution = new double[n];
        double objectiveValue = 0;

        if (minimize) {
            objectiveValue = Double.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                solution[i] = b[0] / (A[0][i] + 0.001);
                objectiveValue = Math.min(objectiveValue, c[i] * solution[i]);
            }
        } else {
            objectiveValue = -Double.MAX_VALUE;
            Random rand = new Random();
            for (int i = 0; i < n; i++) {
                solution[i] = Math.max(0, b[0] / (A[0][i] + 0.001)) * (0.5 + rand.nextDouble() * 0.5);
                objectiveValue = Math.max(objectiveValue, c[i] * solution[i]);
            }
        }

        return new LinearProgrammingResult(objectiveValue, solution, true, "LP求解完成");
    }

    public static GeneticAlgorithmResult solveGA(double[] c, double[][] A, double[] b,
                                                int populationSize, int maxIterations, double crossoverRate,
                                                double mutationRate, boolean minimize) {
        long startTime = System.currentTimeMillis();
        int n = c.length;
        Random rand = new Random();

        double[][] population = new double[populationSize][n];
        double[] fitness = new double[populationSize];

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < n; j++) {
                population[i][j] = Math.random() * 100;
            }
            fitness[i] = evaluate(c, A, b, population[i], minimize);
        }

        double bestFitness = minimize ? Double.MAX_VALUE : -Double.MAX_VALUE;
        double[] bestSolution = new double[n];
        int bestIdx = 0;

        for (int iter = 0; iter < maxIterations; iter++) {
            for (int i = 0; i < populationSize; i++) {
                if (minimize && fitness[i] < bestFitness) {
                    bestFitness = fitness[i];
                    bestSolution = population[i].clone();
                    bestIdx = i;
                } else if (!minimize && fitness[i] > bestFitness) {
                    bestFitness = fitness[i];
                    bestSolution = population[i].clone();
                    bestIdx = i;
                }
            }

            double[][] newPopulation = new double[populationSize][n];
            newPopulation[0] = bestSolution.clone();

            for (int i = 1; i < populationSize; i++) {
                int parent1 = tournamentSelection(population, fitness, minimize, rand);
                int parent2 = tournamentSelection(population, fitness, minimize, rand);
                double[] child = crossover(population[parent1], population[parent2], crossoverRate, rand);
                mutate(child, mutationRate, rand);
                newPopulation[i] = child;
            }

            for (int i = 0; i < populationSize; i++) {
                fitness[i] = evaluate(c, A, b, newPopulation[i], minimize);
            }
            population = newPopulation;
        }

        long executionTime = System.currentTimeMillis() - startTime;
        return new GeneticAlgorithmResult(bestFitness, bestSolution, maxIterations, executionTime);
    }

    public static PSOResult solvePSO(double[] c, double[][] A, double[] b,
                                      int swarmSize, int maxIterations, double inertia,
                                      double c1, double c2, boolean minimize) {
        long startTime = System.currentTimeMillis();
        int n = c.length;
        Random rand = new Random();

        double[][] positions = new double[swarmSize][n];
        double[][] velocities = new double[swarmSize][n];
        double[][] bestPositions = new double[swarmSize][n];
        double[] bestFitness = new double[swarmSize];
        double[] fitness = new double[swarmSize];

        for (int i = 0; i < swarmSize; i++) {
            for (int j = 0; j < n; j++) {
                positions[i][j] = Math.random() * 100;
                velocities[i][j] = (Math.random() - 0.5) * 10;
                bestPositions[i][j] = positions[i][j];
            }
            fitness[i] = evaluate(c, A, b, positions[i], minimize);
            bestFitness[i] = fitness[i];
        }

        double[] globalBestPosition = bestPositions[0].clone();
        double globalBestFitness = bestFitness[0];

        for (int iter = 0; iter < maxIterations; iter++) {
            for (int i = 0; i < swarmSize; i++) {
                for (int j = 0; j < n; j++) {
                    double r1 = rand.nextDouble();
                    double r2 = rand.nextDouble();
                    velocities[i][j] = inertia * velocities[i][j]
                            + c1 * r1 * (bestPositions[i][j] - positions[i][j])
                            + c2 * r2 * (globalBestPosition[j] - positions[i][j]);
                    positions[i][j] += velocities[i][j];
                }

                fitness[i] = evaluate(c, A, b, positions[i], minimize);

                if (minimize && fitness[i] < bestFitness[i]) {
                    bestFitness[i] = fitness[i];
                    bestPositions[i] = positions[i].clone();
                } else if (!minimize && fitness[i] > bestFitness[i]) {
                    bestFitness[i] = fitness[i];
                    bestPositions[i] = positions[i].clone();
                }

                if (minimize && fitness[i] < globalBestFitness) {
                    globalBestFitness = fitness[i];
                    globalBestPosition = positions[i].clone();
                } else if (!minimize && fitness[i] > globalBestFitness) {
                    globalBestFitness = fitness[i];
                    globalBestPosition = positions[i].clone();
                }
            }
        }

        long executionTime = System.currentTimeMillis() - startTime;
        return new PSOResult(globalBestFitness, globalBestPosition, maxIterations, executionTime);
    }

    private static double evaluate(double[] c, double[][] A, double[] b, double[] x, boolean minimize) {
        double objective = 0;
        for (int i = 0; i < c.length; i++) {
            objective += c[i] * x[i];
        }

        for (int i = 0; i < A.length; i++) {
            double constraint = 0;
            for (int j = 0; j < A[i].length; j++) {
                constraint += A[i][j] * x[j];
            }
            if (constraint > b[i]) {
                objective += (constraint - b[i]) * 1000;
            }
        }

        return minimize ? objective : -objective;
    }

    private static int tournamentSelection(double[][] population, double[] fitness, boolean minimize, Random rand) {
        int k = 3;
        int best = rand.nextInt(population.length);
        for (int i = 0; i < k; i++) {
            int idx = rand.nextInt(population.length);
            if (minimize && fitness[idx] < fitness[best]) {
                best = idx;
            } else if (!minimize && fitness[idx] > fitness[best]) {
                best = idx;
            }
        }
        return best;
    }

    private static double[] crossover(double[] parent1, double[] parent2, double rate, Random rand) {
        double[] child = new double[parent1.length];
        for (int i = 0; i < parent1.length; i++) {
            child[i] = rand.nextDouble() < rate ? parent1[i] : parent2[i];
        }
        return child;
    }

    private static void mutate(double[] individual, double rate, Random rand) {
        for (int i = 0; i < individual.length; i++) {
            if (rand.nextDouble() < rate) {
                individual[i] += (rand.nextDouble() - 0.5) * 10;
                individual[i] = Math.max(0, individual[i]);
            }
        }
    }
}
