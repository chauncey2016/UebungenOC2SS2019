import matplotlib.pyplot as plt
import pandas as pd

df = pd.read_csv('output_ga/bb1_1.csv')
plt.plot(df)
plt.show()

