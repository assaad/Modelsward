package org.kevoree.modeling.genetic.cloudtest;




import org.kevoree.modeling.genetic.cloudtest.fitnesses.NumVmFitness;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.PriceFitness;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.TimeFitness;
import org.kevoree.modeling.genetic.cloudtest.mutators.AddInstanceMutator;
import org.kevoree.modeling.genetic.cloudtest.mutators.ChangeWeightMutator;
import org.kevoree.modeling.genetic.cloudtest.mutators.RemoveInstanceMutator;
import org.kevoree.modeling.optimization.api.fitness.FitnessFunction;
import org.kevoree.modeling.optimization.api.fitness.FitnessOrientation;
import org.kevoree.modeling.optimization.api.solution.Solution;
import org.kevoree.modeling.optimization.engine.genetic.GeneticAlgorithm;
import org.kevoree.modeling.optimization.engine.genetic.GeneticEngine;
import polymer.Cloud;
import polymer.Software;
import polymer.VmInstance;
import polymer.factory.DefaultPolymerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: duke
 * Date: 07/08/13
 * Time: 16:00
 */
public class SampleRunner {


    public static Cloud load(){

        DefaultPolymerFactory pf = new DefaultPolymerFactory();
        Cloud cloud = pf.createCloud();

        //Load list of clouds
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try {

            //Load Cloud Provider
            InputStream is = classloader.getResourceAsStream("cloudProviders.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (String line; (line = br.readLine()) != null; ) {
                line = line.trim();
                if (line.startsWith("#"))
                    continue;
                String[] fields = line.split(",");
                String name = fields[1].trim();
                String cpu = fields[2].trim();
                String price = fields[6].trim();

                VmInstance vm = pf.createVmInstance();
                vm.setName(name);

                if (cpu.equals("") == false)
                    vm.setCpu(Double.parseDouble(cpu));

                if (price.equals("") == false) {
                    price = price.replace("$", "");
                    vm.setPrice(Double.parseDouble(price));
                }
                cloud.addInstances(vm);
            }

            //Load softwares
            InputStream isoft = classloader.getResourceAsStream("softwares.txt");
            BufferedReader brSoft = new BufferedReader(new InputStreamReader(isoft));

            for (String line; (line = brSoft.readLine()) != null; ) {
                line = line.trim();
                if (line.startsWith("#"))
                    continue;
                String[] fields = line.split(",");
                //#Name , CpuPerUser , RamPerUser

                String name = fields[0].trim();
                String cpuh = fields[1].trim();

                Software software =pf.createSoftware();
                software.setName(name);
                software.setCpuh(Double.parseDouble(cpuh));
                cloud.addSoftwares(software);
            }

            brSoft.close();
            isoft.close();
            System.out.println("Loaded "+cloud.getInstances().size()+" Vm Instances and "+cloud.getSoftwares().size()+" softwares");
        }
        catch (Exception ex){
            ex.printStackTrace(System.out);
        }
        return cloud;
    }



    public static void main(String[] args) {
        Context.cloud= load();

        GeneticEngine<Cloud> engine = new GeneticEngine<Cloud>();
        engine.setAlgorithm(GeneticAlgorithm.HypervolumeNSGAII);


        engine.addOperator(new AddInstanceMutator());
        engine.addOperator(new ChangeWeightMutator());
        engine.addOperator(new RemoveInstanceMutator());

        engine.addFitnessFunction(new NumVmFitness(),0,Context.maxMachines, FitnessOrientation.MINIMIZE);
        engine.addFitnessFunction(new PriceFitness(),0,Context.maxPrice, FitnessOrientation.MINIMIZE);
        engine.addFitnessFunction(new TimeFitness(),0,Context.maxTime, FitnessOrientation.MINIMIZE);


        engine.setPopulationFactory(new CloudPopulationFactory().setSize(20));

        engine.setMaxGeneration(500)  ;


        long startTime = System.nanoTime();
        List<Solution<Cloud>> result = engine.solve();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;


        for (Solution sol : result) {
            Set af  = sol.getFitnesses();

            Cloud cc = (Cloud) sol.getModel();

            Iterator iter = af.iterator();
            while (iter.hasNext())
            {
                FitnessFunction tf= (FitnessFunction) iter.next();

                System.out.print(tf.getClass().getName().replace("org.kevoree.modeling.genetic.cloudtest.fitnesses.","")+" "+ String.format("%.5f", sol.getRawScoreForFitness(tf))+" ");
            }
           // System.out.print(" VM "+cc.getInstances().size());
            System.out.println();
        }
        System.out.println("Duration: "+(double)duration / 1000000000.0+" seconds");


    }





}
