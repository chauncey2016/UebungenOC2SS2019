from parse_arguments import parse_arguments
from tsp import TSP
from ants import ACS

import time

args_tsp, args_acs = parse_arguments()

tsp = TSP(**args_tsp)
print(tsp.adjmatrix)

ant = ACS(tsp.adjmatrix, **args_acs)

start = time.time()
ant.go_ants()
end = time.time()
print('Ellapsed time in sec: ', end - start)
ant.end_routine()