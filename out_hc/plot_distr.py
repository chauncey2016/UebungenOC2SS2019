import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv('lukasBB5.csv', sep='\t', header=None, skiprows=1)
plt.plot(data[0])
plt.show()
plt.plot(data[2])
plt.show()
plt.plot(data[4])
plt.show()
plt.plot(data[6])
plt.show()
