package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;

public class HeuristicOperators {

	private ObjectiveFunctionInterface oObjectiveFunction;

	public HeuristicOperators() {

	}

	
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {

		this.oObjectiveFunction = f;
	}

	/**
	 * TODO implement any common functionality here so that your
	 * 			heuristics can reuse them!
	 * E.g.  you may want to implement the swapping of two delivery locations here!
	 */
	
	//swap index1 with index2 in array
	public void swapAdjacent(int[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;	
	}
	
	// Get objective value for an array by delta evaluation in Adjacent Swap.
	public double getObjectiveValueForAS(int[] arr, int index, double originValue) {
		if(index == 0) {
			double oldCost1 = oObjectiveFunction.getCostBetweenDepotAnd(arr[index + 1]);
			double newCost1 = oObjectiveFunction.getCostBetweenDepotAnd(arr[index]);
			double oldCost2 = oObjectiveFunction.getCost(arr[index], arr[index + 2]);
			double newCost2 = oObjectiveFunction.getCost(arr[index + 1], arr[index + 2]);
			double value = originValue + newCost1 + newCost2 - oldCost1 - oldCost2 ;
			return value;
		} else if(index == arr.length - 1) {
			double newCost1 = oObjectiveFunction.getCostBetweenHomeAnd(arr[index]);
			double newCost2 = oObjectiveFunction.getCostBetweenDepotAnd(arr[0]);
			double newCost3 = oObjectiveFunction.getCost(arr[index - 1], arr[index]);
			double newCost4 = oObjectiveFunction.getCost(arr[0], arr[1]);
			double oldCost1 = oObjectiveFunction.getCostBetweenHomeAnd(arr[0]);
			double oldCost2 = oObjectiveFunction.getCostBetweenDepotAnd(arr[index]);
			double oldCost3 = oObjectiveFunction.getCost(arr[index], arr[1]);
			double oldCost4 = oObjectiveFunction.getCost(arr[0], arr[index - 1]);
			double value = originValue + newCost1 + newCost2 + newCost3 + newCost4 - oldCost1 - oldCost2 - oldCost3 - oldCost4 ;
			return value;
		}else if(index == arr.length - 2){
			double newCost1 = oObjectiveFunction.getCostBetweenHomeAnd(arr[index+1]);
			double newCost2 = oObjectiveFunction.getCost(arr[index], arr[index - 1]);
			double oldCost1 = oObjectiveFunction.getCostBetweenHomeAnd(arr[index]);
			double oldCost2 = oObjectiveFunction.getCost(arr[index - 1], arr[index + 1]);
			double value = originValue + newCost1 + newCost2- oldCost1 - oldCost2 ;
			return value;
			
		}else {
			double newCost1 = oObjectiveFunction.getCost(arr[index], arr[index - 1 ]);
			double newCost2 = oObjectiveFunction.getCost(arr[index + 1], arr[index + 2]);
			double oldCost1 = oObjectiveFunction.getCost(arr[index + 1], arr[index - 1]);
			double oldCost2 = oObjectiveFunction.getCost(arr[index], arr[index + 2]);
			double value = originValue + newCost1 + newCost2 - oldCost1 - oldCost2;
			return value;
		
		}
		
	}

	// Get objective value for an array by delta evaluation in Inversion Mutation.
	public double getObjectiveValueForIM(int[] arr, int indexStart, int indexEnd, double originValue) {
		
		double newCost1 = 0;
		double newCost2 = 0;
		double oldCost1 = 0;
		double oldCost2 = 0;

		if(indexStart == 0) {
			newCost1 = oObjectiveFunction.getCostBetweenDepotAnd(arr[indexStart]);
			oldCost1 = oObjectiveFunction.getCostBetweenDepotAnd(arr[indexEnd]);
		}else {
			newCost1 = oObjectiveFunction.getCost(arr[indexStart], arr[indexStart - 1]);
			oldCost1 = oObjectiveFunction.getCost(arr[indexEnd], arr[indexStart - 1]);
		}
		if( indexEnd == arr.length - 1) {
			newCost2 = oObjectiveFunction.getCostBetweenHomeAnd(arr[indexEnd]);
			oldCost2 = oObjectiveFunction.getCostBetweenHomeAnd(arr[indexStart]);
		}else {
			newCost2 = oObjectiveFunction.getCost(arr[indexEnd], arr[indexEnd + 1]);
			oldCost2 = oObjectiveFunction.getCost(arr[indexStart], arr[indexEnd + 1]);
		}


		return originValue + newCost1 + newCost2 - oldCost1 - oldCost2;

		
	}
	
	// Get objective value for an array by delta evaluation in Re-insertion.
	public double getObjectiveValueForRI(int[] arr, int originIndex, int destIndex, double originValue) {
		double newCost1 = 0;
		double newCost2 = 0;
		double newCost3 = 0;
		double oldCost1 = 0;
		double oldCost2 = 0;
		double oldCost3 = 0;
		if(originIndex < destIndex) {
			if(originIndex == 0) {
				newCost1 = oObjectiveFunction.getCostBetweenDepotAnd(arr[originIndex]);
				oldCost1 = oObjectiveFunction.getCostBetweenDepotAnd(arr[destIndex]);
			}else {
				newCost1 = oObjectiveFunction.getCost(arr[originIndex], arr[originIndex - 1]);
				oldCost1 = oObjectiveFunction.getCost(arr[originIndex - 1], arr[destIndex]);
			}
			if(destIndex == arr.length - 1) {
				newCost2 = oObjectiveFunction.getCostBetweenHomeAnd(arr[destIndex]);
				oldCost2 = oObjectiveFunction.getCostBetweenHomeAnd(arr[destIndex - 1]);
			}else {
				newCost2 = oObjectiveFunction.getCost(arr[destIndex], arr[destIndex + 1]);
				oldCost2 = oObjectiveFunction.getCost(arr[destIndex - 1], arr[destIndex + 1]);
			}
			if(originIndex + 1 != destIndex) {
				newCost3 = oObjectiveFunction.getCost(arr[destIndex - 1], arr[destIndex]);
				oldCost3 = oObjectiveFunction.getCost(arr[originIndex], arr[destIndex]);
			}	
		}else {
			if(destIndex == 0) {
				newCost1 = oObjectiveFunction.getCostBetweenDepotAnd(arr[destIndex]);
				oldCost1 = oObjectiveFunction.getCostBetweenDepotAnd(arr[destIndex + 1]);
			}else {
				newCost1 = oObjectiveFunction.getCost(arr[destIndex - 1], arr[destIndex]);
				oldCost1 = oObjectiveFunction.getCost(arr[destIndex + 1], arr[destIndex - 1]);
			}
			if(originIndex == arr.length - 1) {
				newCost2 = oObjectiveFunction.getCostBetweenHomeAnd(arr[originIndex]);
				oldCost2 = oObjectiveFunction.getCostBetweenHomeAnd(arr[destIndex]);
				
			}else {
				newCost2 = oObjectiveFunction.getCost(arr[originIndex], arr[originIndex + 1]);
				oldCost2 = oObjectiveFunction.getCost(arr[destIndex], arr[originIndex + 1]);
			}
			if(originIndex + 1 != destIndex) {
				newCost3 = oObjectiveFunction.getCost(arr[destIndex], arr[destIndex + 1]);
				oldCost3 = oObjectiveFunction.getCost(arr[destIndex], arr[originIndex]);
			}
		}
		return originValue + newCost1 + newCost2 + newCost3 - oldCost1 - oldCost2 - oldCost3;
		
	}
	
}
