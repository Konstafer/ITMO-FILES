#!/bin/bash 

if [ ! -f "./AlgLab2-3" ]; then
	echo "Cannot find AlgLab2-3 executable in current directory ^("
	exit 1
fi
if [ ! -d "./timestamps" ]; then
	mkdir timestamps
fi

#-------------- 3.1.a ---------------#
# verticesAmount: iterable
# edgesAmount: (verticesAmount^2)/10
# minWeight: 1; maxWeight: 10^6

tput setaf 1; 
echo "Experiment 3.1.a"
tput sgr0 

minWeight=1
maxWeight=$[10**6]

> timestamps/dijkstra-31a.txt
> timestamps/bellmanford-31a.txt

for ((verticesAmount = 1; verticesAmount < (10**4)+1; verticesAmount += 100))
do
	edgesAmount=$[(verticesAmount**2)/10]
    printf "\nEdges: %s" "$edgesAmount"
	printf "\nvertices amount: %s\n" "$verticesAmount" | tee -a timestamps/dijkstra-31a.txt | more
	./AlgLab2-3 run --algorithm dijkstra --directed 1 -va $verticesAmount -ea $edgesAmount -min $minWeight -max $maxWeight --start 0 --finish $[verticesAmount-1] | tee -a timestamps/dijkstra-31a.txt | more
    printf "\nEdges: %s" "$edgesAmount"
	printf "\nvertices amount: %s\n" "$verticesAmount" | tee -a timestamps/bellmanford-31a.txt | more
	./AlgLab2-3 run --algorithm bellman-ford --directed 1 -va $verticesAmount -ea $edgesAmount -min $minWeight -max $maxWeight --start 0 --finish $[verticesAmount-1] | tee -a timestamps/bellmanford-31a.txt | more
done

# ---------- 3.1.b ----------- #
# verticesAmount: iterable
# edgesAmount: verticesAmount^2
# minWeight: 1; maxWeight: 10^6

tput setaf 1; 
echo "Experiment 3.1.b"
tput sgr0 

minWeight=1
maxWeight=$[10**6]

> timestamps/dijkstra-31b.txt
> timestamps/bellmanford-31b.txt

for ((verticesAmount = 1; verticesAmount < (10**4)+1; verticesAmount += 100))
do
	edgesAmount=$[verticesAmount**2]

	printf "vertices amount: %s\n" "$verticesAmount" | tee -a timestamps/dijkstra-31b.txt | more
	./AlgLab2-3 run --algorithm dijkstra --directed 1 -va $verticesAmount -ea $edgesAmount -min $minWeight -max $maxWeight --start 0 --finish $[verticesAmount-1] | tee -a timestamps/dijkstra-31b.txt | more

	printf "vertices amount: %s\n" "$verticesAmount" | tee -a timestamps/bellmanford-31b.txt | more
	./AlgLab2-3 run --algorithm bellman-ford --directed 1 -va $verticesAmount -ea $edgesAmount -min $minWeight -max $maxWeight --start 0 --finish $[verticesAmount-1] | tee -a timestamps/bellmanford-31b.txt | more

done
