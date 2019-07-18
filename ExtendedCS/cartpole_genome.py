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
