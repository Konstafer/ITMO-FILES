#!/bin/bash

#/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --sendfile /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/in.txt /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/out.txt


# ----------------------- grades table tests ------------------------ #
tput setaf 1; 
echo "1) Testing custom cp copying grades-table"
tput sgr0 

echo ""
echo "/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --posix in.txt out.txt"
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --posix /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/in.txt /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/out.txt
echo "out.txt's content is: "
cat /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/out.txt
rm /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/out.txt

echo ""
echo ""
echo "/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --language in.txt out.txt"
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --language /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/in.txt /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/out.txt
echo "out.txt's content is: "
cat /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/out.txt

echo "/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --sendfile in.txt out.txt"
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --posix /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/in.txt /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/out.txt
echo "out.txt's content is: "
cat /Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/Lab1/out.txt
rm out.txt

# ------------------------- large file tests --------------------------- #
echo ""
echo ""
tput setaf 1; 
echo "2) Testing custom pc by large files"
tput setaf 2; 
echo "generating large file named largeIn"
dd if=/dev/zero of=largeIn count=1024 bs=262144
tput sgr0;

echo ""
echo "The number of seconds before {posix} copy is `date +%s`."
echo "/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --posix largeIn largeOut"
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --posix largeIn largeOut
echo $"The number of seconds after {posix} copy is `date +%s`."
rm largeOut

echo ""
echo "The number of seconds before {language-level} is `date +%s`."
echo "/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --language largeIn largeOut"
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --language largeIn largeOut
echo "The number of seconds after {language-level} is `date +%s`."
rm largeOut

echo ""
echo "The number of seconds before {sendfile copy} is `date +%s`."
echo "/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --sendfile largeIn largeOut"
/Users/konstafer/Documents/ITMO-FILES/Course\ 2/System\ software/Lab1/DerivedData/Lab1/Build/Products/Debug/Lab1 --sendfile largeIn largeOut
echo "The number of seconds after {sendfile copy} is `date +%s`."
rm largeOut
rm largeIn
