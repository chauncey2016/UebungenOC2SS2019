package Optimierung.GeneticAlgorithm;

public class BlackBox {

    private double min;
    private double max;
    private double step;
    private int tupleSize;

    public BlackBox(double min, double max, double step, int tupleSize){
        this.setMin(min);
        this.setMax(max);
        this.setStep(step);
        this.setTupleSize(tupleSize);
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public int getTupleSize() {
        return tupleSize;
    }

    public void setTupleSize(int tupleSize) {
        this.tupleSize = tupleSize;
    }
}
