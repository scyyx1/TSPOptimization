package com.aim.project.pwp.solution;

import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

/**
 * 
 * @author Warren G. Jackson
 * 
 *
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {

	private int[] aiSolutionRepresentation;

	public SolutionRepresentation(int[] aiRepresentation) {

		this.aiSolutionRepresentation = aiRepresentation;
	}

	@Override
	public int[] getSolutionRepresentation() {

		return aiSolutionRepresentation;
	}

	@Override
	public void setSolutionRepresentation(int[] aiSolutionRepresentation) {

		this.aiSolutionRepresentation = aiSolutionRepresentation;
	}

	@Override
	public int getNumberOfLocations() {

		return aiSolutionRepresentation.length+2;
		
		// TODO return the total number of locations in this instance (includes DEPOT and HOME).
	}

	@Override
	public SolutionRepresentationInterface clone() {

		int[] arr = aiSolutionRepresentation.clone();
		return new SolutionRepresentation(arr);
		
		// TODO perform a DEEP clone of the solution representation!
	}

}
