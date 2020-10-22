package com.aim.project.pwp.hyperheuristics;


public class AverageLateAcceptance {

	public final int iListLength;



	/**
	 * getFunctionValue() returns a "double" so it is okay to use double in this
	 * case.
	 */
	public double[] adAcceptedSolutionFitnesses;
	

	/**
	 * Keeps track of the current index; i.e. the index of the array where the
	 * 'iListLength' previously accepted solution's objective value is stored.
	 */
	public int iIndex;


	/**
	 * 
	 * @param iListLength             Length of late accepting list.
	 * @param dInitialSolutionFitness Objective value of the initial solution.
	 */
	public AverageLateAcceptance(int iListLength, double dInitialSolutionFitness) {

		this.iListLength = iListLength;
		this.iIndex = 0;
		
		adAcceptedSolutionFitnesses = new double[iListLength];
		adAcceptedSolutionFitnesses[0] = dInitialSolutionFitness;

	}

	public double getThresholdValue() {

		double sum = 0;
		int count = 0;
		for(double value:adAcceptedSolutionFitnesses ) {
			if(value != 0) {
				count++;
			}
			sum += value;
		}
		return sum / count;
	}

	/**
	 * Replaces the 'iListLength' previously accepted solution fitness with
	 * <code>dObjectiveValue</code> and advances the <code>index</code> counter;
	 * 
	 * @param dObjectiveValue
	 */
	public void update(double dObjectiveValue) {

		adAcceptedSolutionFitnesses[iIndex] = dObjectiveValue;
		
		iIndex = (iIndex + 1) % iListLength;
	}

}
