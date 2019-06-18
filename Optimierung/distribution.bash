#!/bin/bash
input=""
for j in {0..400}
do
    input="$input $j $j \n"
done
out_file="output/bb5_distr.txt"
printf "$input" | docker run -i bb -b 5 >> $out_file
