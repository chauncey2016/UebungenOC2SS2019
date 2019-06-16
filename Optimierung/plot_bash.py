
def decode_bb(bb):
    result = []
    filename = 'output/bb{}_'.format(bb)
    for i in range(1, 11):
        with open(filename+str(i)+'.txt') as output, open(filename+str(i)+'_input.txt') as input: 
            cost = output.readlines()
            opti = input.readlines()
            cost = [float(c.strip('\n')) for c in cost] 
            opti = [float(o.split()[0]) for o in opti]

            best_cost = 0
            temp_result = []
            for i in range(len(cost)):
                current_cost = cost[i]
                if i == 0 or current_cost < best_cost:
                    best_cost = current_cost
                    temp_result.append(opti[i])
                else: temp_result.append(temp_result[-1])
            result.append(temp_result)
            
    return result


import numpy as np
import matplotlib.pyplot as plt

for i in range(1, 6):
    decoded_data = decode_bb(i)
    temp_data = decoded_data[0]
    for i in range(1, 10):
        for j in range(300):
            temp_data[j] += decoded_data[i][j]

    for i in range(300):
        temp_data[i] /= 10

    plt.plot(temp_data)
    plt.show()
