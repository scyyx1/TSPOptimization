package com.aim.project.pwp.hyperheuristics;


import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

/**
 * @author Warren G. Jackson
 *
 * Creates an initial solution and finishes.
 * Can be used for testing your initialisation method.
 */
public class InitialisationTest_HH extends HyperHeuristic {
	
	public InitialisationTest_HH(long seed) {
		
		super(seed);
	}

	@Override
	protected void solve(ProblemDomain problem) {

		problem.initialiseSolution(0);
		hasTimeExpired();
	}

	@Override
	public String toString() {

		return "InitialisationTest_HH";
	}
}
