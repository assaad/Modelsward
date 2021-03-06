package org.kevoree.modeling.genetic.traditional.mutators;

import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.operators.mutation.Mutation;
import jmetal.util.JMException;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by assaa_000 on 9/3/2014.
 */
public class ChangeWeightMutator extends Mutation {
    private Random rand = new Random();



    public ChangeWeightMutator(HashMap<String, Object> parameters) {
        super(parameters) ;
    }

    @Override
    public Object execute(Object object) throws JMException {

        Solution solution = (Solution) object;
        Variable[] vars = solution.getDecisionVariables();
        vars[0].setValue(rand.nextDouble());
        return solution;

    }
}
