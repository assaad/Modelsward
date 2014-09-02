package org.kevoree.modeling.genetic.traditional;


import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class TraditionalSolution extends SolutionType {

    public TraditionalSolution(Problem problem) {
        super(problem);
    }

    @Override
    public Variable[] createVariables() throws ClassNotFoundException {

        return new Variable[0];
    }
}
