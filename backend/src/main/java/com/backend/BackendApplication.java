package com.backend;

import model.SignalFlowGraph;
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
        List<ArrayList<String>> list = new ArrayList<>();

        ArrayList<String> innerList = new ArrayList<>();
        innerList.add("R");
        innerList.add("1");
        innerList.add("2");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("1");
        innerList.add("2");
        innerList.add("11");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("2");
        innerList.add("1");
        innerList.add("1");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("2");
        innerList.add("3");
        innerList.add("6");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("3");
        innerList.add("4");
        innerList.add("7");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("4");
        innerList.add("3");
        innerList.add("3");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("4");
        innerList.add("5");
        innerList.add("1");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("4");
        innerList.add("5");
        innerList.add("5");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("5");
        innerList.add("3");
        innerList.add("4");
        list.add(innerList);
        innerList = new ArrayList<>();
        innerList.add("5");
        innerList.add("C");
        innerList.add("9");
        list.add(innerList);


        SignalFlowGraph signalFlowGraph = new SignalFlowGraph(7, list);
        signalFlowGraph.printGraph();

        List<List<String>> forwardPaths = signalFlowGraph.getForwardPaths();
        System.out.println("Forward Paths:" + forwardPaths);
    }

}
