package org.kevoree.modeling.genetic.cloudtest;

import polymer.Cloud;
import polymer.Software;
import polymer.Task;
import polymer.VmInstance;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class Context {
    public static Cloud cloud=null;

    public static double maxTime=15000;
    public static double maxPrice=100000;
    public static int maxMachines = 300;

    public static void setTime(Cloud c){
        for(Software s: c.getSoftwares()){
            s.setTime(getTime(s));
        }

    }



    private static double getTime(Software software){
        if(software.getTasks().size()==0){
            return Context.maxTime;
        }

        double vcpu=0;
        for(Task t: software.getTasks()){
            vcpu+=t.getCpu();
        }
        if(vcpu!=0) {
            return software.getCpuh() / vcpu;
        }
        return maxTime;
    }


    public static void distributeCpu(VmInstance vm){
        if(vm.getTasks().size()!=0){
            int total=0;
            for(Task t:vm.getTasks()){
                total+=t.getWeight();
            }

            if(total!=0) {
                for (Task t : vm.getTasks()) {
                    double x= ((double) t.getWeight()*vm.getCpu())/total;
                    t.setCpu(x);
                }
            }
            else{
                for (Task t : vm.getTasks()) {
                    t.setCpu(0.0);
                }

            }
        }
    }



}
