package org.kevoree.modeling.genetic.cloudtest.mutators;


import gaussian.Test;
import jet.runtime.typeinfo.JetValueParameter;
import org.jetbrains.annotations.NotNull;
import org.kevoree.modeling.optimization.api.mutation.MutationOperator;
import org.kevoree.modeling.optimization.api.mutation.MutationParameters;
import org.kevoree.modeling.optimization.api.mutation.MutationVariable;
import org.kevoree.modeling.optimization.api.mutation.QueryVar;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by assaa_000 on 16/10/2014.
 */
public class MutateX implements MutationOperator<Test> {

    private static Random random = new Random();

    @NotNull
    @Override
    public List<MutationVariable> enumerateVariables(@NotNull @JetValueParameter(name = "model") Test model) {
        return Arrays.asList((MutationVariable) new QueryVar("target", "*"));
    }

    @Override
    public void mutate(@NotNull @JetValueParameter(name = "model") Test model, @NotNull @JetValueParameter(name = "params") MutationParameters params) {
      model.setX(random.nextDouble()*0.4+0.3);

    }
}
