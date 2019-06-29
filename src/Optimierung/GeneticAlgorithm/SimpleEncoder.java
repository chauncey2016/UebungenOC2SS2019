package Optimierung.GeneticAlgorithm;


public class SimpleEncoder extends BinaryEncoder {

    public SimpleEncoder(BlackBox bb){
        super(bb);
    }

    @Override
    public String encode(double[] genome) {
        String output = "";
        for(double g: genome){
            int sampledDouble = (int)((g - this.min) / this.step);
            String bin = Integer.toBinaryString(sampledDouble);
            int diffLen = this.genomeLen - bin.length();
            for(int i = 0; i < diffLen; i++)
                output += '0';
            output += bin;
        }
        return output;
    }

    @Override
    public double[] decode(String genome) {
        int[] decodedGenome = new int[this.tupleSize];
        for(int i = 0; i < this.tupleSize; i++)
            decodedGenome[i] = Integer.parseInt(
                    genome.substring(i*this.genomeLen, (i+1)*this.genomeLen), 2);

        double[] output = new double[this.tupleSize];
        for(int i = 0; i < this.tupleSize; i++)
            output[i] = decodedGenome[i] * this.step + this.min;

        return output;
    }

}
