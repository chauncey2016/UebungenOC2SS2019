package Optimierung.GeneticAlgorithm;

public class Gene implements Comparable<Gene> {

    private BinaryEncoder encoder;
    private double[] modelInput;
    private double modelOutput;
    private String geneSequence;
    private BlackBox bb;

    public Gene(String encodertype, BlackBox bb){
        this.bb = bb;
        if(encodertype.equals("simple"))
            this.encoder = new SimpleEncoder(bb);
        else if(encodertype.equals("gray"))
            this.encoder = new GrayEncoder(bb);
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

    public double getModelOutput() {
        return modelOutput;
    }

    public void setModelOutput(double modelOutput) {
        this.modelOutput = modelOutput;
    }

    public String getGeneSequence() {
        return geneSequence;
    }

    public void setGeneSequence(String geneSequence) {
        this.geneSequence = geneSequence;
    }

    public double[] getModelInput() {
        return modelInput;
    }
}
