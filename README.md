# car-travel-calculator

A Car travel calculator that uses a directed and weighted nodes in a Graph, to calculate the shortest path from a traveler location to the nearest car in the graph nodes locations

#### Algorithm implementation

This project implements a Breath First Search and Dijkstra's algorithms to calculate the shortest path to the nearest car for the travel.
 
 - Uses an own ```Graph``` and ```Node``` implementation classes to create an Adjacency List of nodes in a graph that represents the locations in a city.
   - Uses ```ConcurrentHashMap``` to handle the adjacent list of nodes.
 - BFS calculates the nearest first 3 cars (cars that are located in the vertex current consulted), then creates a new ```Graph``` with the nodes processed.
   - Uses an ```ArrayQueue``` to enqueue the next nodes to process.
   - Uses a ```HashMap``` to handle the visited nodes to do not repeat them.
   - Uses an ```ArrayList``` to collect the nearest 3 cars.
 - A Dijkstra's algorithm is used over the shrunk graph created by the BFS, to calculate the shortest path to the cars also found in BFS.
   - Uses a ```PriorityQueue``` (min heap) to get the node with the lowest cost (edge).
   - Handles the calculation result costs (edges) in a ```HashMap```.
   

- This algorithm is Thread-Safe due does not share objects, is a complete encapsulation.
- A set of ```JUnit``` test methods tests the behavior in a single thread and multithreading environment.

#### Core libraries

- Concurrent API (*java.util.concurrent.**) 
  - ```ConcurrentHashMap```
  - ```AtomicInteger```
  - ```ExecutorService```
  - ```Executor```
  - ```Callable```
- Collections API 
  - ```PriorityQueue```
  - ```ArrayDeque```
  - ```ArrayList```
  - ```HashMap```
- 