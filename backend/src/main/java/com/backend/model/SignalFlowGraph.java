package com.backend.model;

import java.util.*;

public class SignalFlowGraph {

    private int numberOfNodes;
    private Map<Node, List<Edge>> graph;
    private List<List<String>> forwardPaths;
    private List<List<String>> individualLoops;
    private List<List<String>> nontouchingloops;
    private List<Double> determinants;

    public SignalFlowGraph(int numberOfNodes, List<ArrayList<String>> list) {
        this.numberOfNodes = numberOfNodes;
        this.graph = new HashMap<>();
        this.forwardPaths = new ArrayList<>();
        this.individualLoops=new ArrayList<>();
        this.determinants=new ArrayList<>();
        buildGraph(list);
        setForwardPaths();
        setIndividualLoops();
        setNonTouchingLoops();
        calculateDeterminants();
    }

    public void buildGraph(List<ArrayList<String>> list) {
        for (ArrayList<String> innerList : list) {
            addEdge(new Node(innerList.get(0)), new Node(innerList.get(1)), innerList.get(2));
        }
    }
    private void addEdge(Node source, Node destination, String gain) {
        Edge edge = new Edge(destination, Double.parseDouble(gain));
        graph.putIfAbsent(source,new ArrayList<>());
        graph.putIfAbsent(destination,new ArrayList<>());
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
        nontouchingloops =nontouchingLoops.getNonTouchingLoops(individualLoops);
    }
    public void calculateDeterminants() {
        IndivLoops indivLoops=new IndivLoops(graph);
        DeterminantUtil determinantUtil=new DeterminantUtil(indivLoops);
        determinants =determinantUtil.calcDeterminants(forwardPaths,individualLoops);
    }

    public double getOverallTransferFunction() {
        IndivLoops indivLoops=new IndivLoops(graph);
        DeterminantUtil determinantUtil=new DeterminantUtil(indivLoops);
        return determinantUtil.calcOverallTransferFunction(forwardPaths,determinants);
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
    public List<Double> getDeterminants(){
        return determinants;
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
