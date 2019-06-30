package Optimierung.GeneticAlgorithm;

public class GrayEncoder extends BinaryEncoder {

    public GrayEncoder(BlackBox bb){
        super(bb);
    }

    private String toGrayCode(int n){
        return  Integer.toBinaryString(n ^ (n >>> 1));
    }

    private int toInt(String code){
        int n = Integer.parseInt(code, 2);
        int p = n;
        while ((n >>>= 1) != 0)
            p ^= n;
        return p;
    }


    @Override
    public String encode(double[] genome) {
        String output = "";
        for(double g: genome){
            int sampledDouble = (int)((g - this.min) / this.step);
            String bin = this.toGrayCode(sampledDouble);
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
            decodedGenome[i] = this.toInt(
                    genome.substring(i*this.genomeLen, (i+1)*this.genomeLen));

        double[] output = new double[this.tupleSize];
        for(int i = 0; i < this.tupleSize; i++)
            output[i] = decodedGenome[i] * this.step + this.min;

        return output;
    }
}
