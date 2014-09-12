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
public class DeleteInstanceMutator extends Mutation {
    private Random rand = new Random();



    public DeleteInstanceMutator(HashMap<String, Object> parameters) {
        super(parameters) ;
    }

    @Override
    public Object execute(Object object) throws JMException {

        Solution solution = (Solution) object;
        int r= solution.getDecisionVariables().length/ (Context.softwareNum +1);
        if(r==0){
            return solution;
        }


        int newr= r-1;

        int x = rand.nextInt(r);


        Variable[] temp = new Variable[newr*(Context.softwareNum+1)];
        for(int i=0; i<x;i++){
            temp[i]=new Int( (int) solution.getDecisionVariables()[i].getValue() ,0,Context.instanceNum);
        }
        for(int i=x+1; i<r;i++){
            temp[i-1]=new Int( (int) solution.getDecisionVariables()[i].getValue() ,0,Context.instanceNum);
        }



        for(int i=r; i<r+Context.softwareNum*x;i++) {
            temp[i-1]=new Int( (int) solution.getDecisionVariables()[i].getValue(),0,100);
        }
        for(int i=r+Context.softwareNum*(x+1); i<(r*(Context.softwareNum+1));i++) {
            temp[i-1-Context.softwareNum]=new Int( (int) solution.getDecisionVariables()[i].getValue(),0,100);
        }

        solution.setDecisionVariables(temp);
        return solution;

    }
}
