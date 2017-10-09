#!/bin/bash

tput setaf 1; 
echo "Generating 5000000 integers"
tput sgr0 
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/Lab2 --generate-integers 5000000
echo "Integers to sort head:"
head /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/randomNumbers.txt
echo $"The number of seconds `date +%s`."
tput setaf 1; 
echo "0) Using standart c++ heap dynamic memory" 
tput sgr0 
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/Lab2 --heap /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/randomNumbers.txt /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/out1.txt
echo "Resulted file head:"
head /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/out1.txt
echo $"The number of seconds `date +%s`."

tput setaf 1; 
echo "1) Using file-mapped dynamic memory" 
tput sgr0 
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/Lab2 --file-mapped /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/randomNumbers.txt /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/out2.txt
echo "Resulted file head:"
head /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/out2.txt
echo $"The number of seconds `date +%s`."

tput setaf 1; 
echo "2) Using base pointer mapped dynamic memory" 
tput sgr0 
echo "Resulted file head:"
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/Lab2 --pointer-mapped /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/randomNumbers.txt /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/out3.txt
head /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab2/DerivedData/Lab2/Build/Products/Debug/out3.txt
echo $"The number of seconds `date +%s`."
