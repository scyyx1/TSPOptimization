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
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public DavissHillClimbing(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {

		int length = oSolution.getNumberOfLocations() - 2;
		
		int times = 0;
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
		double curValue = oSolution.getObjectiveFunctionValue();
		for(int count = 0;  count < times; count++) {
			
			// Generate a perturbation
			int[] randomList = new int[length];
			for(int i = 0; i < length; i++) {
				randomList[i] = i;
			}
			for(int i = 0; i< length; i++) {
				int randomIndexToSwap = oRandom.nextInt(length);
				int temp = randomList[randomIndexToSwap];
				randomList[randomIndexToSwap] = randomList[i];
				randomList[i] = temp;
			}

			// Start Swapping
			for(int i = 0; i < length; i++) {
				int startIndex = randomList[i];
				int indexToSwap = (startIndex + 1) % length;

				swapAdjacent(currentRepresentation, startIndex, indexToSwap);

				double updateValue = getObjectiveValueForAS(currentRepresentation, randomList[i], curValue);
				
				// Accept any improving or equal quality solution
				if(updateValue <= curValue) {
					oSolution.setObjectiveFunctionValue(updateValue);
					curValue = updateValue;
				}else {
					// Swap Back
					swapAdjacent(currentRepresentation, startIndex, indexToSwap);

				}
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
