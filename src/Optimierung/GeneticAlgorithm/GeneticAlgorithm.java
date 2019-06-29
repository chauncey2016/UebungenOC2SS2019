package Optimierung.GeneticAlgorithm;

import Optimierung.DockerAdapter;

import java.util.Arrays;

public class GeneticAlgorithm {

    private Gene[] population;

    public GeneticAlgorithm(int populationSize){
        this.population = new Gene[populationSize];
    }

    public void train(DockerAdapter adapter, int iterations, float threshold){
        this.initPopulation();
        for(int i = 0; i < iterations; i++){
            var parents = this.selectParents(population, 2);
            var children = this.crossover(parents);
            var mutants = this.mutation(threshold);
        }
    }

    private void initPopulation(){
        for(int i = 0; i < population.length; i++)
            population[i].setRandomGene();
    }

    /*using the elitist method*/
    private Gene[] selectParents(Gene[] population, int parentCount){
        Gene[] parents = new Gene[population.length];
        System.arraycopy(population, 0, parents, 0, population.length);
        Arrays.sort(parents);
        return Arrays.copyOfRange(parents, 0, parentCount);
    }

    private Gene[] crossover(Gene[] parents){
        return null;
    }

    private String[] mutation(float threshold){
        return null;
    }

}
