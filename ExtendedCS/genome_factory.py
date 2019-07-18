from frozen_lake_genome import FrozenLakeGenome
from cartpole_genome import CartPoleGenome


def GenomeFactory(env, params):
    if env == 'FrozenLake-v0':
        return FrozenLakeGenome(params)
    elif env == 'CartPole-v1':
        return CartPoleGenome(params)

    return
