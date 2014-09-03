package org.kevoree.modeling.genetic.traditional.mutators;

import jmetal.operators.mutation.Mutation;
import jmetal.util.JMException;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by assaa_000 on 9/3/2014.
 */
public class Globalmutator extends Mutation {
    private HashMap<String, Object> param;

    public Globalmutator(HashMap<String, Object> parameters) {
        super(parameters);
        this.param=parameters;
    }

    private Random rand = new Random();

    @Override
    public Object execute(Object o) throws JMException {
        int x= rand.nextInt(3);

        if(x==0){
            AddInstanceMutator ad = new AddInstanceMutator(this.param);
            return ad.execute(o);
        }
        else if (x==1){
            ChangeWeightMutator cw= new ChangeWeightMutator(this.param);
            return cw.execute(o);
        }
        else{
            DeleteInstanceMutator dt = new DeleteInstanceMutator(this.param);
            return dt.execute(o);
        }
    }
}
