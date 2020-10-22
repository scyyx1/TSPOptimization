package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;


public class CX implements XOHeuristicInterface {
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction;

	public CX(Random oRandom) {
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
		return solution.getObjectiveFunctionValue();
	}

	@Override
	public double apply(PWPSolutionInterface p1, PWPSolutionInterface p2,
			PWPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {
		
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
		
		int[] parent1 = p1.getSolutionRepresentation().getSolutionRepresentation().clone();
		int[] parent2 = p2.getSolutionRepresentation().getSolutionRepresentation().clone();
		int len = parent1.length;
		int[] selectedChild = new int[len];
		
		for(int count = 0; count < times; count++) {

			int[] child1 = new int[len];
			int[] child2 = new int[len];
			
			// Pick up start location randomly
			int startIndex = oRandom.nextInt(len);
			int cycleStart = parent1[startIndex];
			int startLocation = parent1[startIndex];
			int nextLocation = parent2[startIndex];
			boolean fullcycle = false;

			// Remaining the one in circle
			while(!fullcycle) {
				if(nextLocation == cycleStart) {
					fullcycle = true;
					child1[startIndex] = startLocation;
					child2[startIndex] = nextLocation;
					break;
				}
				child1[startIndex] = startLocation;
				child2[startIndex] = nextLocation;
				for(int i = 0; i < len; i++) {
					if(parent1[i] == nextLocation) {
						startIndex = i;
						startLocation = parent1[startIndex];
						nextLocation = parent2[startIndex];
						break;
					}
					
				}

			}

			// Fill out blank index
			for(int j = 0; j < len; j++) {
				if(child1[j] == parent1[j] && child2[j] == parent2[j]) {
					continue;
				}else {
					child1[j] = parent2[j];
					child2[j] = parent1[j];
				}
			}
			parent1 = child1;
			parent2 = child2;

		}
		int randomChild = oRandom.nextInt(2);
		if(randomChild == 0) {
			selectedChild = parent1;
		}else {
			selectedChild = parent2;
		}
		c.getSolutionRepresentation().setSolutionRepresentation(selectedChild);
		double childValue = oObjectiveFunction.getObjectiveFunctionValue(c.getSolutionRepresentation());
		c.setObjectiveFunctionValue(childValue);

		return childValue;
	}

	@Override
	public boolean isCrossover() {
		return true;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return false;
	}


	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {
		
		this.oObjectiveFunction = oObjectiveFunction;
	}
}
