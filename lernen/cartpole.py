import gym
import random
import math
from gym import spaces

env = gym.make('CartPole-v1')
stateSpace = 16
actionSpace = 4
alpha = 0.1
gamma = 0.5
epsilon = 0.03
q = [0] * stateSpace * actionSpace

space = spaces.Discrete(8) 
print(env.observation_space)

max = env.observation_space.high
min = env.observation_space.low
state = env.observation_space.sample()

def quantize(val, min, max, levels):
  val = math.floor((val - min) * levels / (max - min))
  return val

def quantizeState(state, min, max, levels):
    state2 = [0] * len(state)
    for x in range(0, len(state)):
        state2[x] = quantize(state[x], min[x], max[x], levels)
    return state2

print(state[0], quantize(state[0], min[0], max[0], 8), min[0], max[0])

print(state[2], quantize(state[2], min[2], max[2], 8), min[2], max[2])

print(state, min, max)
print(space)
#print(quantizeState(state, min, max, 8))
print(env.action_space.sample())
print(env.action_space.n)
print("len=", len(state))

env.reset()
x = 1
y = 0
for x in range(10):
    env.render()
    action = env.action_space.sample()
    state, reward, done, info = env.step(action)
    #print("state", state, "reward", reward, "done", done, "info", info)
    if done:
    	env.render()
    	env.reset()
env.close()
