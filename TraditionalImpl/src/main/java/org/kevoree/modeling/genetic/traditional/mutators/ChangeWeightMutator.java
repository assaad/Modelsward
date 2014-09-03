package org.kevoree.modeling.genetic.traditional.mutators;

import jmetal.core.Solution;
import jmetal.operators.mutation.Mutation;
import jmetal.util.JMException;
import org.kevoree.modeling.genetic.traditional.Context;

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
        int r= solution.getDecisionVariables().length/ (Context.softwareNum+1);

        for (int i = r; i < solution.getDecisionVariables().length; i++) {
            if (rand.nextDouble() < Context.mutationProba) {
                int value = rand.nextInt(100);
                solution.getDecisionVariables()[i].setValue(value);
            }
        }
        return solution;

    }
}
