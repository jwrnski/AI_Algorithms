package GeneticAlgorithm;

import java.util.Arrays;

public class GeneticOperator {

    /*
     ai - lower value of range
     bi - upper value of range
     d - precision of encoding (d decimal places)
     mi - minimum amount of bits needed to encode [ai;bi] with d precision
     pm - probability of mutation
    */
    public static float ai = -2, bi = 2, d = 5, pm;
    public static int mi = (int)Math.ceil((Math.log((bi - ai) * Math.pow(10, d)) / Math.log(2)));
    public static int[] chromosome = new int[mi*2];
    public static int position1, position2;

    // Rounds up floats to 5 decimal points.
    public static float roundAvoid(float value) {
        float scale = (float) Math.pow(10, d);
        return (float) Math.round(value * scale) / scale;
    }

    // Decodes the position in decimal into our value from the [ai,bi] range.
    public static float decode(int decimal){
        return roundAvoid(ai + (decimal * ((bi - ai) / 400001)));
    }

    public static float function(float x1, float x2){
        return (float) (-Math.pow(x1,2) - Math.pow(x2,2) + 2);
    }

    public static int[][] mutatePopulation(int[][] population, double probability){
        int rows = population.length;
        int columns = population[0].length;
        int[][] newPopulation = new int[rows][columns];
        int[] newChromosome = new int[columns];
        int[] mutated = new int[columns];
        for(int i = 0; i < rows; i++){
            newChromosome = Crossover2D.copy(population, i);
            mutated = mutate(newChromosome, probability);
            while(!Crossover2D.checkChromosome(mutated)) mutated = mutate(newChromosome, probability);
            for(int j = 0; j < columns; j++){
                newPopulation[i][j] = mutated[j];
            }
        }
        return newPopulation;
    }

    public static int[] mutate(int[] chromosome, double probability){
        int size = chromosome.length;
        int[] mutated = new int[size];
        double random;
        for(int j = 0; j < size; j++){
            random = Math.random();
            if(random < probability)
                mutated[j] = 1 - chromosome[j];
            else
                mutated[j] = chromosome[j];
        }
        return mutated;
    }

    // Fills in array with randomly generated 0s and 1s.
    public static int[] generateChromosome(int genotypeNum){
        int[] parent = new int[mi*genotypeNum];
        for(int i=0; i<mi*genotypeNum-1; i++)
            parent[i] = (int) Math.round(Math.random());
        chromosome = parent;
        return parent;
    }

    // Convert our array with chromosome into String in order to convert binary value into decimal value.
    public static String toString(int[] vector){
        StringBuilder bin = new StringBuilder();
        for (int b : vector) {
            bin.append(b);
        }
        return bin.toString();
    }
}
