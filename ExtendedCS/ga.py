import random
import numpy as np


class GA():
    
    def __init__(self, population_size=10):
        self.population_size = population_size

    
    def start_ga(self, M_set):
        if len(M_set) < 2: return
        p1, p2, avg_val = self.parent_selection(M_set)
        child1, child2 = self.crossover(p1, p2)
        mutant1 = self.mutation(child1)
        mutant2 = self.mutation(child2)
        M_set[mutant1] = avg_val
        M_set[mutant2] = avg_val
        self.final_selection(M_set)
        print('done')

    
    def crossover(self, genome1, genome2):
        genome1_l = list(genome1)
        genome2_l = list(genome2)
        for i in range(len(genome1_l)):
            if random.random() < 0.5:
                temp = genome1[i]
                genome1_l[i] = genome2_l[i]
                genome2_l[i] = temp
        
        return (''.join(genome1_l), 
                ''.join(genome2_l))


    def mutation(self, genome, threshold=0.04):
        genome_l = list(genome)
        for i in range(len(genome_l)):
            if random.random() < threshold:
                if random.random() < 0.5:
                    if genome_l[i] == '0':
                        genome_l[i] = '1'
                    elif genome_l[i] == '1':
                        genome_l[i] = '.'
                    else:
                        genome_l[i] = '0'
                else:
                    if genome_l[i] == '0':
                        genome_l[i] = '.'
                    elif genome_l[i] == '1':
                        genome_l[i] = '0'
                    else:
                        genome_l[i] = '1'

        return ''.join(genome_l)


    def softmax(self, M_set, tau=0.1):
        div_func = np.vectorize(lambda x: x / tau)
        temp_actions = div_func(M_set)
        temp_actions = np.exp(temp_actions)
        norm_func = np.vectorize(lambda x, y: x / y)
        norm_actions = norm_func(temp_actions, np.sum(temp_actions))
        return norm_actions


    def roulette_wheel(self, distri):
        sum = 0
        q = random.uniform(0, 1)
        for i in range(distri.size):
            sum += distri[i]
            if q <= sum: return i
        return distri.size


    def parent_selection(self, M_set):
        sorted_g = sorted(M_set.items())
        distri = self.softmax([v for _, v in sorted_g])
        p1 = self.roulette_wheel(distri)
        p2 = self.roulette_wheel(distri)
        p1, p2 = sorted_g[p1][0], sorted_g[p2][0]
        return (p1, p2, (M_set[p1]+M_set[p2])/2.0)


    def final_selection(self, M_set):
        if len(M_set) < self.population_size: return
        sorted_pop = sorted(M_set.items(), 
                     key=lambda item: item[1])
        for i in range(len(M_set)-self.population_size):
            M_set.pop(sorted_pop[i][0])
            
            
