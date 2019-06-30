package Optimierung.GeneticAlgorithm;

import Optimierung.GeneticAlgorithm.BlackBox;

import java.util.Random;

public class GenomeA3 implements Comparable<GenomeA3> {

    private double modelOutput;
    private double[] genomeSequence;//genome sequence of double
    private BlackBox bb;
    private Random random;

    public GenomeA3(BlackBox bb, Random random){
        this.bb = bb;
        this.random = random;

        this.setRandomGenome();
    }

    public void setRandomGenome(){
        double[] randStart = new double[bb.getTupleSize()];
        for(int i = 0; i < bb.getTupleSize(); i++)
            randStart[i] = bb.getMin() + random.nextDouble() * (bb.getMax() - bb.getMin());
        this.genomeSequence = randStart;
    }

    public int compareTo(GenomeA3 compareGenomeA3) {
        if(this.getModelOutput() - compareGenomeA3.getModelOutput() > 0)
            return 1;
        if(this.getModelOutput() - compareGenomeA3.getModelOutput() < 0)
            return -1;

        return 0;
    }

    public double getModelOutput() {
        return modelOutput;
    }

    public void setModelOutput(double modelOutput) {
        this.modelOutput = modelOutput;
    }

    public double[] getGenomeSequence() {
		return genomeSequence;
	}

	public void setGenomeSequence(double[] genomeSequence) {
		this.genomeSequence = genomeSequence;
	}

	public double[] getModelInput() {
		
		return genomeSequence;
    }
}
