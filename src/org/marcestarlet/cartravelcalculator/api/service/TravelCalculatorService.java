package org.marcestarlet.cartravelcalculator.api.service;

import org.marcestarlet.cartravelcalculator.api.model.Graph;
import org.marcestarlet.cartravelcalculator.api.model.Node;

import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class TravelCalculatorService extends AbstractTravelService {

    // a static graph, this should be generated on application load
    private static final Graph<String,String,Integer> graph = new Graph<>();
    static {
        graph.add("A","B", 5);
        graph.add("A","C", 3);
        graph.add("A","D", 10);
        graph.add("A","E", 4);
        graph.add("A","F", 2);
        graph.add("A","G", 6);

        graph.add("B","H", 1);
        graph.add("B","I", 7);
        graph.add("C","J", 9);
        graph.add("C","K", 15);
        graph.add("D","L", 3);
        graph.add("D","M", 21);

        graph.add("E","N", 4);
        graph.add("E","O", 2);
        graph.add("F","P", 18);
        graph.add("F","Q", 5);
        graph.add("G","R", 12);
        graph.add("G","S", 17);

        graph.add("E","P", 8);
        graph.add("E","Q", 3);
        graph.add("F","R", 5);
        graph.add("F","S", 2);
        graph.add("G","Q", 10);
        graph.add("G","P", 9);

        graph.add("H","A1", 6);
        graph.add("I","A2", 31);
        graph.add("J","A3", 23);
        graph.add("K","A4", 7);
        graph.add("L","A5", 14);
        graph.add("M","A6", 9);
        graph.add("N","A7", 3);
        graph.add("O","A8", 1);
        graph.add("P","A9", 4);
        graph.add("Q","A10", 16);
        graph.add("R","A11", 24);
        graph.add("S","A12", 29);
    }

    private List<String> carSimulator;

    public TravelCalculatorService(){}

    public TravelCalculatorService(List<String> carSimulator){
        this.carSimulator = carSimulator;
    }

    @Override
    public String requestTravelService(String travelerLocation) throws IllegalArgumentException {

        // validate traveler location
        if (!validateLocation(travelerLocation)){
            throw new IllegalArgumentException("Location not valid");
        }
        // generate and id for the travel request
        // generateTravelId();

        // calculate nearest 3 cars and  get vertex of the nearest car
        String theCarVertex = calculateShortestPath(travelerLocation);

        // get car id on the vertex from a table/service that handles cars and vertex
        // getCarOnVertex(theCarVertex);

        String travelConfirmation = "{id:123467, car:{id:456}}"; // simulation

        return theCarVertex;
    }

    @Override
    protected boolean validateLocation(String location){
        // exists in the graph ??
        return null != location && graph.get().containsKey(location);
    }

    /**
     * Calculates the shortest path to the first 3 cars around the traveler location vertex.
     * A BFS implementation which takes O(n) where n is the num of vertex until the first 3 found
     * @param key Key vertex in node Graph
     * @return shortest car's vertex found, or null if not found
     */
    @Override
    protected String calculateShortestPath(String key){
        // create and initialize data structures for the BFS
        Deque<Node> bfsQueue = new ArrayDeque<>();
        Map<String,Boolean> visitedNodes = new HashMap<>(graph.size());
        List<String> cars = new ArrayList<>(3);

        // graph will be used on the Dijkstra's algorithm
        Graph<String,String,Integer> dijkGraph = new Graph<>();
        Map<String,Integer> costs;

        AtomicInteger countCars = new AtomicInteger(0);

        bfsQueue.add(new Node<>(key, 0));

        while (countCars.get() <= 3 && !bfsQueue.isEmpty()) {
            Node<String, Integer> node = bfsQueue.pop();
            String nodeVertex = node.getVertex();
            List<Node> nodes = graph.get().get(nodeVertex); // get List of nodes
            if (!visitedNodes.getOrDefault(nodeVertex, false)) {
                if (isCarInVertex(nodeVertex)) {
                    // there is a car on this vertex, then add to the list
                    cars.add(nodeVertex);
                    countCars.getAndIncrement();
                }
                visitedNodes.put(nodeVertex, true);
                if (null != nodes)
                    bfsQueue.addAll(nodes);
                // add to dijkGraph for further shortest cost calculation
                dijkGraph.addNodes(nodeVertex, null == nodes ? new ArrayList<>() : nodes);
            }
        }

        // now we have to calculate the shortest path to the shortest vertex found in the BFS
        costs = new HashMap<>(dijkGraph.size());
        dijkstra(key, dijkGraph, costs);

        //System.out.println(costs.toString());
        //System.out.println(cars.toString());

        //System.out.println(dijkGraph.toString());
        return getNearestCarVertex(costs, cars);
    }

    /**
     * Calculates the shortest path in a shrunken graph created during BFS
     * Uses Dijkstra's shortest path algorithm which takes O(v^2)
     * @param key key vertex node in Graph
     * @param dijkGraph the shrunk Graph created by the BFS
     * @param costs a Map to handle the costs of the nodes
     */
    private void dijkstra(String key, Graph<String,String,Integer> dijkGraph, Map<String,Integer> costs){
        // prepare structures for calculation
        Queue<Node<String,Integer>> dijkQueue = new PriorityQueue<>(dijkGraph.size());
        initializeCosts(key, costs, dijkGraph);
        Map<String, Boolean> visited = new HashMap<>(dijkGraph.size());

        Integer lowestCost;
        Integer newCost;

        //System.out.println(dijkGraph.toString());

        dijkQueue.add(new Node(key, 0)); // add source to the queue

        while (!dijkQueue.isEmpty()) {
            // get low cost node in queue
            Node<String, Integer> lowCostNode = dijkQueue.remove();
            String vertex = lowCostNode.getVertex();
            lowestCost = lowCostNode.getEdge();

            if (!visited.getOrDefault(vertex, false)) { // calculate if not visited
                visited.put(vertex,true);
                // iterate over all directed nodes (neighbours) if exists
                List<Node> nodes = dijkGraph.get().get(vertex);
                if (null != nodes) {
                    for (Node<String, Integer> node : nodes) {
                        // get node Vertex
                        String nodeVertex = node.getVertex();
                        // if current vertex is in costs calculate new cost
                        if (costs.containsKey(nodeVertex)) {
                            newCost = lowestCost + node.getEdge();

                            if (newCost < costs.get(nodeVertex)) {
                                costs.put(nodeVertex, newCost);
                            }
                            // add newCost calculated
                            dijkQueue.add(new Node<>(nodeVertex, newCost));
                        }
                    }
                }
            }
        }
    }

    /**
     * Initializes the graph costs
     * @param key The key root node
     * @param costs The Map to handle the costs
     * @param dijkGraph The shrunk graph to get the node costs
     */
    private void initializeCosts(String key, Map<String,Integer> costs, Graph<String,String,Integer> dijkGraph ){
        for (String keySet: dijkGraph.get().keySet()) {
            costs.put(keySet, Integer.MAX_VALUE);
        }

        List<Node> nodes = dijkGraph.get().get(key);
        for (Node<String,Integer> node: nodes ) {
            costs.put(node.getVertex(),node.getEdge());
        }
    }


    /**
     * After dijkstra's costs calculation, gets the lower costs from the nearest cars list.
     * @param costs The Map to handle the costs
     * @param cars The List of cars found
     * @return The vertex with the lowest cost
     */
    private String getNearestCarVertex( Map<String,Integer> costs, List<String> cars){
        Integer low = Integer.MAX_VALUE;
        String node = null;
        for (String key: costs.keySet()) {
            Integer cost = costs.get(key);
            if ( cars.contains(key) && cost < low ){
                low = cost;
                node = key;
            }
        }
        return node;
    }


    private boolean isCarInVertex(String key){
        // simulation of a check car/vertex table or service
        return carSimulator.contains(key);
    }

    protected Graph<String,String,Integer> getGraph(){
        return graph;
    }

}
