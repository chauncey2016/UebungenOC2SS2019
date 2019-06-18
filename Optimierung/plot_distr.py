import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv('output/bb5_distr.txt')
plt.plot(data)
plt.show()
