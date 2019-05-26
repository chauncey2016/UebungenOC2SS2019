import numpy as np

def emergence():
    pass


def entropy(data, classes):
    dict = {}
    for key in data:
        if key in dict: dict[key] += 1
        else:           dict[key]  = 1
    return np.sum([v/classes np.log2(v/classes) for _, v in dict.items()])
    


def preprocess_continous(data, step, minimum, maximum):
    pass
