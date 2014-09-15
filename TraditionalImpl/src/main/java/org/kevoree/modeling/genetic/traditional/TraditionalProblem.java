package org.kevoree.modeling.genetic.traditional;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.util.JMException;

import java.util.Random;

/**
 * Created by assaa_000 on 9/2/2014.
 */
public class TraditionalProblem extends Problem {

    public static double[] vmCPU;
    public static double[] vmPrice;
    public static double[] softwareCpuh;


    public TraditionalProblem(){
        numberOfObjectives_  = 2;
        numberOfConstraints_ = 0;
        problemName_         = "Cloud" ;
        solutionType_ = new CloudSolutionType(this);
    }

    @Override
    public void evaluate(Solution solution) throws JMException {
        int r= solution.getDecisionVariables().length/ (Context.softwareNum +1);

        int[] vmInstances = new int[r];
        int[][] weights = new int[r][Context.softwareNum];

        for(int i=0; i<r;i++){
            vmInstances[i]=(int) solution.getDecisionVariables()[i].getValue();
        }

        for(int i=0; i<r;i++){
            for(int j=0; j<Context.softwareNum;j++){
                weights[i][j]=(int) solution.getDecisionVariables()[r+i*Context.softwareNum+j].getValue();
            }
        }

        int[] sumOfWeights = new int[r];
        double[][] cpu = new double[r][Context.softwareNum];
        double[] times = new double[Context.softwareNum];

        for(int i=0; i<r;i++) {
            sumOfWeights[i]=0;
            for (int j = 0; j < Context.softwareNum; j++) {
                sumOfWeights[i]+=weights[i][j];
            }
        }

        for(int i=0; i<r;i++) {
            if(sumOfWeights[i]!=0) {
                for (int j = 0; j < Context.softwareNum; j++) {
                    cpu[i][j]=(vmCPU[vmInstances[i]]*weights[i][j])/sumOfWeights[i];
                }
            }
        }

        double avgtime=0;
        for (int j = 0; j < Context.softwareNum; j++) {
            double totalcpu=0;
            for(int i=0; i<r;i++) {
                totalcpu+=cpu[i][j];
            }
            if(totalcpu!=0){
                times[j]=softwareCpuh[j]/totalcpu;
            }
            else{
                times[j]=Context.maxTime;
            }
            avgtime+=times[j];
        }
        avgtime=avgtime/Context.softwareNum;

        double price=0;

        for(int i=0; i<r;i++) {
            double maxtime=0;
            for (int j = 0; j < Context.softwareNum; j++) {
                if(weights[i][j]!=0 &&times[j]>maxtime){
                    maxtime=times[j];
                }
            }
            price+= vmPrice[vmInstances[i]]*maxtime;
        }



        Random rand = new Random();
        if(rand.nextInt(100)<8){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        solution.setObjective(0, avgtime);
        solution.setObjective(1, price);




    }
}
