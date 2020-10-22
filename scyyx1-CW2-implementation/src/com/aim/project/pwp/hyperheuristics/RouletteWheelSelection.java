package com.aim.project.pwp.hyperheuristics;

import java.util.LinkedHashMap;
import java.util.Random;
import java.util.stream.Collectors;

public class RouletteWheelSelection {

	public HeuristicPair[] aoHeuristicPairs;

	public LinkedHashMap<HeuristicPair, Integer> oHeuristicScores;

	public final int iUpperBound;

	public final int iLowerBound;

	public final int iDefaultScore;

	public final Random rng;

	/**
	 * Constructs a Roulette Wheel Selection method using a LinkedHashMap.
	 * 
	 * @param heuristic_ids The set of heuristic IDs.
	 * @param default_score The default score to give each heuristic.
	 * @param lower_bound   The lower bound on the heuristic scores.
	 * @param upper_bound   The upper bound on the heuristic scores.
	 * @param rng           The random number generator.
	 */
	public RouletteWheelSelection(HeuristicPair[] hs, int default_score, int lower_bound, int upper_bound, Random rng) {

		this(new LinkedHashMap<HeuristicPair, Integer>(), hs, default_score, lower_bound, upper_bound, rng);
	}

	/**
	 * Constructs a Roulette Wheel Selection method using the supplied Map.
	 * 
	 * @param heuristic_scores An empty Map for mapping heuristic IDs to heuristic
	 *                         scores.
	 * @param hs               The set of heuristic IDs for the (mutation,
	 *                         local_search) operators.
	 * @param default_score    The default score to give each heuristic.
	 * @param lower_bound      The lower bound on the heuristic scores.
	 * @param upper_bound      The upper bound on the heuristic scores.
	 * @param rng              The random number generator.
	 */
	public RouletteWheelSelection(LinkedHashMap<HeuristicPair, Integer> heuristic_scores, HeuristicPair[] hs,
			int default_score, int lower_bound, int upper_bound, Random rng) {

		this.aoHeuristicPairs = hs;
		this.oHeuristicScores = heuristic_scores;
		this.iUpperBound = upper_bound;
		this.iLowerBound = lower_bound;
		this.iDefaultScore = default_score;
		this.rng = rng;

		// initialise scores to the default_score
		for (HeuristicPair h : hs) {
			
			this.oHeuristicScores.put(h, default_score);
		}

	}

	/**
	 * 
	 * @param h The HeuristicPair to get the score of.
	 * @return The score for the aforementioned heuristic. If the HeuristicScore
	 *         does not exist, then you must return 0.
	 */
	public int getScore(HeuristicPair h) {
		if(oHeuristicScores.isEmpty()) {
			return 0;
		}else {
			return oHeuristicScores.get(h);
		}
		
	}

	/**
	 * Increments the score (by 1) of the specified heuristic whilst respecting the
	 * upper and lower bounds.
	 * 
	 * @param h The HeuristicPair whose score should be incremented.
	 */
	public void incrementScore(HeuristicPair h) {

		int score = getScore(h);
		score++;
		if(score >= iUpperBound) {
			oHeuristicScores.put(h, iUpperBound);
		}else {
			oHeuristicScores.put(h, score);
		}
		// TODO...
	}

	/**
	 * Decrements the score (by 1) of the specified heuristic respecting the upper
	 * and lower bounds.
	 * 
	 * @param h The HeuristicPair whose score should be decremented.
	 */
	public void decrementScore(HeuristicPair h) {

		int score = getScore(h);
		score--;
		if(score <= iLowerBound) {
			oHeuristicScores.put(h, iLowerBound);
		}else {
			oHeuristicScores.put(h, score);
		}

	}

	/**
	 * 
	 * @return The sum of scores of all heuristics.
	 */
	public int getTotalScore() {

		int total = 0;
		for(int i = 0; i< oHeuristicScores.size(); i++) {
			total += oHeuristicScores.get(aoHeuristicPairs[i]);
		}
		
		return total;

	}

	/**
	 * 
	 * 
	 * @return A heuristic based on the RWS method.
	 */
	public HeuristicPair performRouletteWheelSelection() {

		int totalScore = getTotalScore();
		int randomValue = rng.nextInt(totalScore);
		int cumulativeScore = 0;
		HeuristicPair chosenPair = null;
		for(HeuristicPair h: aoHeuristicPairs) {
			chosenPair = h;
			cumulativeScore += getScore(h);
			if(cumulativeScore > randomValue) {
				break;
			}
		}


		return chosenPair;
		
	}

	/****************************************
	 * Utility methods useful for debugging
	 ****************************************/

	/**
	 * Prints the heuristic IDs into the console.
	 */
	public void printHeuristicIds() {

		String ids = "["
				+ oHeuristicScores.entrySet().stream().map(e -> e.getKey().toString()).collect(Collectors.joining(", "))
				+ "]";
		System.out.println("IDs    = " + ids);
	}

	/**
	 * Prints the heuristic scores into the console.
	 */
	public void printHeuristicScores() {

		String scores = "[" + oHeuristicScores.entrySet().stream().map(e -> e.getValue().toString())
				.collect(Collectors.joining(", ")) + "]";
		System.out.println("Scores = " + scores);
	}
}
