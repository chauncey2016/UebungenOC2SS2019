#!/bin/bash

for i in {1..10} 
    do
    input=""
    for j in {1..300}
    do
        rand=$RANDOM
        input="$input $rand $rand $rand $rand $rand\n"
    done
    out_file="output/bb2_${i}.txt"
    out_file_input="output/bb2_${i}_input.txt"
    printf "$input" > $out_file_input
    printf "$input" | docker run -i bb -b 2 >> $out_file
done
