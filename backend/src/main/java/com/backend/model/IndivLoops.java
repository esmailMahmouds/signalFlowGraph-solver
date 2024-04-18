package com.backend.model;

import java.lang.reflect.Array;
import java.util.*;
import com.backend.model.*;

public class IndivLoops {
    private Map<Node,List<Edge>>graph;
    private Map<String,Boolean> visited;
    private Map<String,Boolean> recStack;
    private List<String>loops;

    public IndivLoops(Map<Node,List<Edge>>graph){
        this.graph=graph;
        this.recStack=new HashMap<>();
        this.visited=new HashMap<>();
        this.loops=new ArrayList<>();
        for (Node node : graph.keySet()) {
            visited.put(node.getId(), false);
            recStack.put(node.getId(), false);
        }

    }
    public void calcloops() {
        for (Node node : graph.keySet()) {
            dfs(node, node.getId(), new ArrayList<>());
        }
}
   
void dfs(Node current, String startId, List<String> path) {
        String currentId = current.getId();
        visited.put(currentId, true);
        recStack.put(currentId, true);
        path.add(currentId);
        for (Edge edge : graph.get(current)) {
            Node destination = edge.getDestination();
            String destinationId = destination.getId();
            if (!visited.get(destinationId)) {
                dfs(destination, startId, path);
            } else if (recStack.get(destinationId) && destinationId.equals(startId)) {
                String loop = String.join("",path);
                loops.add(loop);
            }
        }
        recStack.put(currentId, false);
        visited.put(currentId, false);
        path.remove(path.size() - 1);
        removeduplicateLoops();
    
}
public static String[] sortEachString(String[] strings) {
    String[] sortedStrings = new String[strings.length];
    for (int i = 0; i < strings.length; i++) {
        char[] chars = strings[i].toCharArray();
        Arrays.sort(chars);
        sortedStrings[i] = new String(chars);
    }
    return sortedStrings;
}

public Void removeduplicateLoops(){
  String[] loop= sortEachString(loops.toArray(String[]::new));
   Set<String> set = new HashSet<>();
    for (String s : loop) {
        set.add(s);
    }
    loops.clear();
    loops.addAll(set);
    return null;
}

    
public ArrayList<Double> cyclesgain(List<String> loops) {
    ArrayList<Double> gains = new ArrayList<>();
    for (int i = 0; i < loops.size(); i++) {
        String loop = loops.get(i) + "" + loops.get(i).charAt(0);  
        System.out.println(loop);
        String[] nodes = loop.split("");
        double loopGain = 1;
        Node previousNode = getNode(nodes[0]); 

        for (int j = 1; j < nodes.length; j++) {
            Node currentNode = getNode(nodes[j]);
            if (graph.get(previousNode) != null) {
                for (Edge edge : graph.get(previousNode)) {
                    if (edge.getDestination().equals(currentNode)) {
                        loopGain *= edge.getGain();
                        break;
                    }
                }
            }
            previousNode = currentNode; 
        }
        gains.add(loopGain);
    }
    System.out.println(gains);
    return gains;
}

private Node getNode(String id) {
    for (Node node : graph.keySet()) {
        if (node.getId().equals(id)) {
            return node;
        }
    }
    return null; // Consider what to do if node not found; maybe throw an exception or handle differently
}
    public Map<Node,List<Edge>> getGraph() {
        return this.graph;
    }

    public void setGraph(Map<Node,List<Edge>> graph) {
        this.graph = graph;
    }

    public Map<String,Boolean> getVisited() {
        return this.visited;
    }

    public void setVisited(Map<String,Boolean> visited) {
        this.visited = visited;
    }

    public Map<String,Boolean> getRecStack() {
        return this.recStack;
    }

    public void setRecStack(Map<String,Boolean> recStack) {
        this.recStack = recStack;
    }


    public List<String> getLoops() {
        calcloops();
        cyclesgain(loops);
        for (int i = 0; i < loops.size(); i++) {
            loops.set(i,loops.get(i) + "" + loops.get(i).charAt(0)); 
        }
        return this.loops;
    }

    public void setLoops(List<String> loops) {
        
        this.loops = loops;
    }

}
