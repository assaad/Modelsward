package org.kevoree.modeling.genetic.cloudtest.fitnesses;

import org.kevoree.modeling.genetic.cloudtest.Context;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import polymer.Cloud;
import polymer.Task;
import polymer.VmInstance;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class PriceFitness implements FitnessFunction<Cloud> {
    @Override
    public double evaluate(Cloud cloud, GenerationContext<Cloud> cloudGenerationContext) {
        double price =0;
        for(VmInstance v: cloud.getInstances()){
            double maxtime=0;
            for(Task t: v.getTasks()){
                if(t.getWeight()!=0&& t.getTime()>maxtime){
                    maxtime=t.getTime();
                }
            }
            price+= v.getPrice()*maxtime;
        }
        if(price> Context.maxPrice){
            return Context.maxPrice;

        }

        return price;
    }
}
