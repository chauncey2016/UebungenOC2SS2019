from qlearning import QLearning
import matplotlib.pyplot as plt
import cProfile, pstats, io


if __name__ == "__main__":
    params = {
        'pos': {
            'min': -2.4,
            'max': 2.4,
            'step': 0.2
        },
        'cvel': {
            'min': -5.0,
            'max': 5.0,
            'step': 0.05
        },
        'angle': {
            'min': -0.42,   
            'max': 0.42,
            'step': 0.001
        },
        'pvel': {
            'min': -5.0,
            'max': 5.0,
            'step': 0.01
        }
    }

    q = QLearning('CartPole-v1', params, alpha=0.001, epsilon=1.0)


    pr = cProfile.Profile()
    pr.enable()
    reward_per_episode = q.learn(1200, 200)
    pr.disable()
    s = io.StringIO()
    sortby = 'cumulative'
    ps = pstats.Stats(pr, stream=s).sort_stats(sortby)
    ps.print_stats()
    print(s.getvalue())

    final_out = []
    for i in range(0, len(reward_per_episode), 100):
        sum_rewards = 0
        for r in reward_per_episode[i:i+100]:
            sum_rewards += r
        final_out.append(sum_rewards/100)

    plt.plot(final_out)
    plt.show()
