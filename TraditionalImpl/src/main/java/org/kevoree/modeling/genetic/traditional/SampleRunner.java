package org.kevoree.modeling.genetic.traditional;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.core.Variable;
import jmetal.encodings.variable.Int;
import org.kevoree.modeling.genetic.cloudtest.*;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.PriceFitness;
import org.kevoree.modeling.genetic.cloudtest.fitnesses.TimeFitness;
import polymer.Cloud;
import polymer.Software;
import polymer.Task;
import polymer.VmInstance;
import polymer.factory.DefaultPolymerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class SampleRunner {



    public static void load(){
        try {
            //Load list of clouds
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            //Load Cloud Provider
            InputStream is = classloader.getResourceAsStream("cloudProviders.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            ArrayList<Double> cpus= new ArrayList<Double>();
            ArrayList<Double> prices = new ArrayList<Double>();
            ArrayList<Double> cpuhs=new ArrayList<Double>();

            for (String line; (line = br.readLine()) != null; ) {
                line = line.trim();
                if (line.startsWith("#"))
                    continue;
                String[] fields = line.split(",");
                String cpu = fields[2].trim();
                String price = fields[6].trim();


                if (cpu.equals("") == false)
                    cpus.add(Double.parseDouble(cpu));

                if (price.equals("") == false) {
                    price = price.replace("$", "");
                   prices.add(Double.parseDouble(price));
                }
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

                String cpuh = fields[1].trim();
                cpuhs.add(Double.parseDouble(cpuh));

            }

            brSoft.close();
            isoft.close();
            System.out.println("Loaded " + cpus.size() + " Vm Instances and " + cpuhs.size() + " softwares");
            TraditionalProblem.vmCPU=new double[cpus.size()];
            TraditionalProblem.vmPrice=new double[prices.size()];
            TraditionalProblem.softwareCpuh=new double[cpuhs.size()];

            for(int i=0; i< cpus.size();i++){
                TraditionalProblem.vmCPU[i]=cpus.get(i);
                TraditionalProblem.vmPrice[i]=prices.get(i);
            }
            for(int i=0; i< cpuhs.size();i++){
                TraditionalProblem.softwareCpuh[i]=cpuhs.get(i);
            }
        }
        catch (Exception ex){
            ex.printStackTrace(System.out);
        }
    }


    public static Cloud createCloud() {
        DefaultPolymerFactory pf = new DefaultPolymerFactory();
        Cloud cloud = pf.createCloud();
        for (int i=0; i<TraditionalProblem.softwareCpuh.length;i++) {
            Software soft = pf.createSoftware();
            soft.setCpuh(TraditionalProblem.softwareCpuh[i]);
            soft.setId(i);
            cloud.addSoftwares(soft);
        }
        return cloud;
    }

    public static void main(String[] args) {

        try {

            load();
            Problem p = new TraditionalProblem();

            Algorithm a = new NSGAII_Settings(p).configure();


            long startTime = System.nanoTime();
            SolutionSet pop = a.execute();
            long endTime = System.nanoTime();
            long duration = endTime - startTime;


            System.out.println("Duration: "+(double)duration / 1000000000.0+" seconds");

          //  Thread.sleep(10000);


         /*   for (int i = 0; i < pop.size(); i++) {
                Variable v = pop.get(i).getDecisionVariables()[0];
                System.out.println("Conf" + (i + 1) + ": " + (Binary) v + " ");

            }*/


           DefaultPolymerFactory pf = new DefaultPolymerFactory();

            for (int i = 0; i < pop.size(); i++) {
                System.out.print("jmetal ");
                for (int j = 0; j < 2; j++) {
                    System.out.print(pop.get(i).getObjective(j) + " ");
                }

               Cloud c= createCloud();
                Variable[] var = pop.get(i).getDecisionVariables();
                int r= var.length/ (Context.softwareNum+1);
                for(int k=0;k<r;k++){
                    VmInstance v = pf.createVmInstance();
                    v.setPrice(TraditionalProblem.vmPrice[(int)var[k].getValue()]);
                    v.setCpu(TraditionalProblem.vmCPU[(int)var[k].getValue()]);

                    int m=0;
                    for(int l=r+k*Context.softwareNum; l<r+(k+1)*Context.softwareNum;l++){
                        Task t= pf.createTask();
                        v.addTasks(t);

                        for(int y=0;y<7;y++){
                            if(c.getSoftwares().get(y).getCpuh()==TraditionalProblem.softwareCpuh[m])
                                t.setSoftware(c.getSoftwares().get(y));
                        }
                        t.setWeight((int)var[l].getValue());
                        m++;
                    }
                    c.addInstances(v);
                }
                org.kevoree.modeling.genetic.cloudtest.Context.distributeCpu(c);
                org.kevoree.modeling.genetic.cloudtest.Context.setTime(c);
                double timx= new TimeFitness().evaluate(c, null);
                double prix = new PriceFitness().evaluate(c,null);
                TraditionalProblem ttt = new TraditionalProblem();
                ttt.evaluate(pop.get(i));
                System.out.print(" polymer "+ timx + " "+ prix);
                System.out.println("");
            }
        } catch (Exception e) {
       e.printStackTrace();
         }

    }
}
