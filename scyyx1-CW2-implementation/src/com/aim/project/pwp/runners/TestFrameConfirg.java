package com.aim.project.pwp.runners;

public class TestFrameConfirg{
	
	/*
	 * permitted run time(s) = { 60s }
	 */
	protected final long RUN_TIME_IN_SECONDS = 5;//60;
	
	protected final long MILLISECONDS_IN_ONE_MINUTES = 6000;
	
	/*
	 * permitted problem domain(s) = { SAT, VRP, BP }
	 */
	protected final String[] PROBLEM_DOMAINS = { "Carparks-40", "Tramstops-85", "Trafficsignals-446" };
	
	/*
	 * permitted instance ID's:
	 * 
	 * 		SAT = { 3, 11 }
	 *      TSP = { 0, 6 } 
	 * 		BP  = { 7, 11 }
	 */
	protected final int[] INSTANCE_IDs = { 2, 3, 4 };
	
	/*
	 * permitted default score = { 5 }.
	 */
	protected final int DEFAULT_SCORE = 5;
	
	/*
	 * permitted lower bounds = { 0, 1 }.
	 */
	protected final int LOWER_BOUND = 1;
	
	/*
	 * permitted upper bounds = { 10 }.
	 */
	protected final int UPPER_BOUND = 10;
	

	public String[] getDomains() {
		
		return this.PROBLEM_DOMAINS;
	}


	public int[] getInstanceIDs() {
		
		return this.INSTANCE_IDs;
	}


	public long getRunTime() {
		
		return (MILLISECONDS_IN_ONE_MINUTES * RUN_TIME_IN_SECONDS) / 600;
	}
	


}
