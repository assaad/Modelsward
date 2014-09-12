package org.kevoree.modeling.genetic.cloudtest;

import org.jetbrains.annotations.NotNull;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.optimization.api.PopulationFactory;
import polymer.Cloud;
import polymer.Software;
import polymer.Task;
import polymer.VmInstance;
import polymer.factory.DefaultPolymerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class CloudPopulationFactory implements PopulationFactory<Cloud> {

    private DefaultPolymerFactory pf = new DefaultPolymerFactory();

    private Integer size = 5;

    private Random rand = new Random();

    public CloudPopulationFactory setSize(Integer nSize) {
        size = nSize;
        return this;
    }

    @NotNull
    @Override
    public List<Cloud> createPopulation() {
        ArrayList<Cloud> populations = new ArrayList<Cloud>();
        for (int i = 0; i < size; i++) {
            Cloud cloud = pf.createCloud();
            for(Software str: Context.cloud.getSoftwares()){
                Software soft = pf.createSoftware();
                soft.setName(str.getName());
                soft.setCpuh(str.getCpuh());
                cloud.addSoftwares(soft);
                soft.setRecursiveReadOnly();
            }

            int x= rand.nextInt(Context.maxMachines);

            for(int j=0; j<x; j++){
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
            }

            Context.distributeCpu(cloud);
            Context.setTime(cloud);
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
