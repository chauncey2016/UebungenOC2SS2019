import subprocess
def call_bb(myrand, bb):
    cmd = 'echo {} | docker run -i bb -b {}'.format(myrand, bb)
    process = subprocess.run(['bash', '-c', cmd], stdout=subprocess.PIPE, 
                            universal_newlines=True)
    return float(process.stdout)


import random
def bb_rand_gen(bb, max_rand):
    rand_gen = lambda: random.uniform(0, max_rand)
    if   bb == 1: return (str(rand_gen()) + ' ') * 3
    elif bb == 2: return (str(rand_gen()) + ' ') * 5
    elif bb == 3: return (str(rand_gen()) + ' ') * 2
    elif bb == 4: return (str(rand_gen()) + ' ') * 10
    elif bb == 5: return (str(rand_gen()) + ' ') * 2
    else: raise('Unknown BB')


def run_naive_optimum(bb, iterations=300, epochs=10):
    for i in range(epochs):
        current_cost = 0.0
        optimuml = []
        for j in range(iterations):
            myrand = bb_rand_gen(bb, 10000)
            cost = call_bb(myrand, bb)
            if j == 0 or cost < current_cost:
                current_cost = cost
                optimuml.append(myrand.split()[0])
            else: optimuml.append(optimuml[-1])

        file_name = 'py_output/bb{}_{}.txt'.format(bb, i)
        with open(file_name, 'w') as f: f.write('\n'.join(optimuml)) 



run_naive_optimum(1)
run_naive_optimum(2)
run_naive_optimum(3)
run_naive_optimum(4)
run_naive_optimum(5)
