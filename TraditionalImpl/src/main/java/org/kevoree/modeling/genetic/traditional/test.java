package org.kevoree.modeling.genetic.traditional;

import jmetal.core.Solution;
import jmetal.core.Variable;
import org.kevoree.modeling.genetic.cloudtest.*;
import org.kevoree.modeling.genetic.cloudtest.SampleRunner;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.PriceFitness;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.TimeFitness;
import org.kevoree.modeling.genetic.cloudtest.mutators.AddInstanceMutator;
import org.kevoree.modeling.genetic.cloudtest.mutators.ChangeWeightMutator;
import org.kevoree.modeling.genetic.cloudtest.mutators.RemoveInstanceMutator;
import org.kevoree.modeling.genetic.traditional.mutators.Globalmutator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import polymer.Cloud;

import java.util.Random;

/**
 * Created by assaa_000 on 9/15/2014.
 */
public class test {
        public static void main(String[] args) {

            org.kevoree.modeling.genetic.traditional.SampleRunner.load();
            TraditionalProblem tp = new TraditionalProblem();
            CloudSolutionType ct= new CloudSolutionType(tp);
            try {
                Variable[] var= ct.createVariables();
                Solution solution = new Solution(2);
                solution.setDecisionVariables(var);
                Random random=new Random();

                long startTime = System.nanoTime();
                for(int i=0; i<1000;i++){
                    for(int j=0;j<1;j++) {
                        Globalmutator gb = new Globalmutator(null);
                        gb.execute(solution);
                        tp.evaluate(solution);
                    }
                }

                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                System.out.println("Duration: "+(double)duration / 1000000000.0+" seconds");

            }
            catch (Exception ex){
                ex.printStackTrace();
            }



        }
    }
