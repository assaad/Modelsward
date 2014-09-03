package org.kevoree.modeling.genetic.traditional;


import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import jmetal.encodings.variable.Int;

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
        int r = rand.nextInt(Context.maxMachines);

        Variable[] temp = new Variable[r*(Context.softwareNum+1)];
        for(int i=0; i<r;i++){
            temp[i]=new Int( rand.nextInt(Context.instanceNum),0,Context.instanceNum);
        }
        for(int i=r; i<r*(Context.softwareNum+1);i++) {
            temp[i]=new Int(rand.nextInt(100),0,100);
        }
        return temp;
    }
}
