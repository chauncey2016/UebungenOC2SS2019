package Optimierung.GeneticAlgorithm;

public class GrayEncoder extends BinaryEncoder {

    public GrayEncoder(float min, float max, int tupleSize, float step, int seed){
        super(min, max, tupleSize, step, seed);
    }

    @Override
    public String encode(int[] gene) {
        return null;
    }

    @Override
    public int[] decode(String gene) {
        return new int[0];
    }

    @Override
    public String random(){
        return null;
    }
}
