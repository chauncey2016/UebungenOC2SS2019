import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns


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
            #if i % 10 == 0: temp_array.append(float(line))
            temp_array.append(float(line))
        avg_city.append(temp_array)


avg_route = []
for i in range(1, 10):
    with open(r'output/Hroute-'+str(i)) as f:
        temp_array = []
        for i, line in enumerate(f):
            #if i % 10 == 0: temp_array.append(float(line))
            temp_array.append(float(line))
        avg_route.append(temp_array)


avg_phero = []
for i in range(1, 10):
    with open(r'output/Hphero-'+str(i)) as f:
        temp_array = []
        for i, line in enumerate(f):
            #if i % 10 == 0: temp_array.append(float(line))
            temp_array.append(float(line))
        avg_phero.append(temp_array)


avg_city = np.average(avg_city, axis=0)
avg_route = np.average(avg_route, axis=0)
avg_phero = np.average(avg_phero, axis=0)

city_final = [0]
for i in range(avg_city.size-1):
    city_final.append(0-avg_city[i])

route_final = [0]
for i in range(avg_route.size-1):
    route_final.append(0-avg_route[i])

phero_final = [0]
for i in range(avg_phero.size-1):
    phero_final.append(0-avg_phero[i])

fig = plt.gcf()
fig.canvas.set_window_title('Emerg per iter')
plt.plot(city_final, label='city')
plt.plot(route_final, label='route')
plt.plot(phero_final, label='phero')
plt.legend()
plt.show()


print('data for kiviat')
print('city:')
for i, data in enumerate(city_final):
    if i % 20 == 0: print(data)

print('route:')
for i, data in enumerate(route_final):
    if i % 20 == 0: print(data)

print('phero:')
for i, data in enumerate(phero_final):
    if i % 20 == 0: print(data)
