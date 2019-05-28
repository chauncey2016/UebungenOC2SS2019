import matplotlib.pyplot as plt
import numpy as np
import random

from symmetric_matrix import SymmetricMatrix
from emergence import emergence, entropy, preprocess_pheromone, normalize_tour


class Ant:
    def __init__(self, cities, myrand):
        self.tour = []
        self.cities = cities
        self.r_k1 = self.r_k = myrand.randrange(cities)
        self.city_set = {c for c in range(self.cities) if c != self.r_k1}


    def create_path(self, r_k, s_k):
        self.tour.append((r_k, s_k))
        self.city_set -= {s_k}
        self.r_k = s_k

    
    def reset(self):
        self.tour = []
        self.r_k = self.r_k1
        self.city_set = {c for c in range(self.cities) if c != self.r_k1}


class ACS:
    def __init__(self, tsp, iterations, seed=42, m=10, beta=0.2, q0=0.9, alpha=0.1, rho=0.1):
        """
        m := number of ants
        beta := determines importance between distance and phermones
        q0 := tradeoff between exploitation and exploration
        alpha := pheromone decay in global update rule
        rho := pheromone decay in local update rule
        tau := adj_matrix with current pheromone levels
        phi := adj_matrix with distances between cities 
        """
        self.seed = seed
        self.myrand = random.Random(seed)
        self.city_count = tsp.size
        self.iterations = iterations
        self.tau0 = self.nearest_neighbour_heuristic(self.city_count)
        self.tau = SymmetricMatrix(self.city_count, value=self.tau0)
        self.m, self.beta, self.alpha, self.q0, self.rho, self.phi = m, beta, alpha, q0, rho, tsp
        self.ants = [Ant(self.city_count, self.myrand) for a in range(m)]

        self.shortest_len_history = []
        self.shortest_route_history = []
        self.route_entropy_history = []
        self.phero_entropy_history = []
        self.city_entropy_history = []

    def go_ants(self):
        """
        starting-routine: plan route - local update - global update - return best path
        """
        for i in range(self.iterations):
            for a in self.ants:
                self.plan_route(a)
                self.local_update(a)
            self.global_update()
            for a in self.ants: a.reset()
            if i % 10 == 0 and i != 0: 
                emerg_city = emergence(self.city_entropy_history[i-10], self.city_entropy_history[i])
                emerg_route = emergence(self.route_entropy_history[i-10], self.route_entropy_history[i])
                emerg_phero = emergence(self.phero_entropy_history[i-10], self.phero_entropy_history[i])
                print('Emerg. city: ', emerg_city)
                print('Emerg. route: ', emerg_route)
                print('Emerg. phero: ', emerg_phero)


    def nearest_neighbour_heuristic(self, city_count):
        """
        median lenght, rough approximation
        """
        return 1.0 / (500.0 * city_count)


    def city_exploitation(self, ant):
        """
        rule (3): select next city using both pheromone-level and distance
        """
        transition_rule = \
            lambda u: self.tau[(ant.r_k, u)] * pow((1/self.phi[(ant.r_k, u)]), self.beta)
        vfunc = np.vectorize(transition_rule)
        temp_array = list(ant.city_set)
        index = np.argmax(vfunc(temp_array))
        return temp_array[index]
        

    def city_exploration(self, ant):
        """
        rule (1): select next city randomly using the probability distribution in (1)
        """
        transition_rule = \
            lambda u: self.tau[(ant.r_k, u)] * pow((1/self.phi[(ant.r_k, u)]), self.beta)
        normalize = lambda u, n: u / n
        vtransition = np.vectorize(transition_rule)
        vnormalize = np.vectorize(normalize) 
        temp_array = list(ant.city_set)
        trans_array = vtransition(temp_array)
        normal_array = vnormalize(trans_array, np.sum(trans_array)) 
        i, sum_entries, q = 0, 0, self.myrand.uniform(0, 1)
        while sum_entries < q and i < len(normal_array):
            sum_entries += normal_array[i]
            i += 1
        return temp_array[i-1]


    def plan_route(self, ant):
        """
        rule (3): route planing: choose between greedy exploitation and exploration
        """
        for i in range(1, self.city_count):
            r_k = ant.r_k
            q = self.myrand.uniform(0, 1)
            s_k = self.city_exploitation(ant) if q <= self.q0 else self.city_exploration(ant)
            ant.create_path(r_k, s_k)
        ant.create_path(ant.r_k, ant.r_k1)
        

    def local_update(self, ant):
        """
        rule (5): update localy visited paths using decay value rho and current pheromone-levels
        """
        for i in ant.tour:
            self.tau[i] = (1 - self.rho) * self.tau[i] + self.rho * self.tau0


    def global_update(self):
        """ 
        rule (4): update all paths using decay variable alpha,
                  increase phermone-levels of best route for more directed search
        """
        L_k = []
        for a in self.ants:
            L_k.append(np.sum([self.phi[xy] for xy in a.tour]))
        L_best, tour_best = np.min(L_k), self.ants[np.argmin(L_k)].tour
        for x in range(self.city_count):
            for y in range(x, self.city_count):
                self.tau[(x, y)] *= (1 - self.alpha) 
        for t in tour_best:
            self.tau[t] += self.alpha * (1 / L_best)
        
        #calc entropy
        self.shortest_len_history.append(L_best)
        self.shortest_route_history.append(tour_best)
        self.route_entropy_history.append(entropy([normalize_tour(a.tour) for a in self.ants]))
        self.phero_entropy_history.append(entropy(preprocess_pheromone(self.tau)))
        #just takin one city per iteration
        self.city_entropy_history.append(entropy([a.r_k for a in self.ants]))


    def greedy_selector(self):
        """
        greedy search of current tau, to find good route
        """
        r_k = 0 
        best_route = []
        cities_to_visit = [i for i in range(1, self.city_count)]
        for _ in range(1, self.city_count):
            s_ind = np.argmax([self.tau[(r_k, u)] for u in cities_to_visit])
            s_k = cities_to_visit.pop(s_ind)
            best_route.append((r_k, s_k))
            r_k = s_k
        best_route.append((r_k, 0))
        
        shortest_path = np.sum([self.phi[(p)] for p in best_route])
        return best_route, shortest_path
            

    def end_routine(self):
        best_route, route_len = self.greedy_selector()
        print('Best route: ', best_route)
        print('Length of route: ', route_len)

        with open(r'output/Hcity-'+str(self.seed), 'w') as fc \
             ,open(r'output/Hroute-'+str(self.seed), 'w') as fr \
             ,open(r'output/Hphero-'+str(self.seed), 'w') as fp \
             ,open(r'output/len-'+str(self.seed), 'w') as fl:
            for l in self.city_entropy_history: fc.write(str(l)+'\n')
            for l in self.route_entropy_history: fr.write(str(l)+'\n')
            for l in self.phero_entropy_history: fp.write(str(l)+'\n')
            for l in self.shortest_len_history: fl.write(str(l)+'\n')

