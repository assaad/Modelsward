package org.kevoree.modeling.genetic.traditional;

import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.variable.ArrayReal;
import jmetal.operators.crossover.Crossover;
import jmetal.util.JMException;

import java.util.HashMap;

/**
 * Created by assaa_000 on 9/3/2014.
 */
public class CloudCrossover extends Crossover {
    public CloudCrossover(HashMap<String, Object> parameters) {
        super(parameters);
    }

    @Override
    public Object execute(Object object) throws JMException {
        Solution [] parents = (Solution[])object;

        Solution [] offSpring = new Solution[2];

        offSpring[0] = new Solution(parents[0]);
        offSpring[1] = new Solution(parents[1]);

        return offSpring;
    }
}
