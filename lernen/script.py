import gym
import random
env = gym.make('FrozenLake-v0')
#env = gym.make('CartPole-v1')
stateSpace = 16
actionSpace = 4
alpha = 0.1
gamma = 0.5
epsilon = 0.03
q = [0] * stateSpace * actionSpace
print(env.observation_space)
env.reset()
x = 1
y = 0
for x in range(100000):
    #env.render()
    action = env.action_space.sample()
    if 0 < x and random.random() < epsilon:
    	for k in range(0, actionSpace):
    		if q[newState*actionSpace+k] > q[newState*actionSpace+action]:
    			action = k
    #observation, reward, done, info = env.step(action)
    result   = env.step(action)
    newState = result[0]
    reward   = result[1]
    done     = result[2]
    if 0 < x:
    	indexNew = newState * actionSpace
    	indexOld = oldState * actionSpace + action
    	bestAction = q[indexNew]
    	for y in range(1, actionSpace):
    		if q[indexNew + y] > bestAction:
    			bestAction = q[indexNew + y]
    	q[indexOld] += alpha * (reward + gamma * bestAction - q[indexOld])
    oldState = newState
    if done:
    	#env.render()
    	env.reset()
    	y = y + 1
    	if 10 < y:
	    	break
    #print("observation", observation, "reward", reward, "done", done, "info", info)
env.render()
print(x, "Iterations")
print(q)

for x in range(500):
    input()
    env.render()
    action = env.action_space.sample()
    if 0 < x and random.random() > epsilon:
    	for y in range(0, actionSpace):
    		if q[newState*actionSpace+y] > q[newState*actionSpace+action]:
    			action = y
    a, b, done, c = env.step(action)
    if done:
    	env.render()
    	env.reset();
#for x in range(10):
#	print(random.random())
env.close()
#1 == down
#0 == left
#2 = right
#3 = up