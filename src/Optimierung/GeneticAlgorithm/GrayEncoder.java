package Optimierung.GeneticAlgorithm;

public class GrayEncoder extends BinaryEncoder {

    public GrayEncoder(BlackBox bb){
        super(bb);
    }

    @Override
    public String encode(int[] gene) {
        return null;
    }

    @Override
    public int[] decode(String gene) {
        return new int[0];
    }
}
