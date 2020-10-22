package com.aim.project.pwp.runners;

import com.aim.project.pwp.hyperheuristics.RLILS_ALA_HH;


import AbstractClasses.HyperHeuristic;

public class RLILS_ALA_VisualRunner extends HH_Runner_Visual {

	@Override
	protected HyperHeuristic getHyperHeuristic(long seed) {

		return new RLILS_ALA_HH(seed, 5, 1, 10);
	}
	
	public static void main(String [] args) {
		
		HH_Runner_Visual runner = new RLILS_ALA_VisualRunner();
		runner.run();
	}
}
