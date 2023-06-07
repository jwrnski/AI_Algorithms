package GeneticAlgorithm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

public class RouletteSelection2 {

    // select(population, answers) return newPopulation{the best chromosomes}

    public static double getMin(float[] fitness) {
        double min = fitness[0];
        for (int i = 0; i < fitness.length; i++) {
            if (min > fitness[i])
                min = fitness[i];
        }
        return min;
    }

    public static double getMax(double[] chance) {
        double max = chance[0];
        for (int i = 0; i < chance.length; i++) {
            if (max < chance[i])
                max = chance[i];
        }
        return max;
    }

    // Each value has a percentage, the bigger (better) fitness value, the higher percentage
    // of it being selected to be kept in the new population.
    public static double[] setRoulette(float[] fitness) {
        int size = fitness.length;
        double min = getMin(fitness);
        double inc = Math.abs(min) + 1;
        double[] increment = new double[size];
        double[] percent = new double[size];
        double sum = 0, test = 0;
        for (int i = 0; i < size; i++) {
            increment[i] = fitness[i] + inc;
            increment[i] = Math.pow(increment[i], 5);
            sum += increment[i];
        }
        for (int i = 0; i < size; i++) {
            percent[i] = increment[i] / sum;
        }
        //System.out.println(Arrays.toString(fitness));
        /*System.out.println(inc);
        System.out.println(Arrays.toString(increment));
        System.out.println(sum);
        System.out.println(Arrays.toString(percent));
        System.out.println(test);*/
        return percent;
    }
    public static double[] setRouletteMin(float[] fitness){
        int size = fitness.length;
        double min = getMin(fitness);
        double inc = Math.abs(min) + 1;
        double[] increment = new double[size];
        double[] percent = new double[size];
        double sum = 0, test = 0;
        for (int i = 0; i < size; i++) {
            increment[i] = 1 / (fitness[i] + inc);
            increment[i] = Math.pow(increment[i], 5);
            sum += increment[i];
        }
        for (int i = 0; i < size; i++) {
            percent[i] = increment[i] / sum;
        }
       /* for(int i = 0; i < size; i++){
            test += percent[i];
        }
        System.out.println("Fitness:\n" + Arrays.toString(fitness));
        System.out.println("Percentage:\n" + Arrays.toString(percent));
        System.out.println(test);*/
        return percent;
    }

    public static int[] roulette(float[] fitnessArray) {
        double[] chance = setRouletteMin(fitnessArray);
        int rows = fitnessArray.length;
        int[] toKeep = new int[rows];
        double added = 0;
        toKeep[0] = chance.length - 1;
        int j = 0, k = 0;
        for (int i = 1; i < rows; i++) {
            double pick = Math.random();
            //System.out.println(pick);
            while (added < pick) {
                added += chance[j];
                j++;
                k++;
                //System.out.println(added + "\t\t" + j + "\t" + k);
            }
            if (k == rows) k -= 1;
            else toKeep[i] = k;
            added = 0;
            k = 0;
            j = 0;
        }
        //System.out.println("\nChromosomes to keep:" + Arrays.toString(toKeep));
        return toKeep;
    }

    public static float[] pickedFitness(int[] keep, float[] fitness) {
        //System.out.println(Arrays.toString(fitness));
        int size = fitness.length;
        float[] keepThese = new float[size];
        //System.out.println(Arrays.toString(fitness));
        for (int i = 0; i < size; i++) {
            int index = keep[i];
            //System.out.println(index);
            keepThese[i] = fitness[index];
        }
        //System.out.println(Arrays.toString(keepThese));
        return keepThese;
    }

    public static int[][] createNewPopulation(int[][] population, float[] fitness) {
        int rows = population.length;
        int columns = population[0].length;
        int[][] newPopulation = new int[rows][columns];
        Arrays.sort(fitness);
        int[] keep = roulette(fitness);
        float[] keepThese = pickedFitness(keep, fitness);
        //System.out.println(Arrays.toString(keepThese));
        int c = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < rows; j++){
                if(FitnessEvaluation.calc(population, i) == keepThese[j]){
                    for(int k = 0; k < columns; k++){
                        newPopulation[c][k] = population[i][k];
                    }
                    if(c < rows-1) c++;
                }
            }
        }
        //Evaluate.printPopulation(newPopulation);
        // keep = {1, 1, 2, 1, 3}
        // fitness = {a, b, c, d, e}
        // checkAns = {a, a, b, a, c}

        return newPopulation;
    }
}
