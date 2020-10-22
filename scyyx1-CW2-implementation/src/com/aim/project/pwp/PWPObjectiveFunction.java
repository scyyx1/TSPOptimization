package com.aim.project.pwp;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

public class PWPObjectiveFunction implements ObjectiveFunctionInterface {
	
	private final PWPInstanceInterface oInstance;
	
	public PWPObjectiveFunction(PWPInstanceInterface oInstance) {
		
		this.oInstance = oInstance;
	}

	@Override
	public double getObjectiveFunctionValue(SolutionRepresentationInterface representation) {
		int[] array = representation.getSolutionRepresentation();
		double value = 0;
		for(int i = 0; i <= array.length; i++) {
			double currentCost = 0;
			if(i == 0) {
				currentCost = getCostBetweenDepotAnd(array[i]);
			}
			else if(i == array.length) {
				currentCost = getCostBetweenHomeAnd(array[i-1]);
			}else {
				currentCost = getCost(array[i-1], array[i]);
			}
			value += currentCost;
		}
		
		return value;
	}
	
	@Override
	public double getCost(int iLocationA, int iLocationB) {
		Location a = oInstance.getLocationForDelivery(iLocationA);
		Location b = oInstance.getLocationForDelivery(iLocationB);
		double a_x = a.getX();
		double a_y = a.getY();
		double b_x = b.getX();
		double b_y = b.getY();
		return Math.sqrt((a_x - b_x)*(a_x - b_x) + (a_y - b_y)*(a_y - b_y));
	}

	@Override
	public double getCostBetweenDepotAnd(int iLocation) {
		Location a = oInstance.getLocationForDelivery(iLocation);
		Location b = oInstance.getPostalDepot();
		double a_x = a.getX();
		double a_y = a.getY();
		double b_x = b.getX();
		double b_y = b.getY();
		return Math.sqrt((a_x - b_x)*(a_x - b_x) + (a_y - b_y)*(a_y - b_y));
	}

	@Override
	public double getCostBetweenHomeAnd(int iLocation) {
		Location a = oInstance.getLocationForDelivery(iLocation);
		Location b = oInstance.getHomeAddress();
		double a_x = a.getX();
		double a_y = a.getY();
		double b_x = b.getX();
		double b_y = b.getY();
		return Math.sqrt((a_x - b_x)*(a_x - b_x) + (a_y - b_y)*(a_y - b_y));
	}
}
