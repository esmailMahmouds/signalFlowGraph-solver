package com.backend;

import com.backend.model.SignalFlowGraph;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        Scanner in = new Scanner(System.in);
//        List<ArrayList<String>> list = new ArrayList<>();
//
//        ArrayList<String> innerList = new ArrayList<>();
//        innerList.add("R");
//        innerList.add("1");
//        innerList.add("2");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("1");
//        innerList.add("2");
//        innerList.add("11");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("2");
//        innerList.add("1");
//        innerList.add("1");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("2");
//        innerList.add("3");
//        innerList.add("6");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("3");
//        innerList.add("4");
//        innerList.add("7");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("4");
//        innerList.add("3");
//        innerList.add("3");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("4");
//        innerList.add("5");
//        innerList.add("1");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("4");
//        innerList.add("5");
//        innerList.add("5");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("5");
//        innerList.add("3");
//        innerList.add("4");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("5");
//        innerList.add("6");
//        innerList.add("2");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("6");
//        innerList.add("7");
//        innerList.add("2");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("7");
//        innerList.add("6");
//        innerList.add("3");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("R");
//        innerList.add("8");
//        innerList.add("3");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("8");
//        innerList.add("9");
//        innerList.add("10");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("9");
//        innerList.add("8");
//        innerList.add("15");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("9");
//        innerList.add("a");
//        innerList.add("20");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("a");
//        innerList.add("b");
//        innerList.add("30");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("b");
//        innerList.add("a");
//        innerList.add("14");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("b");
//        innerList.add("C");
//        innerList.add("1");
//        list.add(innerList);
//        innerList = new ArrayList<>();
//        innerList.add("7");
//        innerList.add("C");
//        innerList.add("2");
//        list.add(innerList);
//
//
//
//        SignalFlowGraph signalFlowGraph = new SignalFlowGraph(13, list);
//        signalFlowGraph.printGraph();
//
//        List<List<String>> forwardPaths = signalFlowGraph.getForwardPaths();
//        System.out.println("Forward Paths:" + forwardPaths);
//
//        List<List<String>> loops=signalFlowGraph.getIndividualLoops();
//        System.out.println("Loops:"+loops);
//        List<List<String>> nontouchingloops=signalFlowGraph.getNonTouchingLoops();
//        System.out.println("nontouchingloops:"+nontouchingloops);
//
//        List<Double> determinants=signalFlowGraph.getDeterminants();
//        System.out.println("Determinants: "+determinants);
//        double overallGain=signalFlowGraph.getOverallTransferFunction();
//        System.out.println("Overall gain: "+overallGain);
    }

}
