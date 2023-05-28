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
    public static int[] genotype1 = new int[mi];
    public static int[] genotype2 = new int[mi];
    public static int position1, position2;
    public static float x1, x2;

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

    // Calculates the value of the function.
    public static float calc(){
        x1 = decode(position1);
        x2 = decode(position2);
        return function(x1, x2);
    }

    // Calculates the value of the function from the given chromosome.
    public static float calc(int[] chromosome){
        getGenotype(chromosome);
        position1 = Integer.parseInt(toString(genotype1), 2);
        position2 = Integer.parseInt(toString(genotype2), 2);
        x1 = decode(position1);
        x2 = decode(position2);
        return function(x1, x2);
    }

    // Mutates the chromosome with pm probability.
    public static void mutate(float pm){
        float rand;
        for(int i =0; i < mi*2-1; i++){
            rand = (float) Math.random();
            if(rand < pm) chromosome[i] = (byte) (chromosome[i] == 0 ? 1 : 0);
        }
    }

    // Fills in array with randomly generated 0s and 1s.
    public static int[] generateChromosome(){
        int[] parent = new int[mi*2];
        for(int i=0; i<mi*2-1; i++)
            parent[i] = (byte)Math.round(Math.random());
        if(!getGenotype(parent)) generateChromosome();
        chromosome = parent;
        return parent;
    }

    // A chromosome consists of two genotypes which are essentially our solutions for function.
    public static boolean getGenotype(int[] chromosome){
        int j = 0;
        for(int i=0; i<mi*2-1; i++){
            if(i < mi)
                genotype1[i] = chromosome[i];
            else {
                genotype2[j] = chromosome[i];
                j++;
            }
        }
        return checkGenotype(genotype1, genotype2);
    }

    // Check if the genotypes are correct.
    public static boolean checkGenotype(int[] genotype1, int[] genotype2){
        position1 = Integer.parseInt(toString(genotype1), 2);
        position2 = Integer.parseInt(toString(genotype2), 2);
        return ((position1 <= 400001) && (position2 <= 400001));
    }

    public static boolean checkGenotype(int[] genotype){
        position1 = Integer.parseInt(toString(genotype), 2);
        return position1 <= 400001;
    }

    // Convert our array with chromosome into String in order to convert binary value into decimal value.
    public static String toString(int[] vector){
        StringBuilder bin = new StringBuilder();
        for (int b : vector) {
            bin.append(b);
        }
        return bin.toString();
    }

    public static void main(String[] args) {
        /*generateChromosome();
        System.out.println(Arrays.toString(chromosome));
        mutate(1);
        System.out.println(Arrays.toString(chromosome));
        System.out.println();
        System.out.println(Arrays.toString(genotype1));
        System.out.println(position1);
        System.out.println(decode(position1));
        System.out.println(Arrays.toString(genotype2));
        System.out.println(position2);
        System.out.println(decode(position2));
        System.out.println("f = ");
        calc();*/
        int[] parent1 = generateChromosome();
        int[] parent2 = generateChromosome();
        System.out.println(Arrays.toString(parent1));
        System.out.println(Arrays.toString(parent2));
    }
}
