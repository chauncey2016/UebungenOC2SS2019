def check_bb(bb):
    if bb == 1: return 3
    if bb == 2: return 5
    if bb == 3: return 2
    if bb == 4: return 10
    if bb == 5: return 2


import random
def generate_tuples(bb,  iterations=300, min_rand=-10000, max_rand=10000):
    for i in range(iterations):
        yield (str(random.uniform(min_rand, max_rand)) + ' ') * check_bb(bb) + '\n'


from subprocess import Popen, PIPE
def create_bb_process(bb):    
    cmd = 'docker run -i bb -b {}'.format(bb)
    return Popen(['bash', '-c', cmd], stdin=PIPE, stdout=PIPE, universal_newlines=True)


def search_optimum(proc, gen):
    current = 0.0
    fitness_history = []
    for i, x in enumerate(gen):
        proc.stdin.write(x)
        proc.stdin.flush()
        output = proc.stdout.readline()
        if i == 0 or float(output) < current: current = float(output)
        fitness_history.append(current)
    return fitness_history

def run_naive_optimum(bb, epochs=10):
    for i in range(epochs):
        with create_bb_process(bb) as proc:
            gen = generate_tuples(bb, 500, -512, 512)
            data = search_optimum(proc, gen)

            with open('blatt08_out/bb'+str(bb)+'_'+str(i)+'.csv', 'w') as f:
                for d in data:
                    f.write(str(d)+'\n')


run_naive_optimum(5)
