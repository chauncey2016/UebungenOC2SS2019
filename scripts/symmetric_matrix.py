import numpy as np


class SymmetricMatrix:
    def __init__(self, size, seed=0, mode='default', value=0):
        np.random.seed(seed)
        self.size = size
        self.seed = seed
        if mode == 'random':
            self.__adjmatrix = np.random.randint(1, 1000, (size, size))
        else:
            self.__adjmatrix = np.full((size, size), value)


    def __getitem__(self, tuple_xy):
        """overwrite [(x, y)] operator for easy variable access"""
        x, y = tuple_xy
        return self.__adjmatrix[x][y] if x <= y else self.__adjmatrix[y][x]


    def __setitem__(self, tuple_xy, value):
        x, y = tuple_xy
        if x <= y: self.__adjmatrix[x][y] = value
        else: self.__adjmatrix[y][x] = value

    
    def __str__(self):
        build_str = ['']
        for x in range(self.size):
            for y in range(self.size):
                if x <= y: build_str.append(str(self.__adjmatrix[x][y]))
                else:      build_str.append(str(self.__adjmatrix[y][x]))
            build_str.append('\n')
        return 'Symmetric Matrix with size ' + str(self.size) + 'x' + str(self.size) + \
               ' and seed ' + str(self.seed) + ':\n' + '\t'.join(build_str) 
