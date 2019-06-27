package Optimierung.GeneticAlgorithm;


import java.util.Random;

public abstract class BinaryEncoder {

    protected int tupleSize;
    protected int geneLen;
    protected float min;
    protected float max;
    protected float step;
    protected Random rand;

    public BinaryEncoder(float min, float max, int tupleSize, float step, int seed){
        this.min = min;
        this.max = max;
        this.step = step;
        this.rand = new Random(seed);
        this.tupleSize = tupleSize;
        this.geneLen = (int)Math.pow((max-min)/step, tupleSize);
    }

    public abstract String encode(int[] gene);
    public abstract int[] decode(String gene);
    public abstract String random();
}
