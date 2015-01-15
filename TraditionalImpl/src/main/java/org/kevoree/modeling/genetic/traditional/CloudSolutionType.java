package org.kevoree.modeling.genetic.traditional;


import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import jmetal.encodings.variable.ArrayReal;
import jmetal.encodings.variable.Int;
import jmetal.encodings.variable.Real;

import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class CloudSolutionType extends SolutionType {

    private Random rand = new Random();

    public CloudSolutionType(Problem problem) {
        super(problem);
    }

    @Override
    public Variable[] createVariables() throws ClassNotFoundException {

        Variable[] temp= new Variable[2];

        for(int i=0; i<2;i++){
            temp[i]=new Real(0,1);
        }

        return temp;
    }
}
