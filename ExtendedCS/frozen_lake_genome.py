import math


class FrozenLakeGenome():
    def __init__(self, params):
        self.params = params

    def encode(self, s):
        """
        s := state in [0, 15]
        """
        genome_len = math.ceil(
                    (self.params['max']-self.params['min'])
                    /self.params['step'])
        enc_s = ['0'] * genome_len        
        enc_s[s] = '1'
        return enc_s


    
