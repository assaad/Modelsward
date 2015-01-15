package org.kevoree.modeling.genetic.cloudtest;

import gaussian.Test;
import gaussian.factory.DefaultGaussianFactory;
import org.jetbrains.annotations.NotNull;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.optimization.api.PopulationFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class CloudPopulationFactory implements PopulationFactory<Test> {

    private DefaultGaussianFactory pf = new DefaultGaussianFactory();

    private Integer size = 100;
    private Random rand = new Random();

    public CloudPopulationFactory setSize(Integer nSize) {
        size = nSize;
        return this;
    }

    @NotNull
    @Override
    public List<Test> createPopulation() {
        ArrayList<Test> populations = new ArrayList<Test>();
        for (int i = 0; i < size; i++) {
            Test cloud = pf.createTest();
            cloud.setX(0.0);
            cloud.setY(rand.nextDouble());
            populations.add(cloud);
        }
        return populations;
    }

    @NotNull
    @Override
    public ModelCloner getCloner() {
        return new ModelCloner(pf);
    }

    @NotNull
    @Override
    public ModelCompare getModelCompare() {
        return new ModelCompare(pf);
    }
}
