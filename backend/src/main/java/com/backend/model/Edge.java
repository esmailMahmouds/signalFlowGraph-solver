package com.backend.model;
import java.util.Objects;

public class Edge {
    private Node destination;
    private double gain;

    public Edge(Node destination, double gain) {
        this.destination = destination;
        this.gain = gain;
    }

    public Node getDestination() {
        return destination;
    }

    public double getGain() {
        return gain;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Edge)) {
            return false;
        }
        Edge edge = (Edge) o;
        return Objects.equals(destination, edge.destination) && gain == edge.gain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, gain);
    }

    @Override
    public String toString() {
        return "{" +
            " destination='" + getDestination() + "'" +
            ", gain='" + getGain() + "'" +
            "}";
    }
    
}
