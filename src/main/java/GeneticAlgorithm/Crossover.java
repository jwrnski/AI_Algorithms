package GeneticAlgorithm;

import java.util.Arrays;
import java.util.Random;

public class Crossover {

    public static float ai = -2, bi = 2, d = 5;
    public static int mi = (int)Math.ceil((Math.log((bi - ai) * Math.pow(10, d)) / Math.log(2)));
    public static byte[] parent1 = new byte[2*mi];
    public static byte[] parent2 = new byte[2*mi];
    public static byte[] child1 = new byte[2*mi];
    public static byte[] child2 = new byte[2*mi];
    public static byte crossPoint1, crossPoint2;


    public static void generateParents(){
        parent1 =  GeneticOperator.generateChromosome();
        parent2 =  GeneticOperator.generateChromosome();
    }

    // Randomly generates a point where two parents will cross their genes and create two children.
    public static void onePointCrossover(byte[] parent1, byte[] parent2){
        crossPoint1 = (byte)Math.round(Math.random() * (37));
        System.out.println("Crossover point: " + crossPoint1);
        for(int i=0; i<parent1.length-1; i++){
            if(i < crossPoint1){
                child1[i] = parent1[i];
                child2[i] = parent2[i];
            }
            else{
                child1[i] = parent2[i];
                child2[i] = parent1[i];
            }
        }
        checkChildren(child1, child2, 1);
    }

    // Does the two point crossover between cc ( current chromosome )
    // and randomly generated chromosome from the population.
    public static byte[][] twoPointCrossPopulation(byte[][] population){
        byte rows = (byte) population.length;
        byte columns = (byte) population[0].length;
        int crossWith = 0;
        float crossProbability = 0;
        byte[][] newPopulation = new byte[rows][columns];
        byte[] newChromosome = new byte[columns];
        for(int i = 0; i < rows; i++){
            byte crossP1 = (byte)Math.round(Math.random() * (18));
            byte crossP2 = (byte)Math.round(Math.random() * (37 - 18) + 18);
            crossProbability = (float) Math.random();
            while (crossWith == i){
                Random random = new Random();
                crossWith = random.nextInt(5);
            }
            System.out.println("i: " + i + " p: " + crossProbability + " cross with: " + crossWith);
            if(crossProbability <= 0.6) {
                for (int j = 0; j < columns - 1; j++) {
                    if (j < crossP1) {
                        newChromosome[i] = population[i][j];
                    } else if (j > crossP1 && j < crossP2) {
                        newChromosome[i] = population[crossWith][j];
                    } else if (j > crossP2) {
                        newChromosome[i] = population[i][j];
                    }
                }
            }
            else {
                for(int k=0; k<columns; k++)
                    newChromosome[k] = population[i][k];
            }
            if(checkGenotype(newChromosome)){
                for (int j = 0; j < columns - 1; j++) {
                    newPopulation[i][j] = newChromosome[j];
                }
            }
            else{
                i--;
            }
        }
        return newPopulation;
    }

    public static boolean checkGenotype(byte[] chromosome){
        int len = chromosome.length;
        int position1, position2;
        byte[] genotype1 = new byte[len/2];
        byte[] genotype2 = new byte[len/2];
        int j = 0;
        for(int i=0; i<len; i++){
            if(i < mi)
                genotype1[i] = chromosome[i];
            else {
                genotype2[j] = chromosome[i];
                j++;
            }
        }
        position1 = Integer.parseInt(GeneticOperator.toString(genotype1), 2);
        position2 = Integer.parseInt(GeneticOperator.toString(genotype2), 2);
        System.out.println("pos 1: " + position1 + " pos 2: " + position2);
        return((position1 <= 400001) && (position2 <= 400001));
    }

    // Generates two crossover points.
    public static void twoPointCrossover(byte[] parent1, byte[] parent2){
        crossPoint1 = (byte)Math.round(Math.random() * (18));
        crossPoint2 = (byte)Math.round(Math.random() * (37 - 18) + 18);
        System.out.println("Crossover points: " + crossPoint1 + ", " + crossPoint2);
        for(int i=0; i<parent1.length-1; i++){
            if(i < crossPoint1){
                child1[i] = parent1[i];
                child2[i] = parent2[i];
            }
            else if(i > crossPoint1 && i < crossPoint2){
                child1[i] = parent2[i];
                child2[i] = parent1[i];
            }
            else if(i >= crossPoint2){
                child1[i] = parent1[i];
                child2[i] = parent2[i];
            }
        }
        checkChildren(child1, child2, 2);
    }

    // Checks if either of children are correct if not do the crossover again until they are correct.
    public static void checkChildren(byte[] child1, byte[] child2, int option){
        if(!(GeneticOperator.getGenotype(child1) || GeneticOperator.getGenotype(child2)))
            switch (option){
                case 1 -> onePointCrossover(parent1, parent2);
                case 2 -> twoPointCrossover(parent1, parent2);
            }
    }

    public static void main(String[] args) {
        generateParents();
        twoPointCrossover(parent1, parent2);
        System.out.println(Arrays.toString(parent1));
        System.out.println(Arrays.toString(parent2));
        System.out.println(Arrays.toString(child1));
        System.out.println(Arrays.toString(child2));
    }

}
