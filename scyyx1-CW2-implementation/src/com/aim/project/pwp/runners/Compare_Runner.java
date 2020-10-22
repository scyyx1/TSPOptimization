package com.aim.project.pwp.runners;

import java.util.Random;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.hyperheuristics.RLILS_ALA_HH;
import com.aim.project.pwp.hyperheuristics.SR_IE_HH;

import AbstractClasses.HyperHeuristic;

public class Compare_Runner {
	final TestFrameConfirg config;
	final int TOTAL_RUNS = 11;
	final String[] DOMAINS;
	final int[] INSTANCE_IDs;
	final long RUN_TIME;
	final long[] SEEDS;
	final int defaultScore, lowerBound, upperBound;
	final long timeLimit = 60_000l;
	
	public Compare_Runner(TestFrameConfirg config) {
		
		this.config = config;
		
		/*
		 * Generation of SEED values
		 */
		Random random = new Random(10022017L);
		SEEDS = new long[TOTAL_RUNS];
		
		for(int i = 0; i < TOTAL_RUNS; i++)
		{
			SEEDS[i] = random.nextLong();
		}
		
		this.DOMAINS = config.PROBLEM_DOMAINS;
		this.INSTANCE_IDs = config.getInstanceIDs();
		this.RUN_TIME = config.getRunTime();
		this.defaultScore = config.DEFAULT_SCORE;
		this.lowerBound = config.LOWER_BOUND;
		this.upperBound = config.UPPER_BOUND;
	}
	public void runTests() {
	

			for(int instance = 0; instance < INSTANCE_IDs.length; instance++) {
				int instanceID = INSTANCE_IDs[instance];
				double[] rankForMyHH = new double[TOTAL_RUNS];
				double[] rankForSR = new double[TOTAL_RUNS];
				// Used for Question2
//				double[] bestValues = new double[TOTAL_RUNS];
//				double bestValue = 999999999;
//				int bestIndex = 0;
//				double totalValue = 0;
				for(int run = 0; run < TOTAL_RUNS; run++) {
					
					long seed = SEEDS[run];
					
					// Used for Question3
					AIM_PWP problem1 = new AIM_PWP(seed);
					problem1.loadInstance(instanceID);
					HyperHeuristic myhh = new RLILS_ALA_HH(seed, defaultScore, lowerBound, upperBound);
					myhh.setTimeLimit(timeLimit);
					myhh.loadProblemDomain(problem1);
					myhh.run();
					double bestValueForRLILS = myhh.getBestSolutionValue();
					
					// Used for Question2
//					bestValues[run] = bestValueForRLILS;
//					if(bestValueForRLILS < bestValue) {
//						bestValue = bestValueForRLILS;
//						bestIndex = run;
//					}
//					System.out.println("bestValue: " + bestValueForRLILS);
//					totalValue += bestValueForRLILS;
					// Used for Question3
					AIM_PWP problem2 = new AIM_PWP(seed);
					problem2.loadInstance(instanceID);
					HyperHeuristic hh = new SR_IE_HH(seed);
					hh.setTimeLimit(timeLimit);
					hh.loadProblemDomain(problem2);
					hh.run();
					double bestValueForSR = hh.getBestSolutionValue();
					System.out.println("MyHH: f(s_best) = " + bestValueForRLILS + " SR:   f(s_best) = " + bestValueForSR);
					
					if(bestValueForRLILS < bestValueForSR) {
						rankForMyHH[run] = 0;
						rankForSR[run] = 1;
					}else if(bestValueForRLILS == bestValueForSR) {
						rankForMyHH[run] = 0.5;
						rankForSR[run] = 0.5;
					}else {
						rankForMyHH[run] = 1;
						rankForSR[run] = 0;
					}
				
				}
				// Used for Question2
//				System.out.println(bestIndex);
//				System.out.println("average: " + totalValue / TOTAL_RUNS);
//				System.out.println("RankForMyHH: " + "         RankForSR");
				// Used for Question3
				for(int i = 0; i < TOTAL_RUNS; i++) {
					System.out.print(rankForMyHH[i] + "         " + rankForSR[i]);
					System.out.println();
				}
		}
	}
	
	
	public static void main(String [] args) {
		
		new Compare_Runner(new TestFrameConfirg()).runTests();
	}

}


