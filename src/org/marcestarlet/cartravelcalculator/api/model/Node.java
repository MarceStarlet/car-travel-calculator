package org.marcestarlet.cartravelcalculator.api.model;

import java.util.Comparator;

public class Node<V extends Comparable<V>,E extends Comparable<E>> implements Comparable<Node>{

    protected V vertex; // key name
    protected E edge;   // costs to this vertex

    public Node(){}

    public Node(V vertex, E edge){
        this.vertex = vertex;
        this.edge = edge;
    }

    public V getVertex() {
        return vertex;
    }

    public void setVertex(V vertex) {
        this.vertex = vertex;
    }

    public E getEdge() {
        return edge;
    }

    public void setEdge(E edge) {
        this.edge = edge;
    }

    @Override
    public int compareTo(Node o) {
        return this.edge.compareTo((E) o.getEdge());

    }


    @Override
    public boolean equals(Object n) {
        Node<V,E> node = (Node)n;
        return (this.vertex.compareTo(node.vertex) == 0 && this.edge.compareTo(node.edge) == 0);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("vertex=").append(vertex);
        sb.append(", edge=").append(edge);
        sb.append('}');
        return sb.toString();
    }
}
