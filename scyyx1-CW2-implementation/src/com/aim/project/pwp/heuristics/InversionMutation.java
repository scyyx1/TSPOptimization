package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class InversionMutation extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public InversionMutation(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {

		int totalLocation = oSolution.getNumberOfLocations() - 2;
		int times = 0;
		if(0 <= dIntensityOfMutation && dIntensityOfMutation <0.2) {
			times = 1;
		}else if(0.2 <= dIntensityOfMutation && dIntensityOfMutation <0.4) {
			times = 2;
		}else if(0.4 <= dIntensityOfMutation && dIntensityOfMutation <0.6) {
			times = 3;
		}else if(0.6 <= dIntensityOfMutation && dIntensityOfMutation <0.8) {
			times = 4;
		}else if(0.8<= dIntensityOfMutation && dIntensityOfMutation <1.0) {
			times = 5;
		}else if(dIntensityOfMutation == 1) {
			times = 6;
		}

		int[] currentRepresentation = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		
		for(int count = 0; count < times; count++) {

			int randomStart = oRandom.nextInt(totalLocation);
			int randomEnd = oRandom.nextInt(totalLocation);

			while(randomEnd == randomStart) {
				randomEnd = oRandom.nextInt(totalLocation);
			}

			if(randomEnd < randomStart) {
				int temp = randomStart;
				randomStart = randomEnd;
				randomEnd = temp;
			}

			int initialStart = randomStart;
			int initialEnd = randomEnd;

			while(randomStart < randomEnd) {
				int temp = currentRepresentation[randomStart];
				currentRepresentation[randomStart] = currentRepresentation[randomEnd];
				currentRepresentation[randomEnd] = temp;
				randomStart++;
				randomEnd--;
			}

			oSolution.setObjectiveFunctionValue(getObjectiveValueForIM(currentRepresentation, initialStart, initialEnd, oSolution.getObjectiveFunctionValue()));
		}
		return oSolution.getObjectiveFunctionValue();
		
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
