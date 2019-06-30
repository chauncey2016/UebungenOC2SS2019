import matplotlib.pyplot as plt
import pandas as pd
import glob
import os


all_files = glob.glob(os.path.join('output_ga', "bb5_floating*"))
df_from_each_file = (pd.read_csv(f, header=None) for f in all_files)
concatenated_df = pd.concat(df_from_each_file, ignore_index=True, axis=1)
df = concatenated_df.mean(axis=1)
plt.plot(df)
plt.show()

