package org.kevoree.modeling.genetic.cloudtest.fitnesses;


import gaussian.Test;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;

import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class XFitness implements FitnessFunction<Test> {


    @Override
    public double evaluate(Test model, GenerationContext<Test> context)
    {
       return model.getX();
      /*  Random rand = new Random();
        if(rand.nextBoolean())
            return 0.3;
        else
            return 0.5;*/
    }
}
