import gym
import numpy as np
import matplotlib.pyplot as plt

env = gym.make('CartPole-v1')

epsilon = 0.05
total_episodes = 20000
max_steps = 100

lr_rate = 0.15
gamma = 0.99

state = env.reset()
state2, reward, done, info = env.step(0)  
print(state[0], state2, reward)
print(env.action)


"""
Q = np.full((env.observation_space.n, env.action_space.n), 0.5)
    
def choose_action(state):
    action=0
    if np.random.uniform(0, 1) < epsilon:
        action = env.action_space.sample()
    else:
        action = np.argmax(Q[state, :])
    return action

def learn(state, state2, reward, action):
    predict = Q[state, action]
    target = reward + gamma * np.max(Q[state2, :])
    Q[state, action] = Q[state, action] + lr_rate * (target - predict)

# Start
steps_per_iter = []
reward_per_iter = []
for episode in range(total_episodes):
    state = env.reset()
    t = 0
    r = 0

    if episode % 1000 == 0 and lr_rate > 0.01:
        lr_rate -= 0.01
    
    while t < max_steps:
        #env.render()
        action = choose_action(state)  
        state2, reward, done, info = env.step(action)  
        print(state2, reward)
        learn(state, state2, reward, action)
        state = state2
        t += 1
        r += reward
        time.sleep(0.5)
        if done:
            steps_per_iter.append(t)
            reward_per_iter.append(r)
            break


print(Q)

final_out = []
for i in range(0, len(steps_per_iter), 100):
    sum_steps = 0
    for s in steps_per_iter[i:i+100]:
        sum_steps += s
    sum_rewards = 0
    for r in reward_per_iter[i:i+100]:
        sum_rewards += r
    
    final_out.append(sum_rewards/100)

plt.plot(final_out)
plt.show()
"""
