import re
import random
import numpy as np
from ga import GA
from genome_factory import GenomeFactory


class LCSTable():
    def __init__(self, mode, actions_n, params, population=4):
        self.genome = GenomeFactory(mode, params)
        self.actions_n = actions_n
        self.table = [{} for _ in range(actions_n)]
        self.ga = GA(population)


    def start_ga(self, state, action):
        M_set, _ = self.lookup(state)
        self.ga.start_ga(M_set[action])


    def update_table_entries(self, state, action, delta):
        M_set, _ = self.lookup(state)
        for k in M_set[action]:
            self.table[action][k] += delta


    def get_action_set(self, s):
        M_set, hits = self.lookup(s)
        if hits == 0:
            self.cover(s)
            M_set, _ = self.lookup(s)
        A_set = self.eval_actions(M_set)
        return A_set


    def eval_actions(self, M_set):
        actions = []
        for i in range(self.actions_n):
            sum = 0
            for _, value in M_set[i].items():
                sum += value
            sum = sum/len(M_set[i]) if len(M_set[i]) > 0 else 0            
            actions.append(sum)
        return actions
        

    def lookup(self, s):
        enc_s = self.genome.encode(s)
        M_set = [[] for _ in range(self.actions_n)]
        hits = 0
        for i in range(self.actions_n):
            M_set[i] = {k: self.table[i][k] 
                        for k in self.table[i] if re.match(k, ''.join(enc_s))}
            hits += len(M_set[i])
        return M_set, hits


    def cover(self, s, wildcard_th=0.1, init_value=0.5):
        """
        create new rules if non exisiting
        """
        enc_s = self.genome.encode(s)
        for i in range(len(enc_s)):
            if random.random() < wildcard_th:
                enc_s[i] = '.' 
        key_genome = ''.join(enc_s)
        for i in range(self.actions_n):
            self.table[i][key_genome] = init_value
        


