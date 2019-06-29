package Optimierung.GeneticAlgorithm;

import Optimierung.DockerAdapter;

import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {

    private Genome[] population;
    private Random random;

    private BlackBox bb;

    public GeneticAlgorithm(int populationSize, int seed, BlackBox bb){
        this.population = new Genome[populationSize];
        random = new Random(seed);
        this.bb = bb;
    }

    public String[] train(DockerAdapter adapter, int iterations, int parentCount,
                      double thresholdMutation, String encoder){
        assert (parentCount % 2) == 0: "Even number of parents needed";
        if((parentCount % 2) != 0) parentCount -= 1;
        String[] fitnessHistory = new String[iterations];
        this.initPopulation(encoder, this.population);
        this.checkFitness(adapter, this.population);
        for(int i = 0; i < iterations; i++){
            Genome[] parents = this.selectParents(parentCount);
            Genome[] children = new Genome[parentCount];
            this.initPopulation(encoder, children);
            this.crossover(parents, children);
            this.mutation(thresholdMutation, children);
            this.checkFitness(adapter, children);
            this.population = this.selectNextGeneration(children, adapter);

            fitnessHistory[i] = Double.toString(this.population[0].getModelOutput());
        }

        return fitnessHistory;
    }

    private void initPopulation(String encoder, Genome[] population){
        for(int i = 0; i < population.length; i++)
            population[i] = new Genome(encoder, this.bb, this.random);
    }

    private Genome[] selectParents(int parentCount){
        return rouletteWheel(parentCount);
    }

    private double[] rankingSelection(){
        Arrays.sort(this.population);
        double[] rankingDistribution = new double[this.population.length];
        for(int i = 1; i <= this.population.length; i++){
            rankingDistribution[i-1] = (1 - Math.exp(-i)) / this.population.length;
            if(i > 1)
                rankingDistribution[i-1] += rankingDistribution[i-2];
            if(i == this.population.length)
                rankingDistribution[i-1] = 1.1;
        }
        return rankingDistribution;
    }

    private Genome[] rouletteWheel(int parentCount){
        Genome[] parents = new Genome[parentCount];
        double[] wheelDistribution = rankingSelection();
        double q = random.nextDouble() * (1.0 / parentCount);
        for(int currentMember = 0, i = 0; currentMember < parentCount; i++){
            while(q <= wheelDistribution[i] && currentMember < parentCount){
                parents[currentMember] = this.population[i];
                q += (1.0 / parentCount);
                currentMember++;
            }
        }
        return parents;
    }

    private Genome[] crossover(Genome[] parents, Genome[] children){
        return uniformCrossover(parents, children);
    }

    private Genome[] uniformCrossover(Genome[] parents, Genome[] children){
        for(int p1 = 0, p2 = 1; p1 < parents.length; p1+=2, p2+=2){
            char[] s1 = parents[p1].getGenomeSequence().toCharArray();
            char[] s2 = parents[p2].getGenomeSequence().toCharArray();
            for(int i = 0; i < s1.length; i++){
                if(random.nextFloat() < 0.5){
                    char temp = s1[i];
                    s1[i] = s2[i];
                    s2[i] = temp;
                }
            }
            children[p1].setGenomeSequence(new String(s1));
            children[p2].setGenomeSequence(new String(s2));
        }
        return children;
    }

    private void mutation(double threshold, Genome[] children){
        for(int c = 0; c < children.length; c++){
            char[] s = children[c].getGenomeSequence().toCharArray();
            for(int i = 0; i < s.length; i++){
                if(random.nextDouble() < threshold)
                    s[i] = s[i] == '0' ? '1' : '0';
            }
            children[c].setGenomeSequence(new String(s));
        }
    }

    private Genome[] selectNextGeneration(Genome[] children, DockerAdapter adapter){
        Genome[] combinedPopulation = new Genome[this.population.length + children.length];
        System.arraycopy(this.population, 0, combinedPopulation, 0, this.population.length);
        System.arraycopy(children, 0, combinedPopulation, this.population.length, children.length);
        Arrays.sort(combinedPopulation);
        return Arrays.copyOfRange(combinedPopulation, 0, this.population.length);
    }

    private void checkFitness(DockerAdapter adapter, Genome[] population){
        for(int i = 0; i < population.length; i++){
            double[] input = population[i].getModelInput();
            double output = adapter.nextVal(input);
            population[i].setModelOutput(output);
        }
    }
}
