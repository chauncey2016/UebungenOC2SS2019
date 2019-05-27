import matplotlib.pyplot as plt
import numpy as np


avg_len = []
for i in range(1, 10):
    with open(r'output/len-'+str(i)) as f:
        temp_array = []
        for i, line in enumerate(f):
            temp_array.append(float(line))
        avg_len.append(temp_array)


fig = plt.gcf()
fig.canvas.set_window_title('Avg len per iter')
plt.plot(np.average(avg_len, axis=0), label='city')
plt.show()


avg_city = []
for i in range(1, 10):
    with open(r'output/Hcity-'+str(i)) as f:
        temp_array = []
        for i, line in enumerate(f):
            if i % 10 == 0: temp_array.append(float(line))
        avg_city.append(temp_array)


avg_route = []
for i in range(1, 10):
    with open(r'output/Hroute-'+str(i)) as f:
        temp_array = []
        for i, line in enumerate(f):
            if i % 10 == 0: temp_array.append(float(line))
        avg_route.append(temp_array)


avg_phero = []
for i in range(1, 10):
    with open(r'output/Hphero-'+str(i)) as f:
        temp_array = []
        for i, line in enumerate(f):
            if i % 10 == 0: temp_array.append(float(line))
        avg_phero.append(temp_array)


fig = plt.gcf()
fig.canvas.set_window_title('Emerg per iter')
plt.plot(np.average(avg_city, axis=0), label='city')
plt.plot(np.average(avg_route, axis=0), label='route')
plt.plot(np.average(avg_phero, axis=0), label='phero')
plt.legend()
plt.show()


