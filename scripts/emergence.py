import numpy as np


def emergence(entr1, entr2):
    return entr1 - entr2


def entropy(data):
    dict = {}
    for key in data:
        if str(key) in dict: dict[str(key)] += 1
        else:           dict[str(key)]  = 1
    return -np.sum([v/len(data) * np.log2(v/len(data)) for _, v in dict.items()])
    

def preprocess_pheromone(data, step=1000, minimum=0.0, maximum=0.0004):
    step = np.abs(minimum-maximum) / step
    preproc_data = []
    for x in range(data.size):
        for y in range(data.size):
            if x <= y: preproc_data.append((data[(x,y)] + minimum) // step)
    return preproc_data
 
  
def normalize_tour(tour):
    i = 0
    for x1, _ in tour:
        if x1 == 0: break
        i += 1
    return tour[i:] + tour[:i]
