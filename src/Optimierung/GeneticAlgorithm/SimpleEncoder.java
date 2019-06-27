package Optimierung.GeneticAlgorithm;


public class SimpleEncoder extends BinaryEncoder {

    public SimpleEncoder(float min, float max, int tupleSize, float step, int seed){
        super(min, max, tupleSize, step, seed);
    }

    @Override
    public String encode(int[] gene) {
        String output = "";
        for(var g: gene)
            output += Integer.toBinaryString(g);

        return output;
    }

    @Override
    public int[] decode(String gene) {
        int step = this.geneLen / this.tupleSize;
        int[] output = new int[this.tupleSize];
        for(int i = 0; i < this.tupleSize; i++)
            output[i] = Integer.parseInt(gene.substring(i*step, (i+1)*step), 2);

        return output;
    }

    @Override
    public String random(){
        int[] output = new int[this.tupleSize];
        for(int i = 0; i < this.tupleSize; i++)
            output[i] = (int)((this.min + rand.nextFloat() * (this.max - this.min))/this.step);

        return this.encode(output);
    }
}
