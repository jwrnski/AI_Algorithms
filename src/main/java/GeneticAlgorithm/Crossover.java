package GeneticAlgorithm;

import java.util.Arrays;

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
        byte crossWith = 0;
        float crossProbability = 0;
        byte[][] newPopulation = new byte[rows][columns];

        for(int i = 0; i < rows - 1; i++){
            byte crossP1 = (byte)Math.round(Math.random() * (18));
            byte crossP2 = (byte)Math.round(Math.random() * (37 - 18) + 18);
            while (crossWith == i){
                crossWith = (byte) Math.round(Math.random() * rows-1);
            }
            crossProbability = (float) Math.random();
            System.out.println(i + " " + crossProbability);
            if(crossProbability <= 0.6) {
                for (int j = 0; j < columns - 1; j++)
                    if (j < crossP1) {
                        newPopulation[i][j] = population[i][j];
                        newPopulation[i + 1][j] = population[crossWith][j];
                    } else if (j > crossP1 && j < crossP2) {
                        newPopulation[i][j] = population[crossWith][j];
                        newPopulation[i + 1][j] = population[i][j];
                    } else if (j > crossP2) {
                        newPopulation[i][j] = population[i][j];
                        newPopulation[i + 1][j] = population[crossWith][j];
                    }
                }
            else
                for (int k = 0; k < columns - 1; k++)
                    newPopulation[i][k] = population[i][k];
        }
        return newPopulation;
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
