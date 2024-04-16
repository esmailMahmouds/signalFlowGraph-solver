package com.backend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForwardPathsUtil {
    private List<List<String>> forwardPaths;
    private Map<Node, List<Edge>> graph;
    private Map<String,Boolean> visited;

    public ForwardPathsUtil(Map<Node, List<Edge>> graph) {
        this.graph = graph;
        this.visited = new HashMap<>();
        forwardPaths = new ArrayList<>();
    }

    public List<List<String>>  getForwardPaths() {
        dfs(new Node("R"), new StringBuilder(), 1.0);
        return forwardPaths;
    }

    private void dfs(Node node, StringBuilder path, double pathGain) {
        if (node.getId().equals("C")) {
            path.append(node.getId());
            List<String> list = new ArrayList<>();
            list.add(path.toString());
            list.add(String.valueOf(pathGain));
            forwardPaths.add(list);
            path.deleteCharAt(path.length() - 1);
            return;
        }

        String nodeId = node.getId();
        visited.put(nodeId, true);
        path.append(node.getId());

        for (Edge edge : graph.get(node)) {
            Node destination = edge.getDestination();
            String destinationId = destination.getId();
            if (!visited.getOrDefault(destinationId,false)){
                dfs(destination, path, pathGain * edge.getGain());
            }
        }
        path.deleteCharAt(path.length() - 1);
        visited.put(nodeId, false);
    }
}

