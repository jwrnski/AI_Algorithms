package GeneticAlgorithm;

public class Functions {

    public static float rosenbrock(float x1, float x2){
        float ans = 0;
        float[] input = {x1, x2};
        for(int i = 0; i < input.length - 1; i++){
            ans += 100*Math.pow((input[i+1] - Math.pow(input[i], 2)), 2) + Math.pow((1 - input[i]), 2);
        }
        //System.out.println(ans);
        return ans;
    }
    public static void rosenbrock(double x1, double x2,double x3, double x4,double x5){

    }
    public static void rosenbrock(double x1, double x2, double x3, double x4, double x5, double x6, double x7, double x8, double x9, double x10){
        double ans = 0;
        double[] input = {x1, x2, x3, x4, x5, x6, x7, x8, x9, x10};
        for(int i = 0; i < input.length - 1; i++){
            ans += 100*Math.pow((input[i+1] - Math.pow(input[i], 2)), 2) + Math.pow((1 - input[i]), 2);
        }

    }


}
