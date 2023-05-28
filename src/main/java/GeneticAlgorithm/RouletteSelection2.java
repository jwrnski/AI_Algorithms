package GeneticAlgorithm;

import java.util.Arrays;

public class RouletteSelection2 {

    // select(population, answers) return newPopulation{the best chromosomes}

    // Each value has a percentage, the bigger (better) fitness value, the higher percentage
    // of it being selected to be kept in the new population.
    public static float[] setRoulette(float[] fitness){
        int size = fitness.length;
        float fitnessSum = 0;
        float[] percentage = new float[size];
        // Find the smalles value from the fitness array, get and abs from it and increment it by 1
        // Increment all values by the inc array (no negative values in the array!)
        float res = fitness[0];
        for (int i = 1; i < size; i++)
            res = Math.min(res, fitness[i]);
        float min = res;
        float inc = Math.abs(min) + 1;
        //System.out.println("Increment by: " + inc);
        for(int i=0; i < size; i++)
            fitnessSum += fitness[i] + inc;
        for(int i=0; i<size; i++)
            percentage[i] = (fitness[i] + inc) / fitnessSum;
        //System.out.println(Arrays.toString(percentage));
        return percentage;
    }

    public static int[] roulette(float[] fitnessArray){
        float[] percentage = setRoulette(fitnessArray);
        int len = fitnessArray.length;
        int[] toKeep = new int[len];
        float added = percentage[0];
        float random;
        int j = 0;
        for(int i = 0; i < len; i++){
            random = (float) Math.random();
            //System.out.println("random: " + random);
            while(added < random){
                added += percentage[j];
                j++;
            }
            if(j>0)
                toKeep[i] = (byte) (j-1);
            else
                toKeep[i] = (byte) j;
            added = percentage[0];
            j = 0;
        }
        //System.out.println("\nChromosomes to keep:" + Arrays.toString(toKeep));
        return toKeep;
    }

    public static int[][] createNewPopulation(int[][] population, float[] fitness){
        int rows = population.length;
        int columns = population[0].length;
        int[][] newPopulation = new int[rows][columns];
        int[] keep = roulette(fitness);
        for (int i = 0; i < rows; i++) {
            int rowToCopy = keep[i];
            System.arraycopy(population[rowToCopy], 0, newPopulation[i], 0, population[rowToCopy].length-1);
        }
        return newPopulation;
    }
}
