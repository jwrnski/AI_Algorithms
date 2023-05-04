package GeneticAlgorithm;

import java.util.Arrays;

public class GeneticOperator {

    /*
     ai - lower value of range
     bi - upper value of range
     d - precision of encoding (d decimal places)
     mi - minimum amount of bits needed to encode [ai;bi] with d precision
    */
    public static float ai = -2, bi = 2, d = 5;
    public static int mi = (int)Math.ceil((Math.log((bi - ai) * Math.pow(10, d)) / Math.log(2)));
    public static byte[] vector = new byte[mi*2];
    public static int position;
    public static float x1, x2;

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

    public static void mutate(byte[] vector){

    }

    // Fills in array with randomly generated 0s and 1s.
    // Checks if the generated value is not bigger than upper bound (400001). If not, generates again.
    public static void generateBin(){
        for(int i=0; i<mi; i++)
            vector[i] = (byte)Math.round(Math.random());
        position = Integer.parseInt(toString(vector),2);
        if(!checkWithinBounds(position)) generateBin();
    }

    // Convert our array with chromosome into String in order to convert binary value into decimal value.
    public static String toString(byte[] vector){
        StringBuilder bin = new StringBuilder();
        for (byte b : vector) bin.append(b);
        return bin.toString();
    }

    // Check if the randomly generated value does not exceed 400001.
    public static boolean checkWithinBounds(int position){
        return position <= 400001;
    }
    public static void main(String[] args) {
        generateBin();
        System.out.println(Arrays.toString(vector));
        System.out.println(position);
        System.out.println(decode(position));
    }
}
