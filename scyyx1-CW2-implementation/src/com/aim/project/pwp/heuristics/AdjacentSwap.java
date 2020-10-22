package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;
	
	public AdjacentSwap(Random oRandom) {

		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		int length = solution.getNumberOfLocations() - 2;

		int times = 0;
		if(0 <= intensityOfMutation && intensityOfMutation <0.2) {
			times = 1;
		}else if(0.2 <= intensityOfMutation && intensityOfMutation <0.4) {
			times = 2;
		}else if(0.4 <= intensityOfMutation && intensityOfMutation <0.6) {
			times = 4;
		}else if(0.6 <= intensityOfMutation && intensityOfMutation <0.8) {
			times = 8;
		}else if(0.8<= intensityOfMutation && intensityOfMutation <1.0) {
			times = 16;
		}else if(intensityOfMutation == 1) {
			times = 32;
		}
		
		int[] currentRepresentation = solution.getSolutionRepresentation().getSolutionRepresentation();

		for(int count = 0; count < times; count++) {

			int randomIndexToStart = oRandom.nextInt(length);

			int indexToSwap = (randomIndexToStart + 1) % length;

			swapAdjacent(currentRepresentation, randomIndexToStart, indexToSwap);

			double updateValue = getObjectiveValueForAS(currentRepresentation, randomIndexToStart, solution.getObjectiveFunctionValue());

			solution.setObjectiveFunctionValue(updateValue);
		}
		return solution.getObjectiveFunctionValue();
	}

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return false;
	}

}

