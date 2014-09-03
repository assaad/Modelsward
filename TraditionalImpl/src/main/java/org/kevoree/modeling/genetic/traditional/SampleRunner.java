package org.kevoree.modeling.genetic.traditional;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;

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


         /*   for (int i = 0; i < pop.size(); i++) {
                Variable v = pop.get(i).getDecisionVariables()[0];
                System.out.println("Conf" + (i + 1) + ": " + (Binary) v + " ");

            }*/

            for (int i = 0; i < pop.size(); i++) {
                for (int j = 0; j < pop.get(i).getNumberOfObjectives(); j++) {
                    System.out.print(pop.get(i).getObjective(j) + " ");
                }
                System.out.println("");
            }
        } catch (Exception e) {
       e.printStackTrace();
         }

    }
}
