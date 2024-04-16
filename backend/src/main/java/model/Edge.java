package model;

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
}
