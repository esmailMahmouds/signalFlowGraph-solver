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
        System.out.println("sum of individual loops: "+sum);
        delta+=1-sum;
        // sum of non touching  loops
        int j=2;
        for (List<String> ntl: nonTouchingLoops){
            sum=0;
            for (String x: ntl)
                sum += Double.parseDouble(x);
            System.out.println("sum of non touching "+j+" loops: "+sum);
            delta+=Math.pow(-1,j++)*sum;
        }
        return delta;
    }
    public List<List<String>> loopsNonTouchingPathI(List<String> forwardPath, List<List<String>> loops){
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
    private double calcDeltaPathI(List<String> forwardPath, List<List<String>> loops){
        // calc delta for each forward path( delta i's)
        List<List<String>> newLoops = loopsNonTouchingPathI(forwardPath, loops);
        System.out.println("loops Non Touching path : " + newLoops);
        // apply Method (get non touching) loops from newLoops
        List<List<String>> nonTouchingLoops=getNonTouchingLoops(newLoops);
        // apply Method (calc Determinant)
        double delta=calcDeterminant(newLoops,nonTouchingLoops);
        return delta;
    }
    public double calcOverallTransferFunction(List<List<String>> forwardPaths, List<List<String>> loops){
        double result=0;
        List<List<String>> nonTouchingLoops=getNonTouchingLoops(loops);
        double overallDelta=calcDeterminant(loops,nonTouchingLoops);
        for (List<String> path: forwardPaths){
            double deltaI=calcDeltaPathI(path,loops);
            double gainI=Double.parseDouble(path.get(1));
            result += deltaI * gainI;
        }
        result /=overallDelta;
        return result;
    }
}
