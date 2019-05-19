from symmetric_matrix import SymmetricMatrix


class TSP:
    def __init__(self, size, seed):
        self.adjmatrix = SymmetricMatrix(size, seed, 'random')

