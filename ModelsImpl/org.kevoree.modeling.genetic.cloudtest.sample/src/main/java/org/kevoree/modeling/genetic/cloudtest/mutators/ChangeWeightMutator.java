package org.kevoree.modeling.genetic.cloudtest.mutators;

import jet.runtime.typeinfo.JetValueParameter;
import org.jetbrains.annotations.NotNull;
import org.kevoree.modeling.genetic.cloudtest.Context;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;
import polymer.Cloud;
import polymer.Task;
import polymer.VmInstance;
import polymer.factory.DefaultPolymerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class ChangeWeightMutator implements MutationOperator<Cloud> {

    private static Random rand = new Random();
    private static DefaultPolymerFactory pf = new DefaultPolymerFactory();

    @NotNull
    @Override
    public List<MutationVariable> enumerateVariables(@JetValueParameter(name = "model") @NotNull Cloud cloud) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "*"));
    }

    @Override
    public void mutate(@JetValueParameter(name = "model") @NotNull Cloud cloud, @JetValueParameter(name = "params") @NotNull MutationParameters mutationParameters) {
        if(cloud.getInstances().size()==0)
            return;


        for(VmInstance v: cloud.getInstances()){
            for(Task t: v.getTasks()){
                if(rand.nextDouble()<Context.mutationProba){
                    t.setWeight(rand.nextInt(100));
                }
            }
        }
        Context.distributeCpu(cloud);
        Context.setTime(cloud);
    }
}
