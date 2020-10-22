package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;


public class OX implements XOHeuristicInterface {
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction;

	public OX(Random oRandom) {
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		return oSolution.getObjectiveFunctionValue();
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
			
			// Initialise cutting start and cutting end
			int randomCutStart = oRandom.nextInt(len);
			int randomCutEnd = oRandom.nextInt(len);
			int cutInterval = Math.abs(randomCutStart - randomCutEnd);

			while(cutInterval == 7|| cutInterval == 0 ) {
				randomCutEnd = oRandom.nextInt(len);
				cutInterval = Math.abs(randomCutStart - randomCutEnd);
			}


			if(randomCutEnd < randomCutStart) {
				int temp = randomCutStart;
				randomCutStart = randomCutEnd;
				randomCutEnd = temp;
			}

			
			// Store the element between cutting start and cutting end in parent1 and parent2
			for(int i = randomCutStart; i <= randomCutEnd; i++) {
				child1[i] = parent1[i];
				child2[i] = parent2[i];
			}

			// Resume from second cut
			int childIndex1 = (randomCutEnd + 1) % len;
			int childIndex2 = (randomCutEnd + 1) % len;
			int num = 0;
			int i = (randomCutEnd + 1) % len;
			
			// Start inserting value from p1 and p2 to c1 and c2
			while(num < len) {
				boolean existInChild1 = false;
				boolean existInChild2 = false;
				
				for(int j = randomCutStart; j <= randomCutEnd; j++) {
					// Check whether the value in p1 already inside p2
					if(parent1[i] == parent2[j]) {
						existInChild2 = true;
					}
					// Check whether the value in p2 already inside p1
					if(parent2[i] == parent1[j]) {
						existInChild1 = true;
					}
				}
				if(!existInChild2) {
					child2[childIndex1] = parent1[i];
					childIndex1 = (childIndex1 + 1) % parent1.length;
				}
				if(!existInChild1) {
					child1[childIndex2] = parent2[i];
					childIndex2 = (childIndex2 + 1) % parent1.length;
				}
				i = (i + 1) % parent1.length;
				num++;

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
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		
		this.oObjectiveFunction = f;
	}
}
