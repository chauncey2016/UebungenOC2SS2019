package Optimierung.GeneticAlgorithm;

import Optimierung.DockerAdapter;

public class GeneticAlgorithm {

    private BinaryEncoder encoder;
    private String[] population;

    public GeneticAlgorithm(BinaryEncoder encoder, int populationSize){
        this.encoder = encoder;
        this.population = new String[populationSize];
    }

    public void train(DockerAdapter adapter, int iterations, float threshold){
        this.initPopulation();
        for(int i = 0; i < iterations; i++){
        	String[] parents = this.selectParents();
        	String[] children = this.crossover();
        	String[] mutants = this.mutation(threshold);
        }
    }

    private void initPopulation(){
        for(int i = 0; i < population.length; i++)
            population[i] = this.encoder.random();
    }

    private String[] selectParents(){
        return null;
    }

    private String[] crossover(){
        return null;
    }

    private String[] mutation(float threshold){
        return null;
    }

    private void killBottom(){

    }
}
