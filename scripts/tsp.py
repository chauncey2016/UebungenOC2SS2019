from symmetric_matrix import SymmetricMatrix


class TSP:
    def __init__(self, cities, seed):
        self.adjmatrix = SymmetricMatrix(cities, seed, 'random')

