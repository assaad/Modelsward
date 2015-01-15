package org.kevoree.modeling.genetic.cloudtest;


import gaussian.Test;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.DistanceFitness;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.XFitness;
import org.kevoree.modeling.genetic.cloudtest.mutators.MutateX;
import org.kevoree.modeling.genetic.cloudtest.mutators.MutateY;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:00
 */
public class SampleRunner {


    public static void main(String[] args) {

        GeneticEngine<Test> engine = new GeneticEngine<Test>();
        engine.setAlgorithm(GeneticAlgorithm.NSGAII);
        engine.addOperator(new MutateX());
        engine.addOperator(new MutateY());
        engine.addFitnessFunction(new DistanceFitness(),0,10,FitnessOrientation.MINIMIZE);
        engine.addGaussianFitnessFunction(new XFitness(),0,1,FitnessOrientation.MINIMIZE,0.4,0.2);

        engine.setPopulationFactory(new CloudPopulationFactory().setSize(100));
        engine.setMaxGeneration(100)  ;
        long startTime = System.nanoTime();
        List<Solution<Test>> result = engine.solve();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        for (Solution sol : result) {
            Set af  = sol.getFitnesses();
            Test cc = (Test) sol.getModel();
            Iterator iter = af.iterator();
            while (iter.hasNext())
            {
                FitnessFunction tf= (FitnessFunction) iter.next();
                System.out.print(tf.getClass().getName().replace("org.kevoree.modeling.genetic.cloudtest.fitnesses.","")+" "+ String.format("%.5f", sol.getRawScoreForFitness(tf))+" ");
            }
            System.out.println();
        }
        System.out.println("Duration: "+(double)duration / 1000000000.0+" seconds");
    }


}
