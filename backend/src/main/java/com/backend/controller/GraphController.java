package com.backend.controller;


import com.backend.service.GraphService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping
public class GraphController {

    private GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @PostMapping("/create")
    public void createGraph(@RequestParam int numberOfNodes,
                            @RequestBody List<ArrayList<String>> graph) {
        System.out.println(graph);
        System.out.println(numberOfNodes);
        graphService.createGraph(numberOfNodes, graph);
    }

    @GetMapping("/forwardPaths")
    public List<List<String>> getForwardPaths() {
        return graphService.getForwardPaths();
    }

    @GetMapping("/individualLoops")
    public List<List<String>> getIndividualLoops() {
        return graphService.getIndividualLoops();
    }

    @GetMapping("/nonTouchingLoops")
    public List<List<String>> getNonTouchingLoops() {
        return graphService.getNonTouchingLoops();
    }

    @GetMapping("/determinants")
    public List<Double> getDeterminants() {
        return graphService.getDeterminants();
    }

    @GetMapping("/overallGain")
    public double getOverallTransferFunction() {
        return graphService.getOverallTransferFunction();
    }
}
