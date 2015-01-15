package org.kevoree.modeling.genetic.cloudtest.fitnesses;


import gaussian.Test;
import org.kevoree.modeling.optimization.api.GenerationContext;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class DistanceFitness implements FitnessFunction<Test> {


    @Override
    public double evaluate(Test model, GenerationContext<Test> context) {

        double g= 1+9*model.getY();
        return g*(1-Math.sqrt(model.getX()/g));

       // return Math.abs(1- model.getX()- model.getY());
    }
}
