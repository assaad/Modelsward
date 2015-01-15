package org.kevoree.modeling.genetic.traditional;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.util.JMException;

import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class TraditionalProblem extends Problem {

    public static double[] vmCPU;
    public static double[] vmPrice;
    public static double[] softwareCpuh;


    public TraditionalProblem(){
        numberOfObjectives_  = 2;
        numberOfConstraints_ = 0;
        problemName_         = "Cloud" ;
        solutionType_ = new CloudSolutionType(this);
    }

    @Override
    public void evaluate(Solution solution) throws JMException {
        Variable[] r= solution.getDecisionVariables();

        double x=r[0].getValue();
        double y=r[1].getValue();

        double g= 1+9*y;
        double f= g*(1-Math.sqrt(x/g));


        solution.setObjective(0, gaussian(x,0.2,0.2));
      // solution.setObjective(0, x);
       // solution.setObjective(1, f);
        solution.setObjective(1,gaussian(f,0.5,0.2));
    }

    public double gaussian(double value, double target, double std){
        return (1-Math.exp(-((value-target)*(value-target))/(2*std*std)));
    }
}
