package GeneticAlgorithm;

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

    public static void eval(int N, int ev){
        int[][] population = Population.generatePopulation(N);
        float[] ans = FitnessEvaluation.getAns(population);
        float[] deltaEv = new float[ev/50+1];
        int c = 0;
        //System.out.println(FitnessEvaluation.getMax(ans));
        deltaEv[c] = FitnessEvaluation.getMax(ans);
        c++;
        for(int i = 0; i<ev; i++) {
            population = RouletteSelection2.createNewPopulation(population, ans);
            population = Crossover2D.mixPopulation(population);
            population = GeneticOperator.mutatePopulation(population, pc);
            ans = FitnessEvaluation.getAns(population);
            if(i % 50 == 0){
                deltaEv[c] = FitnessEvaluation.getMax(ans);
                c++;
            }
            //System.out.println(FitnessEvaluation.getMax(ans));
        }
        for(int i = 0; i<ev/50+1; i++)
            System.out.println(deltaEv[i]);
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
        eval(200, 1000);
    }
}
