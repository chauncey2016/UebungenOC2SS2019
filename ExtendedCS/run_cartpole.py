from qlearning import QLearning
import matplotlib.pyplot as plt


if __name__ == "__main__":
    params = {
        'pos': {
            'min': -2.4,
            'max': 2.4,
            'step': 0.001
        },
        'cvel': {
            'min': -0.5,
            'max': 0.5,
            'step': 0.001
        },
        'angle': {
            'min': -0.42,   
            'max': 0.42,
            'step': 0.001
        },
        'pvel': {
            'min': -0.5,
            'max': 0.5,
            'step': 0.001
        }
    }

    q = QLearning('CartPole-v1', params)
    reward_per_episode = q.learn(50000, 200)

    final_out = []
    for i in range(0, len(reward_per_episode), 100):
        sum_rewards = 0
        for r in reward_per_episode[i:i+100]:
            sum_rewards += r
        final_out.append(sum_rewards/100)

    plt.plot(final_out)
    plt.show()
