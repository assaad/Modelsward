package org.kevoree.modeling.genetic.polytest.fitnesses;

import org.kevoree.modeling.genetic.polytest.Context;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import polymer.Cloud;
import polymer.Software;

import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class TimeFitness implements FitnessFunction<Cloud> {
    private Random random = new Random();
    @Override
    public double evaluate(Cloud cloud, GenerationContext<Cloud> cloudGenerationContext) {

        return random.nextDouble()* Context.maxTime;
    }


}
