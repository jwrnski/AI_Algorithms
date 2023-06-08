package GeneticAlgorithm;

public class FitnessEvaluation {

    public static float[] getAns(int[][] population, int genotypes){
        int len = population.length;
        float[] ans = new float[len];
        for(int i = 0; i < len; i++){
            ans[i] = calc(population, i, genotypes);
        }
        return ans;
    }

    public static float getMin(float[] fitness){
        float minValue = fitness[0];
        for(int i = 1; i < fitness.length; i++)
            if(fitness[i] < minValue) minValue = fitness[i];
        return minValue;
    }

    public static float getMax(float[] fitness){
        float maxValue = fitness[0];
        for(int i = 1; i < fitness.length; i++)
            if(fitness[i] > maxValue) maxValue = fitness[i];
        return maxValue;
    }

    public static float avg(float[] fit){
        float sum = 0;
        int len = fit.length;
        for(float i : fit)
            sum += i;
        return sum/len;
    }

    public static float calc(int[][]population, int row, int genotypes){
        float ans = 0;
        int[] chromosome = Crossover2D.copy(population, row);
        int len = chromosome.length;
        float[] variables = new float[genotypes];
        String[] positionsBin = new String[genotypes];
        String positionOne = null;
        int[] positionDec = new int[genotypes];
        int j = 0;
        for(int i = 0; i < len; i++){
            positionOne += String.valueOf(chromosome[i]);
            if(i % 19 == 0){
                positionsBin[j] = positionOne;
                j++;
                positionOne = null;
            }
        }
        for(int i = 0; i < genotypes; i++){
            positionDec[i] = Integer.parseInt(positionsBin[i], 2);
            variables[i] = GeneticOperator.decode(positionDec[i]);
        }
        ans = Functions.rosenbrock(variables);
        return ans;
    }
}
