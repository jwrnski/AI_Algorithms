package GeneticAlgorithm;

public class Functions {

    public static float rosenbrock(float[] variables){
        float ans = 0;
        for(int i = 0; i < variables.length - 1; i++){
            ans += 100*Math.pow((variables[i+1] - Math.pow(variables[i], 2)), 2) + Math.pow((1 - variables[i]), 2);
        }
        return ans;
    }
}
