package GeneticAlgorithm;

public class FitnessEvaluation {

    public static float[] getAns(int[][] population){
        int len = population.length;
        float[] ans = new float[len];
        for(int i = 0; i < len; i++){
            ans[i] = calc(population, i);
        }
        return ans;
    }

    public static float getMax(float[] fitness){
        float maxValue = fitness[0];
        for(int i = 1; i < fitness.length; i++)
            if(fitness[i] > maxValue) maxValue = fitness[i];
        return maxValue;
    }

    public static float calc(int[][]population, int row){
        float ans = 0;
        int[] chromosome = Crossover2D.copy(population, row);
        int len = chromosome.length;
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
        int position1 = Integer.parseInt(GeneticOperator.toString(genotype1), 2);
        int position2 = Integer.parseInt(GeneticOperator.toString(genotype2), 2);
        //System.out.println(position1 + "\t" + position2);
        float x1 = GeneticOperator.decode(position1);
        float x2 = GeneticOperator.decode(position2);
        //System.out.println(x1 + "\t" + x2);
        ans = GeneticOperator.function(x1, x2);
        return ans;
    }
}
