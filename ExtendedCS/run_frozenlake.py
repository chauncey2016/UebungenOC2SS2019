from qlearning import QLearning
import matplotlib.pyplot as plt


if __name__ == "__main__":
    params = {
        'min': 0,
        'max': 16,
        'step': 1
    }

    q = QLearning('FrozenLake-v0', params)
    reward_per_episode = q.learn(4000)

    final_out = []
    for i in range(0, len(reward_per_episode), 100):
        sum_rewards = 0
        for r in reward_per_episode[i:i+100]:
            sum_rewards += r
        final_out.append(sum_rewards/100)

    plt.plot(final_out)
    plt.show()
