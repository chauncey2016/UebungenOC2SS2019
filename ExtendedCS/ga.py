import random


class GA():
    
    def __init__(self, population_size=100):
        self.population_size = population_size

    
    def start_ga(self, population):
        if len(population) < 2: return

    
    def crossover(self, genome1, genome2):
        for i in range(len(genome1)):
            if random.random() < 0.5:
                temp = genome1[i]
                genome1[i] = genome2[i]
                genome2[i] = temp
        
        return (genome1, genome2)


    def mutation(self, genome, threshold=0.04):
        for i in range(len(genome)):
            if random.random() < threshold:
                if random.random() < 0.5:
                    if genome[i] == '0':
                        genome[i] = '1'
                    elif genome[i] == '1':
                        genome[i] = '.'
                    else:
                        genome[i] = '0'
                else:
                    if genome[i] == '0':
                        genome[i] = '.'
                    elif genome[i] == '1':
                        genome[i] = '0'
                    else:
                        genome[i] = '1'

        return genome


    def roulette_wheel(self):
        pass


    def parent_selection(self):
        pass


    def final_selection(self):
        pass
