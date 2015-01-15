package org.kevoree.modeling.genetic.traditional;


import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.operators.crossover.Crossover;
import jmetal.operators.selection.Selection;
import jmetal.operators.selection.SelectionFactory;
import jmetal.util.JMException;
import org.kevoree.modeling.genetic.traditional.mutators.Globalmutator;

import java.util.HashMap;

/**
 * Settings class of algorithm NSGAIIRandom
 * Reference: Antonio J. Nebro, Juan José Durillo, Mirialys Machin Navas, Carlos A. Coello Coello, Bernabé Dorronsoro:
 * A Study of the Combination of Variation Operators in the NSGA-II Algorithm.
 * CAEPIA 2013: 269-278
 * DOI: http://dx.doi.org/10.1007/978-3-642-40643-0_28
 */
public class NSGAII_Settings extends Settings {
    public int populationSize_                 ;
    public int maxEvaluations_                 ;
    public double mutationProbability_         ;
    public double crossoverProbability_        ;
    public double mutationDistributionIndex_   ;
    public double crossoverDistributionIndex_  ;
    public double CR_                          ;
    public double F_                           ;

    /**
     * Constructor
     * @throws jmetal.util.JMException
     */
    public NSGAII_Settings(Problem problem) throws JMException {
        super(problem.getName()) ;

        Object [] problemParams = {"Real"};
            problem_ = problem;
        // Default settings
        populationSize_              =1000   ;
        maxEvaluations_              = 100000 ;
        mutationProbability_         = 0.05 ;
        crossoverProbability_        = 0.01 ;
        mutationDistributionIndex_   = 20 ;
        CR_                          = 1.0 ;
        F_                           = 0.5 ;
    } // NSGAII_Settings


    /**
     * Configure NSGAII with user-defined parameter settings
     * @return A NSGAII algorithm object
     * @throws jmetal.util.JMException
     */
    public Algorithm configure() throws JMException {
        Algorithm algorithm ;
        Selection  selection ;
        Crossover crossover ;

        HashMap  parameters ; // Operator parameters

        algorithm = new NSGAII(problem_) ;

        // Algorithm parameters
        algorithm.setInputParameter("populationSize",populationSize_);
        algorithm.setInputParameter("maxEvaluations",maxEvaluations_);
        algorithm.setInputParameter("crossoverProbability",0.0);
        crossover = new CloudCrossover(null);


        // Selection Operator
        parameters = null ;
        selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters) ;
        Operator mutation = new Globalmutator(parameters);

        algorithm.addOperator("selection",selection);
        algorithm.addOperator("crossover",crossover);
        algorithm.addOperator("mutation",mutation);


        return algorithm ;
    } // configure

} // NSGAIIRandom_Settings
