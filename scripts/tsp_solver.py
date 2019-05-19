from parse_arguments import parse_arguments
from tsp import TSP
from ants import ACS

args_tsp, args_acs = parse_arguments()

tsp = TSP(**args_tsp)
print(tsp.adjmatrix)

ant = ACS(tsp.adjmatrix, **args_acs)
ant.go_ants()
