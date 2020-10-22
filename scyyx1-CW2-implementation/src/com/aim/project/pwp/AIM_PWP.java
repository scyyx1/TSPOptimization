package com.aim.project.pwp;


import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

import com.aim.project.pwp.heuristics.AdjacentSwap;
import com.aim.project.pwp.heuristics.CX;
import com.aim.project.pwp.heuristics.DavissHillClimbing;
import com.aim.project.pwp.heuristics.InversionMutation;
import com.aim.project.pwp.heuristics.NextDescent;
import com.aim.project.pwp.heuristics.OX;
import com.aim.project.pwp.heuristics.Reinsertion;
import com.aim.project.pwp.instance.InitialisationMode;
import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.reader.PWPInstanceReader;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.Visualisable;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

import AbstractClasses.ProblemDomain;

public class AIM_PWP extends ProblemDomain implements Visualisable {

	private String[] instanceFiles = {
		"square", "libraries-15", "carparks-40", "tramstops-85", "trafficsignals-446", "streetlights-35714"
	};
	
	private PWPSolutionInterface[] aoMemoryOfSolutions;
	
	public PWPSolutionInterface oBestSolution;
	
	public PWPInstanceInterface oInstance;
	
	private HeuristicInterface[] aoHeuristics;
	
	private ObjectiveFunctionInterface oObjectiveFunction;
	
	private final long seed;
		
	public AIM_PWP(long seed) {
		
		super(seed);

		this.seed = seed;
		setMemorySize(2);
		HeuristicInterface[] heuristics = new HeuristicInterface[7];
		heuristics[0] = new InversionMutation(rng);
		heuristics[1] = new AdjacentSwap(rng);
		heuristics[2] = new Reinsertion(rng);
		heuristics[3] = new NextDescent(rng);
		heuristics[4] = new DavissHillClimbing(rng);
		heuristics[5] = new OX(rng);
		heuristics[6] = new CX(rng);
		aoHeuristics = heuristics;
		// TODO - set default memory size and create the array of low-level heuristics
		
	}
	
	public PWPSolutionInterface getSolution(int index) {
		return aoMemoryOfSolutions[index];
		// TODO 
	}
	
	public PWPSolutionInterface getBestSolution() {
		return oBestSolution;
		// TODO 
	}

	@Override
	public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {

		if(hIndex >= 0 && hIndex < 7) {
			copySolution(currentIndex, candidateIndex);
			double tempValue = aoHeuristics[hIndex].apply(getSolution(candidateIndex), this.depthOfSearch, this.intensityOfMutation);
			updateBestSolution(candidateIndex);
			return tempValue;
		}
		return getSolution(currentIndex).getObjectiveFunctionValue();
		
		// TODO - apply heuristic and return the objective value of the candidate solution
		//			remembering to keep track/update the best solution
	}

	@Override
	public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {

		if(hIndex == 5 || hIndex == 6) {
			XOHeuristicInterface currentHeuristic = (XOHeuristicInterface)aoHeuristics[hIndex];
			if(getSolution(candidateIndex) == null) {
				initialiseSolution(candidateIndex);
			}
			double tempValue = currentHeuristic.apply(getSolution(parent1Index), getSolution(parent2Index), getSolution(candidateIndex), this.depthOfSearch, this.intensityOfMutation);
			updateBestSolution(candidateIndex);

			return tempValue;
		}
		return getSolution(parent1Index).getObjectiveFunctionValue();
		
		// TODO - apply heuristic and return the objective value of the candidate solution
		//			remembering to keep track/update the best solution
	}

	@Override
	public String bestSolutionToString() {
		
		if(getBestSolution() == null) {
			return "empty solution";
		}
		int[] arr = getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
		String route = "DEPOT";
		for(int i = 0; i < arr.length; i++) {
			
			String location = " -> " + arr[i];
			route += location;
		}
		String home = " -> HOME";
		route += home;
		return route;
		// TODO return the location IDs of the best solution including DEPOT and HOME locations
		//		e.g. "DEPOT -> 0 -> 2 -> 1 -> HOME"
	}

	@Override
	public boolean compareSolutions(int iIndexA, int iIndexB) {

		return getFunctionValue(iIndexA) == getFunctionValue(iIndexB);
		// TODO return true if the objective values of the two solutions are the same, else false
	}

	@Override
	public void copySolution(int iIndexA, int iIndexB) { 
		if(getSolution(iIndexA) == null) {
			System.out.println("iIndex " + iIndexA + " is empty!");
		}else {
			aoMemoryOfSolutions[iIndexB] = getSolution(iIndexA).clone();
		}

		// TODO - BEWARE this should copy the solution, not the reference to it!
		//			That is, that if we apply a heuristic to the solution in index 'b',
		//			then it does not modify the solution in index 'a' or vice-versa.
		
		
	}

	@Override
	public double getBestSolutionValue() {

		if(oBestSolution == null) {
			return 0.0;
		}
		return oBestSolution.getObjectiveFunctionValue();
		// TODO
	}
	
	@Override
	public double getFunctionValue(int index) {
		if(getSolution(index) == null) {
			return 0.0;
		}
		return getSolution(index).getObjectiveFunctionValue();
		// TODO
	}

	@Override
	public int[] getHeuristicsOfType(HeuristicType type) {
		if(type == HeuristicType.MUTATION) {
			return new int[] {0, 1, 2};
		}
		if(type == HeuristicType.LOCAL_SEARCH) {
			return new int[] {3, 4};
		}
		if(type == HeuristicType.CROSSOVER) {
			return new int[] {5, 6};
		}
		return new int[] {} ;
		// TODO return an array of heuristic IDs based on the heuristic's type.
		
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {
		
		return new int[] {3, 4};
		// TODO return the array of heuristic IDs that use depth of search.
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {
		
		return new int[] {0, 1, 2, 5, 6};
		// TODO return the array of heuristic IDs that use intensity of mutation.
	}

	@Override
	public int getNumberOfHeuristics() {

		// TODO - has to be hard-coded due to the design of the HyFlex framework...
		return 7;
	}

	@Override
	public int getNumberOfInstances() {

		return instanceFiles.length;
		// TODO return the number of available instances
	}

	@Override
	public void initialiseSolution(int index) {
		
		aoMemoryOfSolutions[index] = oInstance.createSolution(InitialisationMode.RANDOM);
		updateBestSolution(index);

		// TODO - initialise a solution in index 'index' 
		// 		making sure that you also update the best solution!
		
	}

	// TODO implement the instance reader that this method uses
	//		to correctly read in the PWP instance, and set up the objective function.
	@Override
	public void loadInstance(int instanceId) {

		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "pwp" + SEP + instanceFiles[instanceId] + ".pwp";

		Path path = Paths.get(instanceName);
		Random random = new Random(seed);
		PWPInstanceReader oPwpReader = new PWPInstanceReader();
		oInstance = oPwpReader.readPWPInstance(path, random);

		oObjectiveFunction = oInstance.getPWPObjectiveFunction();
		
		for(HeuristicInterface h : aoHeuristics) {
			h.setObjectiveFunction(oObjectiveFunction);
		}
	}

	@Override
	public void setMemorySize(int size) {

		if(aoMemoryOfSolutions == null) {
			
			aoMemoryOfSolutions = new PWPSolutionInterface[size];
			return;
		}
		if(size <= 1) {
			return;
		}else if(size > aoMemoryOfSolutions.length) {
			PWPSolutionInterface[] newMemory = new PWPSolutionInterface[size];
			for(int i = 0; i < aoMemoryOfSolutions.length; i++) {
				newMemory[i] = aoMemoryOfSolutions[i];
			}
			aoMemoryOfSolutions = newMemory;
		}else if(size == aoMemoryOfSolutions.length) {
			return;
		}else {
			PWPSolutionInterface[] newMemory = new PWPSolutionInterface[size];
			for(int i = 0; i < size; i++) {
				
				newMemory[i] = aoMemoryOfSolutions[i];
			}
			aoMemoryOfSolutions = newMemory;
		}
		// TODO sets a new memory size
		// IF the memory size is INCREASED, then
		//		the existing solutions should be copied to the new memory at the same indices.
		// IF the memory size is DECREASED, then
		//		the first 'size' solutions are copied to the new memory.
	}

	@Override
	public String solutionToString(int index) {

		if(getSolution(index) == null) {
			return "empty solution";
		}
		int[] arr = getSolution(index).getSolutionRepresentation().getSolutionRepresentation();
		String route = "DEPOT";
		for(int i = 0; i < arr.length; i++) {
			
			String location = " -> " + arr[i];
			route += location;
		}
		String home = " -> HOME";
		route += home;
		return route;
		// TODO

	}

	@Override
	public String toString() {

		// TODO change 'AAA' to be your username
		return "scyyx1's G52AIM PWP";
	}
	
	private void updateBestSolution(int index) {

		if(oBestSolution == null || aoMemoryOfSolutions[index].getObjectiveFunctionValue() < oBestSolution.getObjectiveFunctionValue()) {
			oBestSolution = aoMemoryOfSolutions[index].clone();
		}
		
		

		// TODO
		
	}
	
	@Override
	public PWPInstanceInterface getLoadedInstance() {

		return this.oInstance;
	}

	@Override
	public Location[] getRouteOrderedByLocations() {

		int[] city_ids = getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
		Location[] route = Arrays.stream(city_ids).boxed().map(getLoadedInstance()::getLocationForDelivery).toArray(Location[]::new);
		return route;
	}
}
