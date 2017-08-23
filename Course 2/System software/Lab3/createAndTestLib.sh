#!/bin/bash

echo "0) compiling src to objects";
gcc -fPIC -g -c -Wall /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab3/Lab3/LibraryIOFunctions.c
gcc -fPIC -g -c -Wall /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab3/Lab3/LibraryMathFunctions.c

echo "1) creating lib";
gcc -shared -fPIC -Wl -o libcrap.so.1.5.0 LibraryMathFunctions.o LibraryIOFunctions.o -lc

echo "2) removing .o files"

echo "3) compiling source files with custom and math library-links"
gcc /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab3/Lab3/main.c libcrap.so.1.5.0 -lm -o thirdPartyProgram # -lm is needed for sin and cos functions, coz 

echo "4) executing output file"
./thirdPartyProgram
