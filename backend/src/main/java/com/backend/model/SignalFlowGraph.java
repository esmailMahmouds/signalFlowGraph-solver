package com.backend.model;

import java.util.*;

public class SignalFlowGraph {

    private int numberOfNodes;
    private Map<Node, List<Edge>> graph;
    private List<List<String>> forwardPaths;
    private List<List<String>> individualLoops;
    private List<List<String>> nontouchingloops;
    public SignalFlowGraph(int numberOfNodes, List<ArrayList<String>> list) {
        this.numberOfNodes = numberOfNodes;
        this.graph = new HashMap<>();
        this.forwardPaths = new ArrayList<>();
        initializeGraph();
        buildGraph(list);
        setForwardPaths();
        setIndividualLoops();
        setNonTouchingLoops();
    }
    private void initializeGraph() {
        Node source = new Node("R");
        graph.put(source, new ArrayList<>());

        for (int i = 1; i <= numberOfNodes-2; i++) {
            Node node = new Node(String.valueOf(i));
            graph.put(node, new ArrayList<>());
        }

        Node destination = new Node("C");
        graph.put(destination, new ArrayList<>());
    }

    public void buildGraph(List<ArrayList<String>> list) {
        for (ArrayList<String> innerList : list) {
            addEdge(new Node(innerList.get(0)), new Node(innerList.get(1)), innerList.get(2));
        }
    }
    private void addEdge(Node source, Node destination, String gain) {
        Edge edge = new Edge(destination, Double.parseDouble(gain));
        graph.get(source).add(edge);
    }

    private void setForwardPaths() {
        ForwardPathsUtil forwardPathsUtil = new ForwardPathsUtil(graph);
        forwardPaths = forwardPathsUtil.getForwardPaths();
    }

    private void setIndividualLoops() {  
        IndivLoops indivLoops = new IndivLoops(graph);
        individualLoops = indivLoops.getLoops();
    }

    private void setNonTouchingLoops() {
        IndivLoops nontouchingLoops = new IndivLoops(graph);
        nontouchingloops =nontouchingLoops.getNonTouchingLoops();
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
    public List<List<String>> getIndividualLoops() {
        return individualLoops;
    }
    public List<List<String>> getNonTouchingLoops(){
        return nontouchingloops;
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
