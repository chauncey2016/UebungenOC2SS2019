from argparse import ArgumentParser

def parse_arguments():
    tsp_keys = ('seed', 'cities')
    acs_key = ('seed', 'iterations', 'm', 'rho', 'alpha', 'beta', 'q0')
    parser = ArgumentParser()
    parser.add_argument("-c", "--cities", type=int, dest="cities", default=10,
                    help="number of cities in TSP")
    parser.add_argument("-s", "--seed", type=int, dest="seed", default=42,
                    help="seed used in ACS and TSP")
    parser.add_argument("-i", "--iter", type=int, dest="iterations", default=1000,
                    help="number of iterations in ACS")
    parser.add_argument("-m", "--m", type=int, dest="m", default=10,
                    help="number of ants in ACS")
    parser.add_argument("-r", "--rho", type=float, dest="rho", default=0.1,
                    help="local decay in ACS")
    parser.add_argument("-a", "--alpha", type=float, dest="alpha", default=0.1,
                    help="global decay in ACS")
    parser.add_argument("-b", "--beta", type=float, dest="beta", default=0.2,
                    help="determines importance between distance and phermones in ACS")
    parser.add_argument("-q0", "--q0", type=float, dest="q0", default=0.9,
                    help="tradeoff between exploitation and exploration in ACS")
    dict = vars(parser.parse_args())
    return {k:v for k,v in dict.items() if k in tsp_keys}, {k:v for k,v in dict.items() if k in acs_key}

