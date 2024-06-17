/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fss;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import algen.Individu;

public class FSS {
    private int[][] processingTimes;
    private int numJobs;
    private int numMachines;

    // Constructor with Individu and int[][] parameters
    public FSS(Individu individu, int[][] processingTimes) {
        this.numJobs = individu.getUrutanPekerjaan().length;
        this.numMachines = individu.getUrutanMesin().length;
        this.processingTimes = processingTimes;
    }

    public FSS(int numJobs, int numMachines) {
        this.numJobs = numJobs;
        this.numMachines = numMachines;
        this.processingTimes = new int[numJobs][numMachines];
        generateProcessingTimes();
    }

    private void generateProcessingTimes() {
        Random rand = new Random();
        for (int i = 0; i < numJobs; i++) {
            for (int j = 0; j < numMachines; j++) {
                processingTimes[i][j] = rand.nextInt(100) + 1;
            }
        }
    }

    public int calculateTotalFlowTime(int[] jobOrder) {
        int totalFlowTime = 0;
        int[] completionTimes = new int[numMachines];

        for (int job : jobOrder) {
            completionTimes[0] += processingTimes[job][0];
            for (int machine = 1; machine < numMachines; machine++) {
                completionTimes[machine] = Math.max(completionTimes[machine], completionTimes[machine - 1]) + processingTimes[job][machine];
            }
            totalFlowTime += completionTimes[numMachines - 1];
        }
        return totalFlowTime;
    }

    // Method to calculate makespan
    public int getMakespan() {
        int makespan = 0;
        for (int i = 0; i < processingTimes.length; i++) {
            for (int j = 0; j < processingTimes[i].length; j++) {
                makespan = Math.max(makespan, processingTimes[i][j]);
            }
        }
        return makespan;
    }

    private int[] generateInitialSolution() {
        int[] initialSolution = new int[numJobs];
        for (int i = 0; i < numJobs; i++) {
            initialSolution[i] = i;
        }
        shuffleArray(initialSolution);
        return initialSolution;
    }

    private void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private int[][] generateNeighborhood(int[] solution) {
        int[][] neighborhood = new int[numJobs * (numJobs - 1) / 2][];
        int index = 0;
        for (int i = 0; i < numJobs; i++) {
            for (int j = i + 1; j < numJobs; j++) {
                int[] neighbor = Arrays.copyOf(solution, solution.length);
                int temp = neighbor[i];
                neighbor[i] = neighbor[j];
                neighbor[j] = temp;
                neighborhood[index++] = neighbor;
            }
        }
        return neighborhood;
    }

    public int[] tabuSearch(int maxIterations, int tabuTenure) {
        int[] bestSolution = generateInitialSolution();
        int bestCost = calculateTotalFlowTime(bestSolution);

        int[] currentSolution = Arrays.copyOf(bestSolution, bestSolution.length);
        int currentCost = bestCost;

        Queue<int[]> tabuList = new LinkedList<>();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            int[][] neighborhood = generateNeighborhood(currentSolution);
            int[] bestNeighbor = null;
            int bestNeighborCost = Integer.MAX_VALUE;

            for (int[] neighbor : neighborhood) {
                int neighborCost = calculateTotalFlowTime(neighbor);
                if (neighborCost < bestNeighborCost && !tabuList.contains(neighbor)) {
                    bestNeighbor = neighbor;
                    bestNeighborCost = neighborCost;
                }
            }

            if (bestNeighbor != null) {
                currentSolution = bestNeighbor;
                currentCost = bestNeighborCost;

                if (currentCost < bestCost) {
                    bestSolution = currentSolution;
                    bestCost = currentCost;
                }

                tabuList.add(bestNeighbor);
                if (tabuList.size() > tabuTenure) {
                    tabuList.poll();
                }
            }
        }

        return bestSolution;
    }

    public static void main(String[] args) {
        int numJobs = 5;
        int numMachines = 3;
        FSS fss = new FSS(numJobs, numMachines);

        // Example job order
        int[] jobOrder = {0, 1, 2, 3, 4};
        int totalFlowTime = fss.calculateTotalFlowTime(jobOrder);
        System.out.println("Initial Total Flow Time: " + totalFlowTime);

        // Tabu Search
        int maxIterations = 100;
        int tabuTenure = 10;
        int[] optimalSolution = fss.tabuSearch(maxIterations, tabuTenure);
        int optimalFlowTime = fss.calculateTotalFlowTime(optimalSolution);
        System.out.println("Optimal Job Order: " + Arrays.toString(optimalSolution));
        System.out.println("Optimal Total Flow Time: " + optimalFlowTime);
    }
}


