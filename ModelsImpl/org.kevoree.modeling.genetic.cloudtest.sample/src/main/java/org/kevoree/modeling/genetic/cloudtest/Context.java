package org.kevoree.modeling.genetic.cloudtest;

import polymer.Cloud;
import polymer.Software;
import polymer.Task;
import polymer.VmInstance;

import java.util.ArrayList;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class Context {
    public static Cloud cloud = null;

    public static double maxTime = 15000;
    public static double maxPrice = 100000;
    public static int maxMachines = 100;
    public static double mutationProba = 0.01;


    public static void setTime(Cloud c) {
        for (Software s : c.getSoftwares()) {
            setTime(s,c);
        }

    }


    private static double setTime(Software software, Cloud cloud) {

        ArrayList<Task> al = new ArrayList<Task>();

        for(VmInstance v: cloud.getInstances()){
            for(Task t: v.getTasks()){
                if(t.getSoftware()==software){
                    al.add(t);
                }
            }
        }

        if (al.size() == 0) {
            return Context.maxTime;
        }

        double vcpu = 0;


        for (Task t : al) {
            vcpu += t.getCpu();
        }
        double time = maxTime;

        if (vcpu != 0) {
            time= software.getCpuh() / vcpu;
        }

        for (Task t : al) {
           t.setTime(time);
        }

        return time;
    }

    public static void distributeCpu(Cloud c) {
        for (VmInstance v : c.getInstances()) {
            distributeCpu(v);
        }
    }


    public static void distributeCpu(VmInstance vm) {
        if (vm.getTasks().size() != 0) {
            int total = 0;
            for (Task t : vm.getTasks()) {
                total += t.getWeight();
            }

            if (total != 0) {
                for (Task t : vm.getTasks()) {
                    double x = ((double) t.getWeight() * vm.getCpu()) / total;
                    t.setCpu(x);
                }
            } else {
                for (Task t : vm.getTasks()) {
                    t.setCpu(0.0);
                }

            }
        }
    }


}
