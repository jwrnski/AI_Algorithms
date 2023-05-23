package GeneticAlgorithm;

import java.util.Arrays;

public class Evaluate {

    /*
    * ev - number of evaluations
    * pc - probability for crossover
    */
    public static int ev = 1000;
    public static float pc = 0.6F;


    public static void eval(int N, int ev){
        byte[][] population = Population.generatePopulation((byte) N);
        for(int i = 0; i<N; i++) {
            for (int j = 0; j < population[0].length - 1; j++) {
                System.out.print(population[i][j]);
            }
            System.out.println("\n");
        }
        System.out.println("\n");
        population = Crossover.twoPointCrossPopulation(population);
        for(int i = 0; i<N; i++) {
            for (int j = 0; j < population[0].length - 1; j++) {
                System.out.print(population[i][j]);
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        eval(6, 0);
    }
}
