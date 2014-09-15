package org.kevoree.modeling.genetic.cloudtest;

import org.kevoree.modeling.genetic.cloudtest.fitnesses.PriceFitness;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.TimeFitness;
import org.kevoree.modeling.genetic.cloudtest.mutators.AddInstanceMutator;
import org.kevoree.modeling.genetic.cloudtest.mutators.ChangeWeightMutator;
import org.kevoree.modeling.genetic.cloudtest.mutators.RemoveInstanceMutator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.solution.Solution;
import polymer.Cloud;

import java.util.List;
import java.util.Random;

/**
 * Created by assaa_000 on 9/15/2014.
 */
public class test {
    public static void main(String[] args) {
        Cloud c= SampleRunner.load();
        Context.cloud= SampleRunner.load();
        Random random=new Random();

        long startTime = System.nanoTime();
        for(int i=0; i<100000;i++){
            for(int j=0;j<1;j++) {
                int x = random.nextInt(3);
                if (x == 0) {
                    AddInstanceMutator addInstanceMutator = new AddInstanceMutator();
                    addInstanceMutator.mutate(c, new MutationParameters());
                } else if (x == 1) {
                    ChangeWeightMutator ch = new ChangeWeightMutator();
                    ch.mutate(c, new MutationParameters());
                } else {
                    RemoveInstanceMutator rm = new RemoveInstanceMutator();
                    rm.mutate(c, new MutationParameters());
                }

                PriceFitness pf = new PriceFitness();
                pf.evaluate(c, null);

                TimeFitness tf = new TimeFitness();
                tf.evaluate(c, null);
            }
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Duration: "+(double)duration / 1000000000.0+" seconds");

    }
}
