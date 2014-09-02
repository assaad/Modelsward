package org.kevoree.modeling.genetic.cloudtest.fitnesses;

import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import polymer.Cloud;
import polymer.Software;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class TimeFitness implements FitnessFunction<Cloud> {
    @Override
    public double evaluate(Cloud cloud, GenerationContext<Cloud> cloudGenerationContext) {

        double time =0;
        for(Software s: cloud.getSoftwares()){
            time+= s.getTime();
        }
        if(cloud.getSoftwares().size()!=0){
            time=time/cloud.getSoftwares().size();
        }
        return time;
    }


}
