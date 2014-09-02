package org.kevoree.modeling.genetic.cloudtest.mutators;

import jet.runtime.typeinfo.JetValueParameter;
import org.jetbrains.annotations.NotNull;
import org.kevoree.modeling.genetic.cloudtest.Context;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;
import polymer.Cloud;
import polymer.Software;
import polymer.Task;
import polymer.VmInstance;
import polymer.factory.DefaultPolymerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class AddInstanceMutator implements MutationOperator<Cloud> {

    private static Random rand = new Random();
    private static DefaultPolymerFactory pf = new DefaultPolymerFactory();

    @NotNull
    @Override
    public List<MutationVariable> enumerateVariables(@JetValueParameter(name = "model") @NotNull Cloud cloud) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "*"));
    }

    @Override
    public void mutate(@JetValueParameter(name = "model") @NotNull Cloud cloud, @JetValueParameter(name = "params") @NotNull MutationParameters mutationParameters) {
        if(cloud.getInstances().size()<Context.maxMachines) {
            int next = rand.nextInt(Context.cloud.getInstances().size());
            VmInstance original = Context.cloud.getInstances().get(next);
            VmInstance vm = pf.createVmInstance();
            vm.setName(original.getName());
            vm.setCpu(original.getCpu());
            vm.setPrice(original.getPrice());
            for (Software s : cloud.getSoftwares()) {
                Task t = pf.createTask();
                vm.addTasks(t);
                t.setSoftware(s);
                t.setWeight(rand.nextInt(100));
            }
            cloud.addInstances(vm);
            Context.distributeCpu(vm);
            Context.setTime(cloud);
        }

    }
}
