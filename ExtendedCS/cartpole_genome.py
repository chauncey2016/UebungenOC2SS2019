import math


class CartPoleGenome():
    def __init__(self, params):
        self.params = params

    
    def encode(self, state):
        """
        s1 := Cart Position in [-2.4, 2.4]
        s2 := Cart Velocity in [-Inf, Inf]
        s3 := Pole Angle in [-41.8, 41.8]
        s4 := Pole Velocity At Tip in [-Inf, Inf]
        """
        s1, s2, s3, s4 = state
        pos_min = self.params['pos']['min']
        pos_max = self.params['pos']['max']
        pos_step = self.params['pos']['step']
        
        cvel_min = self.params['cvel']['min']
        cvel_max = self.params['cvel']['max']
        cvel_step = self.params['cvel']['step']

        angle_min = self.params['angle']['min']
        angle_max = self.params['angle']['max']
        angle_step = self.params['angle']['step']

        pvel_min = self.params['pvel']['min']
        pvel_max = self.params['pvel']['max']
        pvel_step = self.params['pvel']['step']

        genome_len_pos = math.ceil((pos_max-pos_min)/pos_step)
        genome_len_cvel = math.ceil((cvel_max-cvel_min)/cvel_step)
        genome_len_angle = math.ceil((angle_max-angle_min)/angle_step)
        genome_len_pvel = math.ceil((pvel_max-pvel_min)/pvel_step)

        genome_pos = ['0'] * genome_len_pos
        comp = pos_min
        for i in range(genome_len_pos):
            if s1 < comp: 
                genome_pos[i] = '1'
                break
            comp += pos_step

        genome_cvel = ['0'] * genome_len_cvel
        comp = cvel_min
        for i in range(genome_len_cvel):
            if s1 < comp: 
                genome_cvel[i] = '1'
                break
            comp += cvel_step
 
        genome_angle = ['0'] * genome_len_angle
        comp = angle_min
        for i in range(genome_len_angle):
            if s1 < comp: 
                genome_angle[i] = '1'
                break
            comp += angle_step

        genome_pvel = ['0'] * genome_len_pvel
        comp = pvel_min
        for i in range(genome_len_pvel):
            if s1 < comp: 
                genome_pvel[i] = '1'
                break
            comp += pvel_step

        return genome_pos + genome_cvel + genome_angle + genome_pvel
        #return genome_cvel + genome_angle + genome_pvel
