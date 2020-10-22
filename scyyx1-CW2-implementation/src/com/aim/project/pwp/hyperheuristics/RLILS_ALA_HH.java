package com.aim.project.pwp.hyperheuristics;




import java.util.List;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.SolutionPrinter;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

public class RLILS_ALA_HH extends HyperHeuristic {
	
	private final int defaultScore, lowerBound, upperBound;
	public RLILS_ALA_HH(long lSeed, int defaultScore, int lowerBound, int upperBound) {
		
		super(lSeed);
		this.defaultScore = defaultScore;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	@Override
	protected void solve(ProblemDomain oProblem) {
		
		oProblem.setMemorySize(2);
		
		oProblem.initialiseSolution(0);
		double current = oProblem.getFunctionValue(0);
		
		oProblem.setIntensityOfMutation(0.2);
		oProblem.setDepthOfSearch(0.2);
		
		int[] mtns = oProblem.getHeuristicsOfType(HeuristicType.MUTATION);
		int[] lss = oProblem.getHeuristicsOfType(HeuristicType.LOCAL_SEARCH);
		HeuristicPair[] hs = new HeuristicPair[(mtns.length * lss.length)];
		
		int i = 0;
		for(int muID = 0; muID < mtns.length; muID++) {
			for(int lsID = 0; lsID < lss.length; lsID++) {
				hs[i] = new HeuristicPair(mtns[muID], lss[lsID]);
				i++;
			}
		}

		RouletteWheelSelection rws = new RouletteWheelSelection(hs, defaultScore, lowerBound, upperBound, rng);
		AverageLateAcceptance ala = new AverageLateAcceptance(3, current);
		long iteration = 0;
		System.out.println("Iteration\tf(s)\tf(s')\tAccept");
		double candidate;
		while(!hasTimeExpired() ) {

			HeuristicPair bestHeuristics = rws.performRouletteWheelSelection();

			oProblem.applyHeuristic(bestHeuristics.getFirst(), 0, 1);
			candidate = oProblem.applyHeuristic(bestHeuristics.getLast(), 1, 1);

			 double tau = ala.getThresholdValue();
	            double max = 0;
	            if(tau < current){
	            	max = current;
	            }else {
	            	max = tau;
	            }
	            if(candidate < max) {

	            	rws.incrementScore(bestHeuristics);
	            	ala.update(candidate);
	            	oProblem.copySolution(1, 0);
	            	current = candidate;
	            }else {

	            	rws.decrementScore(bestHeuristics);
	            	ala.update(current);
	            }

			iteration++;





		
		}
		
		PWPSolutionInterface oSolution = ((AIM_PWP) oProblem).getBestSolution();
		SolutionPrinter oSP = new SolutionPrinter("out.csv");
		oSP.printSolution( ((AIM_PWP) oProblem).oInstance.getSolutionAsListOfLocations(oSolution));
		System.out.println(String.format("Total iterations = %d", iteration));
//		List<com.aim.project.pwp.instance.Location> alllocation = ((AIM_PWP) oProblem).oInstance.getSolutionAsListOfLocations(oSolution);
//	  
//		int length = oSolution.getSolutionRepresentation().getSolutionRepresentation().length;
//		for(int j = 0; j < length; j++) {
//			System.out.print(oSolution.getSolutionRepresentation().getSolutionRepresentation()[j]+ " ");
//		}
//		System.out.println();
//		System.out.print("(" + alllocation.get(0).getX() + "," + alllocation.get(0).getY()+")->");
//		for(int j=0; j<length;j++){
//			int index = oSolution.getSolutionRepresentation().getSolutionRepresentation()[j] + 1;
//			System.out.print("(" + alllocation.get(index).getX() + "," + alllocation.get(index).getY() + ")->");
//		}
//		System.out.println("("+alllocation.get(length+1).getX() +","+alllocation.get(length+1).getY()+")");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "RLILS_ALA_HH";
	}

}
