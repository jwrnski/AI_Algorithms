package GeneticAlgorithm;

import java.util.Arrays;

public class Population {

    public static float ai = -2, bi = 2, d = 5, pm, avg, amtL=0, amtS=0;
    public static int mi = (int)Math.ceil((Math.log((bi - ai) * Math.pow(10, d)) / Math.log(2)));
    public static int position;
    public static int[] chromosome = new int[mi*2];
    public static float[] ans = new float[5];

    // Generates a chromosome with correct genotypes.
    public static int[] generateChromosome(){
        return GeneticOperator.generateChromosome();
    }

    // Generates a population of chromosomes. Fills in a two-dimensional array with N amount of chromosomes.
    public static int[][] generatePopulation(byte N){
        int[][] population = new int[N][2*mi];
        int[] chromosome = generateChromosome();
        for(int i=0; i<N; i++){
            for(int j=0; j<mi*2-1; j++){
                population[i][j] = chromosome[j];
            }
            //ans[i] = GeneticOperator.calc(chromosome);
            generateChromosome();
        }
        return population;
    }

    // Calculates the average of fitness function values.
    public static void avg(float[] ans){
        int len = ans.length;
        for(int i=0; i<len; i++){
            avg += ans[i];
        }
        avg /= len;
    }

    // Simply checks which chromosome generated an answer bigger or smaller than the average.
    public static void checkAns(int N){
        for(int i=0; i<N; i++){
            if(ans[i] >= avg) {
                System.out.println("Ans larger than avg: " + i);
                amtL++;
            }
            else {
                System.out.println("Ans smaller than avg: " + i);
                amtS++;
            }
        }
    }

    public static void print(float ans){
        if(ans > avg) System.out.print(" >\n");
        else System.out.print(" <\n");
    }

    public static void main(String[] args) {
        int[][] population;
        population = generatePopulation((byte) 5);
        for(int i = 0; i<5; i++) {
            for (int j = 0; j < mi * 2 - 1; j++) {
                System.out.print(population[i][j]);
                if(j == mi-1) System.out.print(" | ");
            }
            System.out.print(" f = " + ans[i]);
            print(ans[i]);
        }
        System.out.println("ans = \n" + Arrays.toString(ans));
        avg(ans);
        System.out.println("avg = \n" + avg);
        checkAns(5);
        System.out.println("Liczba osobników, których wartośc funkcji przystosowania jest większa lub równa: " + amtL);
        System.out.println("Liczba osobników, których wartośc funkcji przystosowania jest mniejsza: " + amtS);
    }
}
