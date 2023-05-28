package GeneticAlgorithm;

import java.util.Arrays;

public class RouletteSelection {

    public static float ai = -2, bi = 2, d = 5, pm, avg, amtL=0, amtS=0;
    public static int mi = (int)Math.ceil((Math.log((bi - ai) * Math.pow(10, d)) / Math.log(2)));
    public static int[][] p1 = Population.generatePopulation((byte) 5);
    public static float fitnessSum = 0;
    public static float[] fitnessVal = Population.ans;

    public static float min=0;

    public static void sumFitness(int N){
        for(int i=0; i < N; i++)
            fitnessSum += fitnessVal[i];
    }

    public static void getMin(float[] fitness){
        float res = fitness[0];
        for (int i = 1; i < fitness.length; i++)
            res = Math.min(res, fitness[i]);
        min = res;
    }

    public static float[] setRoulette(float[] fitness){
        int size = fitness.length;
        float fitnessSum = 0;
        float[] percentage = new float[size];
        float res = fitness[0];
        for (int i = 1; i < fitness.length; i++)
            res = Math.min(res, fitness[i]);
        float min = res;
        float inc = Math.abs(min) + 1;
        System.out.println("Increment by: " + inc);
        for(int i=0; i < size; i++)
            fitnessSum += fitness[i] + inc;
        System.out.println("Sum of fitness values: " + fitnessSum);
        float[] fitSorted = Arrays.copyOf(fitness, fitness.length);
        Arrays.sort(fitSorted);
        for(int i=0; i<size; i++)
            percentage[i] = (fitSorted[i] + inc) / fitnessSum;
        return percentage;
    }

    public static void roulette(float[] percentVal){
        int[] keep = new int[percentVal.length];
        float temp = percentVal[0];
        float rand = 0;
        byte j = 0;
        System.out.println("Random values: ");
        for(int i=0; i<percentVal.length; i++){
            rand = (float) Math.random();
            System.out.print(rand + ", ");
            while(temp < rand){
                temp += percentVal[j];
                j++;
            }
            if(j>0)
                keep[i] = (byte) (j-1);
            else
                keep[i] = j;
            temp = percentVal[0];
            j = 0;
        }
        System.out.println("\nChromosomes to keep:\n" + Arrays.toString(keep));
        newPopulation(keep);
    }

    public static void newPopulation(int[] keep){
        byte size = (byte) keep.length;
        float[] fitnessP2 = new float[size];
        int[][] p2 = new int[size][2*mi-1];
        for (int i = 0; i < size; i++) {
            int rowToCopy = keep[i];
            System.arraycopy(p1[rowToCopy], 0, p2[i], 0, p1[rowToCopy].length-1);
        }
        int i=0;
        for (int[] row : p2) {
            fitnessP2[i] = GeneticOperator.calc(row);
            for (int element : row) {
                System.out.print(element);
            }
            i++;
            System.out.println("\n");
        }
        System.out.println("Fitness values for second population:\n" + Arrays.toString(fitnessP2));

    }

    public static float[] fromMin(float[] fitness, int N){
        float[] fromMin = new float[N];
        for(int i=0; i<N; i++)
            fromMin[i] = fitness[i] - min;
        return fromMin;
    }

    public static void main(String[] args) {
        for(int i = 0; i<5; i++) {
            for (int j = 0; j < mi * 2 - 1; j++) {
                System.out.print(p1[i][j]);
                if(j == mi-1) System.out.print(" | ");
            }
            System.out.println("\n");
        }
        sumFitness(5);
        System.out.println(Arrays.toString(fitnessVal) + "\nSum: " + fitnessSum);
        getMin(fitnessVal);
        System.out.println("Min: " + min);
        float[] percent = setRoulette(fitnessVal);
        System.out.println("% of fitness:\n" + Arrays.toString(percent));
        roulette(percent);
    }

}
