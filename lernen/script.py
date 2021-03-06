import gym
import random
import sys
import matplotlib.pyplot as plt

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

def averageWinsPerEpisode(t_2, episodes, length):
	wins = t_2[episodes]
	if length > episodes:
		return wins / (episodes+1)
	oldWins = t_2[episodes - length]
	return (wins - oldWins) / length

wins = 0
loos = 0
movs = 0
episodes = 0
t = [0] * 100000
t_2 = [0] * 100000
t_3 = [0] * 100000
t_4 = [0] * 100000
t_5 = [0] * 100000
for x in range(1000000):
    action = env.action_space.sample()
    if random.random() > epsilon:
        action = bestAction(newState)
    newState, reward, done, info = env.step(action)
    if done:
        if newState == 15:
            wins = wins + 1
            reward = 1
        else:
            loos = loos + 1
            reward = -1
        t[episodes] = x
        t_2[episodes] = wins
        t_3[episodes] = loos
        t_4[episodes] = movs
        t_5[episodes] = averageWinsPerEpisode(t_2, episodes, 100);
        movs = 0
        episodes = episodes + 1
        updateQ(oldState, newState, action, reward)
        newState = env.reset()
        oldState = newState
        #if episodes == 1000:
        #    break
        if t_5[episodes - 1] > 0.5:
        	break;
    else:
        updateQ(oldState, newState, action, reward)
        oldState = newState
        movs = movs + 1

env.render()

printQ()
printBestActions()

print("avg-Wins:", t_5[episodes - 1])
print("episodes:", episodes)

state = env.reset();

for x in range(100):
    input()
    env.render()
    action = bestAction(state)
    state, reward, done, c = env.step(action)
    print("reward", reward, "state", state)
    if done:
    	env.render()
    	env.reset();
env.close()

e = episodes
t = t[0:e]
t_2 = t_2[0:e]
t_3 = t_3[0:e]
t_4 = t_4[0:e]
t_5 = t_5[0:e]


plt.plot(t_5)
plt.show()
plt.plot(t_2)
plt.plot(t_3)
plt.show()
plt.plot(t_4)
plt.show()