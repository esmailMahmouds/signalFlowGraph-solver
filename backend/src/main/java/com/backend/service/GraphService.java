package com.backend.service;

import com.backend.model.SignalFlowGraph;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GraphService {

    private SignalFlowGraph signalFlowGraph;
    public void createGraph(int numberOfNodes, List<ArrayList<String>> graph) {
        signalFlowGraph = new SignalFlowGraph(numberOfNodes, graph);
    }
    public List<List<String>> getForwardPaths() {
        return signalFlowGraph.getForwardPaths();
    }

    public List<List<String>> getIndividualLoops() {
        return signalFlowGraph.getIndividualLoops();
    }

    public List<List<String>> getNonTouchingLoops() {
        return signalFlowGraph.getNonTouchingLoops();
    }

    public List<Double> getDeterminants() {
        return signalFlowGraph.getDeterminants();
    }

    public double getOverallTransferFunction() {
        return signalFlowGraph.getOverallTransferFunction();
    }
}
