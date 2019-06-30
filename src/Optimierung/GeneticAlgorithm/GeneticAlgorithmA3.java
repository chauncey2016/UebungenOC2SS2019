package Optimierung.GeneticAlgorithm;;

import java.util.Arrays;
import java.util.Random;

import Optimierung.DockerAdapter;
import Optimierung.GeneticAlgorithm.BlackBox;

public class GeneticAlgorithmA3 {

    private GenomeA3[] population;
    private Random random;

    private BlackBox bb;

    public GeneticAlgorithmA3(int populationSize, int seed, BlackBox bb){
        this.population = new GenomeA3[populationSize];
        random = new Random(seed);
        this.bb = bb;
    }

    public String[] train(DockerAdapter adapter, int iterations, int parentCount,
                      double thresholdMutation, double mutationStep){
        assert (parentCount % 2) == 0: "Even number of parents needed";
        if((parentCount % 2) != 0) parentCount -= 1;
        String[] fitnessHistory = new String[iterations];
        this.initPopulation(this.population);
        this.checkFitness(adapter, this.population);
        for(int i = 0; i < iterations; i++){
            GenomeA3[] parents = this.selectParents(parentCount);
            GenomeA3[] children = new GenomeA3[parentCount];
            this.initPopulation(children);
            this.crossover(parents, children);
            this.mutation(thresholdMutation, children, mutationStep);
            this.checkFitness(adapter, children);
            this.population = this.selectNextGeneration(children, adapter);

            fitnessHistory[i] = Double.toString(this.population[0].getModelOutput());
        }

        return fitnessHistory;
    }

    private void initPopulation(GenomeA3[] population){
        for(int i = 0; i < population.length; i++)
            population[i] = new GenomeA3(this.bb, this.random);
    }

    private GenomeA3[] selectParents(int parentCount){
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

    private GenomeA3[] rouletteWheel(int parentCount){
        GenomeA3[] parents = new GenomeA3[parentCount];
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

    private GenomeA3[] crossover(GenomeA3[] parents, GenomeA3[] children){
        return uniformCrossover(parents, children);
    }

    private GenomeA3[] uniformCrossover(GenomeA3[] parents, GenomeA3[] children){
        for(int p1 = 0, p2 = 1; p1 < parents.length; p1+=2, p2+=2){
            double[] s1 = parents[p1].getGenomeSequence();
            double[] s2 = parents[p2].getGenomeSequence();
            for(int i = 0; i < s1.length; i++){
                if(random.nextFloat() < 0.5){
                    double temp = s1[i];
                    s1[i] = s2[i];
                    s2[i] = temp;
                }
            }
            children[p1].setGenomeSequence(s1);
            children[p2].setGenomeSequence(s2);
        }
        return children;
    }

    private void mutation(double threshold, GenomeA3[] children, double mutationStep){
        for(int c = 0; c < children.length; c++){
            double[] s = children[c].getGenomeSequence();
            for(int i = 0; i < s.length; i++){
                if(random.nextDouble() < threshold){
                   double temp = -mutationStep + random.nextDouble() * (2*mutationStep);
                	s[i] += temp;
                }
            }
            children[c].setGenomeSequence(s);
        }
    }

    private GenomeA3[] selectNextGeneration(GenomeA3[] children, DockerAdapter adapter){
        GenomeA3[] combinedPopulation = new GenomeA3[this.population.length + children.length];
        System.arraycopy(this.population, 0, combinedPopulation, 0, this.population.length);
        System.arraycopy(children, 0, combinedPopulation, this.population.length, children.length);
        Arrays.sort(combinedPopulation);
        return Arrays.copyOfRange(combinedPopulation, 0, this.population.length);
    }

    private void checkFitness(DockerAdapter adapter, GenomeA3[] population){
        for(int i = 0; i < population.length; i++){
            double[] input = population[i].getModelInput();
            double output = adapter.nextVal(input);
            population[i].setModelOutput(output);
        }
    }
}
