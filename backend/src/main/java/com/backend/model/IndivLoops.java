package com.backend.model;
import java.util.*;
import com.backend.model.*;

public class IndivLoops {
    private Map<Node,List<Edge>>graph;
    private Map<String,Boolean> visited;
    private Map<String,Boolean> recStack;
    private List<List<String>>loops;

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
            if(!visited.get(node.getId())){
            dfs(node, node.getId(), new StringBuilder(),1.0);
            }
        }
}
   
void dfs(Node current, String startId, StringBuilder path,double gain) {
        String currentId = current.getId();
        visited.put(currentId, true);
        recStack.put(currentId, true);
        path.append(currentId);
        for (Edge edge : graph.get(current)) {
            Node destination = edge.getDestination();
            String destinationId = destination.getId();
            if (!visited.get(destinationId)) {
                dfs(destination, startId, path, gain * edge.getGain());
            } else if (recStack.get(destinationId) && destinationId.equals(startId)) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(path.toString());
                temp.add(gain * edge.getGain() + "");
                loops.add(temp);
            }
        }
        recStack.put(currentId, false);
        visited.put(currentId, false);
        path.deleteCharAt(path.length() - 1);
    
}
public static List<List<String>> sortEachString(List<List<String>> strings) {
    List<List<String>> sortedStrings = new ArrayList<>(strings);
    for (int i = 0; i < strings.size(); i++) {
        char[] chars = strings.get(i).get(0).toCharArray();
        Arrays.sort(chars);
        sortedStrings.get(i).set(0, new String(chars));
    }
    return sortedStrings;
}

public Void removeduplicateLoops(){
  List<List<String>> loop= new ArrayList<>(loops);
   loop= sortEachString(loop);
   Set<List<String>> set = new HashSet<>();
    for (List<String> s : loop) {
        set.add(s);
    }
    loops.clear();
    loops.addAll(set);
    return null;
}

private Node getNode(String id) {
    for (Node node : graph.keySet()) {
        if (node.getId().equals(id)) {
            return node;
        }
    }
    return null; // Consider what to do if node not found; maybe throw an exception or handle differently
}
    void adjustloops(){
        for(int i=0;i<loops.size();i++){
            loops.get(i).set(0, new String(loops.get(i).get(0)+loops.get(i).get(0).charAt(0)));
        }
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


    public List<List<String>> getLoops() {
        calcloops();
        removeduplicateLoops();
        adjustloops();
        return this.loops;
    }
    
    // private boolean areLoopsTouching(String loop1,String loop2) {
    //     for(int i=0;i<loop1.length();i++){
    //         for(int j=0;j<loop2.length();j++){
    //             if(loop1.charAt(i)==loop2.charAt(j)){
    //                 return true;
    //             }
    //         }
    //     }
    //     return false;
    // }

    // public List<List<String>> getNonTouchingLoops(){
    //     List<List<String>> loops=getLoops();
    //     List<List<String>> nonTouchingLoops=new ArrayList<>();
    //     int count=1;
    //     System.out.println(loops.size());
    //     while(count!=0){
    //         count=0;
    //         for(int i=0;i<loops.size();i++){
    //             for(int j=i+1;j<loops.size();j++){
    //                 if(!areLoopsTouching(loops.get(i).get(0), loops.get(j).get(0))){
    //                     List<String> twoNonTouching= new ArrayList<>();
    //                     twoNonTouching.add(loops.get(i).get(0));
    //                     twoNonTouching.add(loops.get(j).get(0));
    //                     twoNonTouching.add(String.valueOf(Double.parseDouble(loops.get(i).get(1)) * Double.parseDouble(loops.get(j).get(1))));
    //                     nonTouchingLoops.add(twoNonTouching);
    //                 }
    //             }
    //         }
    //         for(int i=0;i<loops.size();i++){
    //             for(int j=i+1;j<loops.size();j++){
    //                 for(int k=j+1;k<loops.size();k++){
    //                     if(!areLoopsTouching(loops.get(i).get(0), loops.get(j).get(0)) && !areLoopsTouching(loops.get(i).get(0), loops.get(k).get(0)) && !areLoopsTouching(loops.get(j).get(0), loops.get(k).get(0))){
    //                         List<String> threeNonTouching= new ArrayList<>();
    //                         threeNonTouching.add(loops.get(i).get(0));
    //                         threeNonTouching.add(loops.get(j).get(0));
    //                         threeNonTouching.add(loops.get(k).get(0));
    //                         threeNonTouching.add(String.valueOf(Double.parseDouble(loops.get(i).get(1))* Double.parseDouble(loops.get(j).get(1))*Double.parseDouble(loops.get(k).get(1))));
    //                         nonTouchingLoops.add(threeNonTouching);
    //                     }
    //                 }
    //             }
    //         }

    //     }
    //     return nonTouchingLoops;

    // }
    private boolean areLoopsTouching(List<String> loop1,List<String> loop2) {
        for(int k=0;k<loop1.size()-1;k++){
        for(int i=0;i<loop1.get(k).length();i++){
            for(int j=0;j<loop2.get(0).length();j++){
                if(loop1.get(k).charAt(i)==loop2.get(0).charAt(j)){
                    return true;
                }
            }
        }
    }
        return false;
    }

    public List<List<String>> getNonTouchingLoops(){
        List<List<String>> loops=getLoops();
        List<List<String>> nonTouchingLoops=new ArrayList<>();
        int count=0;
        System.out.println(loops.size());
            for(int i=0;i<loops.size();i++){
                for(int j=i+1;j<loops.size();j++){
                    if(!areLoopsTouching(loops.get(i), loops.get(j))){
                        count++;
                        List<String> twoNonTouching= new ArrayList<>();
                        twoNonTouching.add(loops.get(i).get(0));
                        twoNonTouching.add(loops.get(j).get(0));
                        twoNonTouching.add(String.valueOf(Double.parseDouble(loops.get(i).get(1)) * Double.parseDouble(loops.get(j).get(1))));
                        nonTouchingLoops.add(twoNonTouching);        
                    }
                }
        }
        while(count!=0){
            int f=nonTouchingLoops.size()-count;
            int l=nonTouchingLoops.size();
            count=0;
            for(int i=f;i<l;i++){
                for(int j=0;j<loops.size();j++){
                    if(!areLoopsTouching(nonTouchingLoops.get(i), loops.get(j))){
                        count++;
                        List<String> nonTouching=new ArrayList<>();

                        for(int k=0;k<nonTouchingLoops.get(i).size()-1;k++){
                            nonTouching.add(nonTouchingLoops.get(i).get(k));
                        }
                        nonTouching.add(loops.get(j).get(0));
                        nonTouching.add(String.valueOf(Double.parseDouble(nonTouchingLoops.get(i).get(nonTouchingLoops.get(i).size()-1)) * Double.parseDouble(loops.get(j).get(1))));
                        int temp=0;
                        for(int y=0;y<nonTouchingLoops.size();y++){
                                for(int t=0;t<nonTouching.size();t++){
                                    if(nonTouchingLoops.get(y).contains(nonTouching.get(t))){
                                        temp++;
                                    }
                                }
                            if(temp==nonTouching.size()){
                                break;
                            }
                            temp=0;
                            if(y==nonTouchingLoops.size()-1){
                                nonTouchingLoops.add(nonTouching);
                            }
                        }
                        
                    }
                }
            }
        }
        return nonTouchingLoops;

    }

    public void setLoops(List<List<String>> loops) {
        
        this.loops = loops;
    }

}
