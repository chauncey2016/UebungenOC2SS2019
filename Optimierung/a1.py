import subprocess
def call_bb(inp, bb):
    cmd = 'echo {} | docker run -i bb -b {}'.format(inp, bb)
    process = subprocess.run(['bash', '-c', cmd], stdout=subprocess.PIPE, 
                            universal_newlines=True)
    return float(process.stdout)


def check_bb(bb, input):
    if   bb == 1: return (input + ' ') * 3
    elif bb == 2: return (input + ' ') * 5
    elif bb == 3: return (input + ' ') * 2
    elif bb == 4: return (input + ' ') * 10
    elif bb == 5: return (input + ' ') * 2



def run_1_to_9(bb):
    for i in range(1, 10):
        inp = check_bb(bb, str(i))
        print(call_bb(inp, bb))


for i in range(1, 6):
    run_1_to_9(i)
