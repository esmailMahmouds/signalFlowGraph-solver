package com.backend.model;

import java.util.ArrayList;
import java.util.List;

public class OverallTransferFunctionUtil {
    public List<List<String>> getNonTouchingLoops(List<List<String>> loops){
        /*ABO ALAA*/ //TODO
        List<List<String>> nonTouchingLoops=new ArrayList<>();
        //[ [g12,g13,g14]  ,   [g123,g234] ]
        //[ [gains of 2nonTouching], [gains of 3nonTouching], ...]
        return nonTouchingLoops;
    }
    private double calcDeterminant (List<List<String>> loops,List<List<String>> nonTouchingLoops){
        double delta=0;
        double sum=0;
        for (List<String> loop: loops)
            sum+=Double.parseDouble(loop.get(1));
        System.out.println("sum of individual loops: "+sum);//to be removed
        delta+=1-sum;
        // sum of non touching  loops
        int j=2;
        for (List<String> ntl: nonTouchingLoops){
            sum=0;
            for (String x: ntl)
                sum += Double.parseDouble(x);
            System.out.println("sum of non touching "+j+" loops: "+sum);//to be removed
            delta+=Math.pow(-1,j++)*sum;
        }
        return delta;
    }
    private List<List<String>> loopsNonTouchingPathI(List<String> forwardPath, List<List<String>> loops){
        List<List<String>> newLoops=new ArrayList<>();
        //check loops contain node from path i
        //remove loop that contain path i
        int i=0;
        String[] pathNodes=forwardPath.get(0).split(",");
        for (List<String> loop:loops){
            boolean found=false;
            String[] loopNodes=loop.get(0).split(",");
            //check if the loopNodes in the pathNodes
            //if Not found add to new loops
            for (String loopNode: loopNodes){
                for (String pathNode: pathNodes){
                    if (loopNode.equals(pathNode)){
                        found=true;
                        break;
                    }
                }
            }
            if (!found) newLoops.add(loop);
        }
        return newLoops;
    }
    public List<Double> calcDeterminants(List<List<String>> forwardPaths, List<List<String>> loops){
        List<Double> deltas=new ArrayList<>();
        List<List<String>> nonTouchingLoops=getNonTouchingLoops(loops);
        double overallDelta=calcDeterminant(loops,nonTouchingLoops);
        deltas.add(overallDelta);
        for (List<String> path: forwardPaths) {
            // calc delta for each forward path( delta i's)
            List<List<String>> newLoops = loopsNonTouchingPathI(path, loops);
            System.out.println("loops Non Touching path : " + newLoops);//to be removed
            // apply Method (get non touching) loops from newLoops
            nonTouchingLoops = getNonTouchingLoops(newLoops);
            double tmpDelta = calcDeterminant(newLoops, nonTouchingLoops);
            deltas.add(tmpDelta);
        }
        return deltas;
    }
    public double calcOverallTransferFunction(List<List<String>> forwardPaths, List<Double> deltas){
        double result=0;
        for (int i=0;i<forwardPaths.size();i++){
            double deltaI=deltas.get(i+1);
            double gainI=Double.parseDouble(forwardPaths.get(i).get(1));
            result += deltaI * gainI;
        }
        result /=deltas.get(0);
        return result;
    }
}
