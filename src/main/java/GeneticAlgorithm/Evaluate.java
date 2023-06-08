package GeneticAlgorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Evaluate {

    /*
    * ev - number of evaluations
    * pc - probability for crossover
    */
    public static int ev = 1000;
    public static double pc = 0.02;

    // generate population
    // calculate fitness values for each chromosome
    // based on those values use roulette selection to keep the best chromosomes
    // add them to a new population
    // do the crossover and mutation on new population
    // calculate fitness values again for new population
    // repeat until end of evaluation steps (ev)

    public static void writeToFileCurrentBest(float[] values, int number){
        String fileName = "test\\current_best\\p50_currentBest_" +  number + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (float val : values) {
                writer.write(String.valueOf(val));
                writer.newLine(); // Add a new line after each number
            }
            System.out.println("Numbers have been written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
    public static void writeToFileGlobalBest(float[] values, int number){
        String fileName = "test\\global_best\\p50_global_Best" +  number + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (float val : values) {
                writer.write(String.valueOf(val));
                writer.newLine(); // Add a new line after each number
            }
            System.out.println("Numbers have been written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
    public static void writeToFileAvg(float[] values, int number){
        String fileName = "test\\average\\p50_avg_" +  number + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (float val : values) {
                writer.write(String.valueOf(val));
                writer.newLine(); // Add a new line after each number
            }
            System.out.println("Numbers have been written to the file.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    public static void eval(int N, int ev, int j, int genotypeNum){
        int[][] population = Population.generatePopulation(N,genotypeNum);
        printPopulation(population);
        float[] ans = FitnessEvaluation.getAns(population, genotypeNum);
        float[] deltaEv = new float[ev+1];
        float[] avg_ev = new float[ev+1];
        float[] globalBest = new float[ev+1];
        float currentMin = FitnessEvaluation.getMin(ans);
        float globalMin = currentMin;
        int c = 1;
        deltaEv[0] = FitnessEvaluation.getMin(ans);
        avg_ev[0] = FitnessEvaluation.avg(ans);
        globalBest[0] = currentMin;
        for(int i = 0; i<ev; i++) {
            population = RouletteSelection2.createNewPopulation(population, ans);
            population = Crossover2D.mixPopulation(population);
            population = GeneticOperator.mutatePopulation(population, pc);
            ans = FitnessEvaluation.getAns(population, genotypeNum);
            deltaEv[c] = FitnessEvaluation.getMin(ans);
            avg_ev[c] = FitnessEvaluation.avg(ans);
            currentMin = FitnessEvaluation.getMin(ans);
            if(currentMin < globalMin){
                globalBest[c] = currentMin;
                globalMin = currentMin;
            }
            else
                globalBest[c] = globalMin;
            c++;
            //System.out.println(Arrays.toString(ans));
        }
        writeToFileCurrentBest(deltaEv, j);
        writeToFileAvg(avg_ev, j);
        writeToFileGlobalBest(globalBest, j);
    }

    public static void printPopulation(int[][] population){
        int rows = population.length;
        int columns = population[0].length;
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < columns - 1; j++) {
                System.out.print(population[i][j]);
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        eval(10, 1, 1, 5);
        //for(int i = 0; i < 30; i++) {
        //    eval(10, 2, i);
       // }
    }
}
