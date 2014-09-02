package org.kevoree.modeling.genetic.cloudtest.fitnesses;

import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import polymer.Cloud;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class NumVmFitness implements FitnessFunction<Cloud> {
    @Override
    public double evaluate(Cloud cloud, GenerationContext<Cloud> cloudGenerationContext) {
        return cloud.getInstances().size();
    }
}
