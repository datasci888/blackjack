package edu.westga.cs1302.casino.model;

/**
 * The enum Rank.
 * 
 * @author CS 1302
 * @version Fall 2021
 */
public enum Rank {

	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(12), QUEEN(13),
	KING(14);

	private int value;

	/**
	 * Creates the given rank with the specified value.
	 * 
	 * @precondition none
	 * @postcondition getValue() == value
	 * @param value the numeric value of this rank
	 */
	Rank(int value) {
		this.value = value;
	}

	/**
	 * Returns the numeric value of this rank.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the numeric value of this rank
	 */
	public int getValue() {
		return this.value;
	}
}
