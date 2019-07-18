import re
import random
import numpy as np
from genome_factory import GenomeFactory


class LCSTable():
    def __init__(self, mode, actions_n, params):
        self.genome = GenomeFactory(mode, params)
        self.actions_n = actions_n
        self.table = {}


    def update_table_entries(self, state, action, delta):
        A_set = self.lookup(state)
        for k in A_set:
            self.table[k][action] += delta


    def get_action_set(self, s):
        M_set = self.lookup(s)
        if len(M_set) == 0:
            M_set = self.cover(s)
        A_set = self.eval_actions(M_set)
        return A_set


    def eval_actions(self, M_set):
        actions = []
        for k in M_set:
            actions.append(self.table[k])
        return np.average(actions, 0)


    def lookup(self, s):
        #print(self.table)
        enc_s = self.genome.encode(s)
        return [k for k in self.table if re.match(k, ''.join(enc_s))]


    def cover(self, s, wildcard_th=0.0, init_value=0.5):
        """
        create new rules if non exisiting
        """
        enc_s = self.genome.encode(s)
        for i in range(len(enc_s)):
            if random.random() < wildcard_th:
                enc_s[i] = '.' 
        init_values =  np.full(self.actions_n, init_value)
        key_genome = ''.join(enc_s)
        self.table[key_genome] = init_values
        return [key_genome]
        


