package Optimierung.GeneticAlgorithm;


public abstract class BinaryEncoder {

    protected int tupleSize;
    protected int genomeLen;
    protected double min;
    protected double max;
    protected double step;

    public BinaryEncoder(BlackBox bb){
        this.min = bb.getMin();
        this.max = bb.getMax();
        this.step = bb.getStep();
        this.tupleSize = bb.getTupleSize();
        this.genomeLen = (int)Math.ceil(Math.log((max-min)/step)/Math.log(2));
    }

    public abstract String encode(double[] gene);
    public abstract double[] decode(String gene);
}
