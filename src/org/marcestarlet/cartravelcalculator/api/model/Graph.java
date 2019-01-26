package org.marcestarlet.cartravelcalculator.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Graph<K,V extends Comparable<V>,E extends Comparable<E>> {

    protected Map<K,List<Node>> graph = new ConcurrentHashMap<>();

    public Graph(){}

    /**
     * add new Key, Node to the graph
     * @param key Key name of the vertex
     * @param vertex key name of the vertex which is directed
     * @param edge weight of the edge between vertex
     */
    public void add(K key, V vertex, E edge ){
        Node<V,E> node = null;
        List<Node> nodes = null;
        if (graph.containsKey(key)){
            nodes = graph.get(key);
            if ( null != nodes ){
                nodes.add(new Node(vertex,edge));
            }else{
                nodes = new ArrayList<>();
                nodes.add(new Node(vertex,edge));
            }
        } else {
            nodes = new ArrayList<>();
            nodes.add(new Node(vertex,edge));
            graph.put(key, nodes);
        }
    }

    /**
     * add key, <code>List<Node></code> to the graph
     * @param key
     * @param nodes
     */
    public void addNodes(K key, List<Node> nodes ){
        graph.put(key, nodes);
    }

    /**
     * get graph size, maintained by the Map
     * @return
     */
    public int size(){
        return graph.size();
    }

    /**
     * get graph
     * @return graph <code> Map<K, List<Node>></code>
     */
    public Map<K, List<Node>> get() { return graph; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Graph{");
        sb.append("graph=").append("\n");
        for (K key: graph.keySet()) {
            sb.append(key).append("->");
            List<Node> nodes = graph.get(key);
            if ( null != nodes ){
                sb.append(nodes.toString());
            }
            sb.append("\n");
        }
        sb.append('}');
        return sb.toString();
    }
}
