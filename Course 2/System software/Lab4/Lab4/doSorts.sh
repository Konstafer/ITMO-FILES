#!/bin/bash

tput setaf 1; 
echo "0) Generating 100500 integers in file randomNumbers.txt"
tput sgr0
rm -rf /Users/konstafer/ITMO-FILES/Course\ 2/System\ software/Lab4/Lab4/randomNumbers.txt
touch /Users/konstafer/ITMO-FILES/Course\ 2/System\ software/Lab4/Lab4/randomNumbers.txt
for i in `seq 1 100500`; do
    echo $RANDOM >> /Users/konstafer/ITMO-FILES/Course\ 2/System\ software/Lab4/Lab4/randomNumbers.txt
done

tput setaf 1;
echo "1) head randomNumbers.txt"
tput sgr0
head /Users/konstafer/ITMO-FILES/Course\ 2/System\ software/Lab4/Lab4/randomNumbers.txt

tput setaf 1; 
echo "2) Running SortAndFind program:"
tput sgr0
/Users/konstafer/ITMO-FILES/Course\ 2/System\ software/Lab4/DerivedData/Build/Products/Debug/Lab4 --sort-algorithm quick --missed-number 1230 /Users/konstafer/ITMO-FILES/Course\ 2/System\ software/Lab4/Lab4/randomNumbers.txt

