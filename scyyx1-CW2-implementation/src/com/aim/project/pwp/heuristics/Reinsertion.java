package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;
	
	public Reinsertion(Random oRandom) {

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
			times = 3;
		}else if(0.6 <= intensityOfMutation && intensityOfMutation <0.8) {
			times = 4;
		}else if(0.8<= intensityOfMutation && intensityOfMutation <1.0) {
			times = 5;
		}else if(intensityOfMutation == 1) {
			times = 6;
		}
		
		int[] currentRepresentation = solution.getSolutionRepresentation().getSolutionRepresentation();

		for(int count = 0; count < times; count++) {

			int removeIndex = oRandom.nextInt(length);
			int destinationIndex = oRandom.nextInt(length);
			while(destinationIndex == removeIndex) {
				destinationIndex = oRandom.nextInt(length);
			}

			if(removeIndex > destinationIndex) {

				int temp = currentRepresentation[removeIndex];
				for(int i = removeIndex; i > destinationIndex; i--) {
					currentRepresentation[i] = currentRepresentation[i - 1];

				}
				currentRepresentation[destinationIndex] = temp;
			}else {
				int temp = currentRepresentation[removeIndex];
				for(int i = removeIndex; i < destinationIndex; i++) {
					currentRepresentation[i] = currentRepresentation[i + 1];
				}
				currentRepresentation[destinationIndex] = temp;
			}
			solution.setObjectiveFunctionValue(getObjectiveValueForRI(currentRepresentation, removeIndex, destinationIndex, solution.getObjectiveFunctionValue()));
			
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
