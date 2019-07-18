class FrozenLakeGenome():
    def __init__(self, params):
        self.params = params

    def encode(self, s):
        """
        s := state in [0, 15]
        """
        enc_s = ['0'] * 16
        enc_s[s] = '1'
        return enc_s


    
