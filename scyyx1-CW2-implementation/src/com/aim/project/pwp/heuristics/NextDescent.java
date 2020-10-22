package com.aim.project.pwp.heuristics;


import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


/**
 * 
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public NextDescent(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int length = oSolution.getNumberOfLocations() - 2;
		int times = 0;
		int acceptance = 0;
		boolean fullLoop = false;
		
		if(0 <= dDepthOfSearch && dDepthOfSearch <0.2) {
			times = 1;
		}else if(0.2 <= dDepthOfSearch && dDepthOfSearch <0.4) {
			times = 2;
		}else if(0.4 <= dDepthOfSearch && dDepthOfSearch <0.6) {
			times = 3;
		}else if(0.6 <= dDepthOfSearch && dDepthOfSearch <0.8) {
			times = 4;
		}else if(0.8<= dDepthOfSearch && dDepthOfSearch <1.0) {
			times = 5;
		}else if(dDepthOfSearch == 1) {
			times = 6;
		}
		
		int[] currentRepresentation = oSolution.getSolutionRepresentation().getSolutionRepresentation();

		int startIndex = oRandom.nextInt(length);
		int processIndex = startIndex;
		double curValue = oSolution.getObjectiveFunctionValue();
		while(acceptance < times && !fullLoop) {
			int indexToSwap = (processIndex + 1) % length;

			swapAdjacent(currentRepresentation, processIndex, indexToSwap);

			double updateValue = getObjectiveValueForAS(currentRepresentation, processIndex, curValue);

			// Accepted current solution.
			if(updateValue < curValue) {
				acceptance++;
				oSolution.setObjectiveFunctionValue(updateValue);
				curValue = updateValue;
				processIndex = (processIndex+1)%length;
				startIndex = processIndex;
				continue;
			}else {
				swapAdjacent(currentRepresentation, processIndex, indexToSwap);

			}
			processIndex = (processIndex + 1) % length;

			if(processIndex == startIndex) {
				fullLoop = true;
			}
		}

		return oSolution.getObjectiveFunctionValue();
	}

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return true;
	}
}
