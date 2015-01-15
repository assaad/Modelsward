package org.kevoree.modeling.genetic.traditional;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.core.Variable;
import jmetal.encodings.variable.Int;
import org.kevoree.modeling.genetic.cloudtest.*;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.PriceFitness;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.TimeFitness;
import polymer.Cloud;
import polymer.Software;
import polymer.Task;
import polymer.VmInstance;
import polymer.factory.DefaultPolymerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class SampleRunner {


    public static void main(String[] args) {

        try {

            Problem p = new TraditionalProblem();
            Algorithm a = new NSGAII_Settings(p).configure();


            long startTime = System.nanoTime();
            SolutionSet pop = a.execute();
            long endTime = System.nanoTime();
            long duration = endTime - startTime;


            System.out.println("Duration: "+(double)duration / 1000000000.0+" seconds");

          for (int i = 0; i < pop.size(); i++) {

              double x=pop.get(i).getDecisionVariables()[0].getValue();
              double y=pop.get(i).getDecisionVariables()[1].getValue();
              double g= 1+9*y;
              double f= g*(1-Math.sqrt(x/g));

              System.out.print(x+ " ");
              //System.out.print(pop.get(i).getDecisionVariables()[1]+ " ");
              //System.out.print(pop.get(i).getObjective(0)+ " ");
              System.out.print(f + " ");

                /*for (int j = 0; j < 2; j++) {
                    System.out.print(pop.get(i).getObjective(j) + " ");
                }*/
                System.out.println("");
            }



        } catch (Exception e) {
       e.printStackTrace();
         }

    }
}
