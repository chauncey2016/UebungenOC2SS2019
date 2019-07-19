import gym
import numpy as np
import random
from lcs_table import LCSTable


class QLearning():
    def __init__(self, env, params, alpha=0.15, gamma=0.99, 
                 epsilon=0.05, population=100):
        self.env = gym.make(env)
        self.table = LCSTable(env, self.env.action_space.n, params)
        self.alpha = alpha
        self.gamma = gamma
        self.epsilon = epsilon


    def exploration(self, A_set, tau=0.1):
        div_func = np.vectorize(lambda x: x / tau)
        temp_actions = div_func(A_set)
        temp_actions = np.exp(temp_actions)
        norm_func = np.vectorize(lambda x, y: x / y)
        norm_actions = norm_func(temp_actions, np.sum(temp_actions))
        q = random.uniform(0, 1)
        sum = 0
        for i in range(norm_actions.size):
            sum += norm_actions[i]
            if q <= sum: return i
        return norm_actions.size


    def exploitation(self, A_set):
        return np.argmax(A_set)


    def choose_action(self, A_set):
        if np.random.uniform(0, 1) < self.epsilon:
            return self.exploration(A_set)
        else:
            return self.exploitation(A_set)


    def update_table(self, as1, state1, state2, reward, action):
        predict = as1[action]
        as2 = self.table.get_action_set(state2)
        target = reward + self.gamma * np.max(as2)
        delta = self.alpha * (target - predict)
        self.table.update_table_entries(state1, action, delta)


    def learn(self, total_episodes=20000, max_steps=100, genetic_step=50):
        reward_per_episode = []
        for episode in range(total_episodes):
            state = self.env.reset()
            r, t, g = 0, 0, 0

            while t < max_steps:
                self.env.render()
                action_set = self.table.get_action_set(state)
                action = self.choose_action(action_set)  
                state2, reward, done, _ = self.env.step(action)  
                self.update_table(action_set, state, state2, reward, action)
                state = state2
                r += reward
                t += 1
                g = (g + 1) % genetic_step
                if g == 0:
                    self.table.start_ga(state, action)
                if done:
                    print('reset', r, t, state)
                    reward_per_episode.append(r)
                    break

        return reward_per_episode

            

