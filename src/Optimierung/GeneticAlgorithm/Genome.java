package Optimierung.GeneticAlgorithm;

import java.util.Random;

public class Genome implements Comparable<Genome> {

    private BinaryEncoder encoder;
    private double modelOutput;
    private String genomeSequence;
    private BlackBox bb;
    private Random random;

    public Genome(String encoderType, BlackBox bb, Random random){
        this.bb = bb;
        this.random = random;
        if(encoderType.equals("simple"))
            this.encoder = new SimpleEncoder(bb);
        else if(encoderType.equals("gray"))
            this.encoder = new GrayEncoder(bb);

        this.setRandomGenome();
    }

    public void setRandomGenome(){
        double[] randStart = new double[bb.getTupleSize()];
        for(int i = 0; i < bb.getTupleSize(); i++)
            randStart[i] = bb.getMin() + random.nextDouble() * (bb.getMax() - bb.getMin());
        this.genomeSequence = encoder.encode(randStart);
    }

    public int compareTo(Genome compareGenome) {
        if(this.getModelOutput() - compareGenome.getModelOutput() > 0)
            return 1;
        if(this.getModelOutput() - compareGenome.getModelOutput() < 0)
            return -1;

        return 0;
    }

    public double getModelOutput() {
        return modelOutput;
    }

    public void setModelOutput(double modelOutput) {
        this.modelOutput = modelOutput;
    }

    public String getGenomeSequence() {
        return genomeSequence;
    }

    public void setGenomeSequence(String genomeSequence) {
        this.genomeSequence = genomeSequence;
    }

    public double[] getModelInput() {
        return this.encoder.decode(this.genomeSequence);
    }
}
