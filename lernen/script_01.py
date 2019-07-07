import gym
import random
import sys
#import matplotlib.pyplot as plt

env = gym.make('FrozenLake-v0')

stateSpace = 16
actionSpace = 4
alpha = 0.1
gamma = 0.5
epsilon = 0.09
q = [0] * stateSpace * actionSpace
print(env.observation_space)

newState = env.reset()
oldState = newState

x = 1

def stateToIndex(state):
	return state

def bestAction(state):
    index = stateToIndex(state)
    maxAction = 0
    for x in range(1, actionSpace):
        if q[index*actionSpace + x] > q[index*actionSpace + maxAction]:
            maxAction = x
    return maxAction

def updateQ(state1, state2, action, reward):
    index1 = stateToIndex(state1) * actionSpace + action
    index2 = stateToIndex(state2) * actionSpace + action
    promise = q[ stateToIndex(state2) * actionSpace + bestAction(state2) ]
    q[index1] += alpha * (reward + gamma * promise - q[index1])
    #print("update Q", index1, q[index1], reward, action)

wins = 0

for x in range(1000000):
    action = env.action_space.sample()
    if random.random() > epsilon:
        action = bestAction(newState)
    newState, reward, done, info = env.step(action)
    if done:
        if newState == 15:
            reward = 1
        else:
            reward = -1
        updateQ(oldState, newState, action, reward)
        newState = env.reset()
        oldState = newState
    else:
    	updateQ(oldState, newState, action, reward)
    	oldState = newState

def printQ():
	for x in q:
		sys.stdout.write("{:1.6f}".format(x))
		sys.stdout.write(", ")
	sys.stdout.write("\n")

def printBestActions():
	for x in range(stateSpace):
		action = bestAction(x)
		if action == 0:
			action = "lt"
		if action == 1:
			action = "dn"
		if action == 2:
			action = "rt"
		if action == 3:
			action = "up"
		sys.stdout.write(str(action))
		sys.stdout.write(", ")
		if x % 4 == 0:
			sys.stdout.write("\n")
	sys.stdout.write("\n")
#printQ()
printBestActions()
state = env.reset();
for x in range(500):
    input()
    env.render()
    action = bestAction(state)
    state, reward, done, c = env.step(action)
    print("reward", reward, "state", state)
    if done:
    	env.render()
    	env.reset();
env.close()