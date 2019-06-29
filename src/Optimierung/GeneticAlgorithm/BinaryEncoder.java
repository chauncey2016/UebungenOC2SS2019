package Optimierung.GeneticAlgorithm;


public abstract class BinaryEncoder {

    protected int tupleSize;
    protected int geneLen;
    protected double min;
    protected double max;
    protected double step;

    public BinaryEncoder(BlackBox bb){
        this.min = bb.getMin();
        this.max = bb.getMax();
        this.step = bb.getStep();
        this.tupleSize = bb.getTupleSize();
        this.geneLen = (int)Math.pow((max-min)/step, tupleSize);
    }

    public abstract String encode(int[] gene);
    public abstract int[] decode(String gene);
}
