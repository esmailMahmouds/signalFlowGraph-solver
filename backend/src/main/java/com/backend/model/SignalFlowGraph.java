package com.backend.model;

import java.util.*;

public class SignalFlowGraph {

    private int numberOfNodes;
    private Map<Node, List<Edge>> graph;
    private List<List<String>> forwardPaths;

    public SignalFlowGraph(int numberOfNodes, List<ArrayList<String>> list) {
        this.numberOfNodes = numberOfNodes;
        this.graph = new HashMap<>();
        this.forwardPaths = new ArrayList<>();
        buildGraph(list);
        setForwardPaths();
        setIndividualLoops();
        setNonTouchingLoops();
    }

    public void buildGraph(List<ArrayList<String>> list) {
        for (ArrayList<String> innerList : list) {
            addEdge(new Node(innerList.get(0)), new Node(innerList.get(1)), innerList.get(2));
        }
    }
    private void addEdge(Node source, Node destination, String gain) {
        Edge edge = new Edge(destination, Double.parseDouble(gain));
        graph.putIfAbsent(source, new ArrayList<>());
        graph.get(source).add(edge);
    }

    private void setForwardPaths() {
        ForwardPathsUtil forwardPathsUtil = new ForwardPathsUtil(graph);
        forwardPaths = forwardPathsUtil.getForwardPaths();
    }

    private void setIndividualLoops() {  // TODO

    }

    private void setNonTouchingLoops() { // TODO

    }
    public List<Integer> CalculateDeterminants() { // TODO
        return null;
    }

    public double getOverallTransferFunction() { // TODO

        return 0;
    }

    public List<List<String>> getForwardPaths() {
        return forwardPaths;
    }

    public void printGraph() {
        for (Map.Entry<Node, List<Edge>> entry : graph.entrySet()) {
            Node node = entry.getKey();
            List<Edge> edges = entry.getValue();
            System.out.print(node.getId() + " -> ");
            for (Edge edge : edges) {
                System.out.print(edge.getDestination().getId() + "(" + edge.getGain() + ") ");
            }
            System.out.println();
        }
    }
}
