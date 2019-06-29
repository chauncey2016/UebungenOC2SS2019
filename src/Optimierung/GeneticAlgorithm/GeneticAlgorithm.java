package Optimierung.GeneticAlgorithm;

import Optimierung.DockerAdapter;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {

    private Gene[] population;
    private Random random;

    private BlackBox bb;

    public GeneticAlgorithm(int populationSize, int seed, BlackBox bb){
        this.population = new Gene[populationSize];
        random = new Random(seed);
        this.bb = bb;
    }

    public void train(DockerAdapter adapter, int iterations, int parentCount, float threshold, String encoder){
        assert(parentCount % 2 == 0);
        this.initPopulation(encoder);
        this.checkFitness(adapter, this.population);
        for(int i = 0; i < iterations; i++){
            Gene[] parents = this.selectParents(parentCount);
            Gene[] children = this.crossover(parents);
            this.mutation(threshold, children);
            this.checkFitness(adapter, children);
            this.population = this.selectNextGeneration(children, adapter);
        }
    }

    private void initPopulation(String encoder){
        for(int i = 0; i < population.length; i++)
            population[i] = new Gene(encoder, this.bb);
    }

    private Gene[] selectParents(int parentCount){
        return rouletteWheel(parentCount);
    }

    private double[] rankingSelection(){
        Arrays.sort(this.population);
        double[] rankingDistribution = new double[this.population.length];
        for(int i = 0; i < this.population.length; i++){
            rankingDistribution[i] = (1 - Math.exp(-i)) / this.population.length;
        }
        return rankingDistribution;
    }

    private Gene[] rouletteWheel(int parentCount){
        Gene[] parents = new Gene[parentCount];
        double[] wheelDistribution = rankingSelection();
        double q = random.nextDouble() * (1.0 / parentCount);
        for(int currentMember = 0, i = 0; currentMember < parentCount; i++){
            while(q < wheelDistribution[i]){
                parents[currentMember] = this.population[i];
                q += 1.0 / parentCount;
                currentMember++;
            }
        }
        return parents;
    }

    private Gene[] crossover(Gene[] parents){
        return uniformCrossover(parents);
    }

    private Gene[] uniformCrossover(Gene[] parents){
        Gene[] children = new Gene[parents.length];
        for(int p1 = 0, p2 = 1; p1 < parents.length; p1+=2, p2+=2){
            char[] s1 = parents[p1].getGeneSequence().toCharArray();
            char[] s2 = parents[p2].getGeneSequence().toCharArray();
            for(int i = 0; i < s1.length; i++){
                if(random.nextFloat() < 0.5){
                    char temp = s1[i];
                    s1[i] = s2[i];
                    s2[i] = temp;
                }
            }
            children[p1].setGeneSequence(new String(s1));
            children[p2].setGeneSequence(new String(s2));
        }
        return children;
    }

    private void mutation(float threshold, Gene[] children){
        for(int c = 0; c < children.length; c++){
            char[] s = children[c].getGeneSequence().toCharArray();
            for(int i = 0; i < s.length; i++){
                if(random.nextFloat() < threshold)
                    s[i] = s[i] == '0' ? '1' : '0';
            }
            children[c].setGeneSequence(new String(s));
        }
    }

    private Gene[] selectNextGeneration(Gene[] children, DockerAdapter adapter){
        Gene[] combinedPopulation = new Gene[this.population.length + children.length];
        System.arraycopy(this.population, 0, combinedPopulation, 0, this.population.length);
        System.arraycopy(children, 0, combinedPopulation, this.population.length, children.length);
        Arrays.sort(combinedPopulation);
        return Arrays.copyOfRange(combinedPopulation, 0, this.population.length);
    }

    private void checkFitness(DockerAdapter adapter, Gene[] population){
        for(int i = 0; i < population.length; i++){
            double[] input = population[i].getModelInput();
            double output = adapter.nextVal(input);
            population[i].setModelOutput(output);
        }
    }
}
