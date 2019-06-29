package Optimierung.GeneticAlgorithm;

public class Gene implements Comparable<Gene> {

    private float[] modelInput;
    private float modelOutput;
    private String geneSequence;

    private int tupleSize;
    private float min, max;

    public Gene(BinaryEncoder encoder){

    }

    public void setRandomGene(){

    }

    public int compareTo(Gene compareGene) {
        if(this.getModelOutput() - compareGene.getModelOutput() > 0)
            return 1;
        if(this.getModelOutput() - compareGene.getModelOutput() < 0)
            return -1;

        return 0;
    }

    public float getModelOutput() {
        return modelOutput;
    }

    public void setModelOutput(float modelOutput) {
        this.modelOutput = modelOutput;
    }

    public String getGeneSequence() {
        return geneSequence;
    }

    public void setGeneSequence(String geneSequence) {
        this.geneSequence = geneSequence;
    }
}
