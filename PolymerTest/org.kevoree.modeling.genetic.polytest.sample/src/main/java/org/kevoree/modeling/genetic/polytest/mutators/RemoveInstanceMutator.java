package org.kevoree.modeling.genetic.polytest.mutators;

import jet.runtime.typeinfo.JetValueParameter;
import org.jetbrains.annotations.NotNull;
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
public class RemoveInstanceMutator implements MutationOperator<Cloud> {

    private static Random rand = new Random();
    private static DefaultPolymerFactory pf = new DefaultPolymerFactory();

    @NotNull
    @Override
    public List<MutationVariable> enumerateVariables(@JetValueParameter(name = "model") @NotNull Cloud cloud) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "*"));
    }

    @Override
    public void mutate(@JetValueParameter(name = "model") @NotNull Cloud cloud, @JetValueParameter(name = "params") @NotNull MutationParameters mutationParameters) {
       return;
    }
}
