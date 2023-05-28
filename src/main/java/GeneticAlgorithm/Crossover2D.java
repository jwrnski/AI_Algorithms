package GeneticAlgorithm;

import java.util.Random;

public class Crossover2D {

    // Copies a chromosome from population to a separate byte[] array
    public static int[] copy(int[][] toCopy, int row){
        byte columns = (byte) toCopy[0].length;
        int[] copied = new int[columns];
        for(int k=0; k<columns; k++)
            copied[k] = toCopy[row][k];
        return copied;
    }

    // Cross every chromosome in the population
    // Chance of crossover for current chromosome is crossProbability (60%)
    // Cannot cross with itself
    public static int[][] mixPopulation(int[][] population){
        byte rows = (byte) population.length;
        byte columns = (byte) population[0].length;
        float crossProbability = 0;
        int[][] newPopulation = new int[rows][columns];
        int[] newChromosome;
        int crossWith = 0;
        Random random = new Random();
        for(int i = 0; i< rows; i++){
            crossProbability = (float) Math.random();
            crossWith = random.nextInt(6);
            while(crossWith == i) crossWith = random.nextInt(6);
            if(crossProbability <= 0.6) {
                //System.out.println("i " + i + "with " + crossWith);
                newChromosome = crossover(population, i, crossWith);
                while(!checkChromosome(newChromosome)) newChromosome = crossover(population, i, crossWith);
                for (int j = 0; j < columns; j++) {
                    newPopulation[i][j] = newChromosome[j];
                }
            }
            else{
                newChromosome = copy(population, i);
                for(int j = 0; j < columns; j++){
                    newPopulation[i][j] = newChromosome[j];
                }
            }
        }
        return newPopulation;
    }

    // Generate two random points in the chromosome for the crossover to occur
    public static int[] crossover(int[][] population, int row, int crossWith){
        byte crossP1 = (byte)Math.round(Math.random() * 38);
        byte crossP2 = (byte)Math.round(Math.random() * 38);
        byte lower;
        byte upper;
        if(crossP1 > crossP2){
            lower = crossP2;
            upper = crossP1;
        }
        else{
            lower = crossP1;
            upper = crossP2;
        }
        byte columns = (byte) population[0].length;
        int[] newChromosome = new int[columns];
        for(int i = 0; i < columns - 1; i++){
            if(i < lower){
                newChromosome[i] = population[row][i];
            }
            else if (i > lower && i < upper){
                newChromosome[i] = population[crossWith][i];
            }
            else if(i > upper){
                newChromosome[i] = population[row][i];
            }
        }
        return newChromosome;
    }

    // Check if the new chromosome and its genotypes are within bounds
    public static boolean checkChromosome(int[] chromosome){
        int len = chromosome.length;
        int position1, position2;
        int[] genotype1 = new int[len/2];
        int[] genotype2 = new int[len/2];
        int j = 0;
        for(int i=0; i<len; i++){
            if(i < len/2)
                genotype1[i] = chromosome[i];
            else {
                genotype2[j] = chromosome[i];
                j++;
            }
        }
        position1 = Integer.parseInt(GeneticOperator.toString(genotype1), 2);
        position2 = Integer.parseInt(GeneticOperator.toString(genotype2), 2);
        //System.out.println("pos 1: " + position1 + " pos 2: " + position2);
        return((position1 <= 400001) && (position2 <= 400001));
    }

}
