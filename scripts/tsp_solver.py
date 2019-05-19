from tsp import TSP
from ants import ACS


tsp = TSP(16, 1)
print(tsp.adjmatrix)

ant = ACS(tsp.adjmatrix, 1000)
ant.go_ants()

