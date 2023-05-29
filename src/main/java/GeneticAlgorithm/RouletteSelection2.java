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
        // Find the smallest value from the fitness array, get and abs from it and increment it by 1
        // Increment all values by the inc array (no negative values in the array!)
        float res = fitness[0];
        for (int i = 1; i < size; i++)
            res = Math.min(res, fitness[i]);
        float min = res;
        float inc = Math.abs(min) + 1;
        //System.out.println("Increment by: " + inc);
        Arrays.sort(fitness);
        //System.out.println(Arrays.toString(fitness));
        for(int i=0; i < size; i++) {
            fitnessSum += fitness[i] + inc;
            //fitness[i] += inc;
        }
        //System.out.println(Arrays.toString(fitness));
        /*for(int i = 0; i < size; i++){
            fitness[i] = (float) Math.pow(fitness[i], 2);
        }*/
        //System.out.println(Arrays.toString(fitness));
        for(int i=0; i<size; i++) {
            percentage[i] = (fitness[i] + inc) / fitnessSum;
        }
        //System.out.println(Arrays.toString(percentage));
        return percentage;
    }

    public static int[] roulette(float[] fitnessArray){
        float[] percentage = setRoulette(fitnessArray);
        int rows = fitnessArray.length;
        int[] toKeep = new int[rows];
        float added = percentage[0];
        float random;
        int k = 0, j = 1;
        for(int i = 0; i < rows; i++){
            random = (float) Math.random();
            //System.out.println("random: " + random);
            while(added < random){
                added += percentage[j];
                k++;
                j++;
                //System.out.println(added + "\t" + k);
            }
            //System.out.println(added);
            toKeep[i] = k;
            k = 0;
            j = 1;
            added = percentage[0];
        }
        //System.out.println("\nChromosomes to keep:" + Arrays.toString(toKeep));
        return toKeep;
    }

    public static float[] pickedFitness(int[] keep, float[] fitness){
        int size = fitness.length;
        float[] keepThese = new float[size];
        for(int i = 0; i<size; i++){
            int index = keep[i];
            keepThese[i] = fitness[index];
        }
        return keepThese;
    }

    public static int[][] createNewPopulation(int[][] population, float[] fitness){
        int rows = population.length;
        int columns = population[0].length;
        int[][] newPopulation = new int[rows][columns];
        Arrays.sort(fitness);
        int[] keep = roulette(fitness);
        float[] keepThese = pickedFitness(keep, fitness);
        //System.out.println(Arrays.toString(keepThese));
        for(int i = 0; i < rows; i++){
            if(FitnessEvaluation.calc(population, i) == keepThese[i]) {
                //System.out.println(FitnessEvaluation.calc(population, i));
                for (int j = 0; j < columns; j++)
                    newPopulation[i][j] = population[i][j];
            }
        }

        // keep = {1, 1, 2, 1, 3}
        // fitness = {a, b, c, d, e}
        // checkAns = {a, a, b, a, c}

        return newPopulation;
    }
}
