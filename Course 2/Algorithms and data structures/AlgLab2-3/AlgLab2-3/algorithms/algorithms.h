#pragma once

#include "../graph/Graph.h"

using namespace std;

// Lab 2
list<Vertex*> Dijkstra(Graph* graph, size_t startId, size_t destinationId);
list<Vertex*> BellmanFord(Graph* graph, size_t startId, size_t destinationId);

// Lab 3
Graph* Prim(Graph* graph);
Graph* Kruskal(Graph* graph);




