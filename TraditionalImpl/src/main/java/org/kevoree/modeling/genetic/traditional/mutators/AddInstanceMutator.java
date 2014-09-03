package org.kevoree.modeling.genetic.traditional.mutators;

import jmetal.core.Solution;
import jmetal.core.Variable;
import jmetal.encodings.variable.Int;
import jmetal.operators.mutation.Mutation;
import jmetal.util.JMException;
import org.kevoree.modeling.genetic.traditional.Context;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by assaa_000 on 9/3/2014.
 */
public class AddInstanceMutator extends Mutation {
    private Random rand = new Random();



    public AddInstanceMutator(HashMap<String, Object> parameters) {
        super(parameters) ;
    }

    @Override
    public Object execute(Object object) throws JMException {

        Solution solution = (Solution) object;
        int r= solution.getDecisionVariables().length/ (Context.softwareNum +1);
        if(r>Context.maxMachines){
            return solution;
        }

        int newr= r+1;


        Variable[] temp = new Variable[newr*(Context.softwareNum+1)];
        for(int i=0; i<r;i++){
            temp[i]=new Int( (int) solution.getDecisionVariables()[i].getValue() ,0,Context.instanceNum);
        }
        temp[r]=new Int( rand.nextInt(Context.instanceNum),0,Context.instanceNum);

        for(int i=r+1; i<r*(Context.softwareNum+1)+1;i++) {
            temp[i]=new Int( (int) solution.getDecisionVariables()[i-1].getValue(),0,100);
        }
        for(int i=r*(Context.softwareNum+1)+1; i<newr*(Context.softwareNum+1);i++) {
            temp[i]=new Int(rand.nextInt(100),0,100);
        }

        solution.setDecisionVariables(temp);
        return solution;

    }
}
