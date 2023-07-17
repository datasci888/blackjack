package edu.westga.cs1302.casino.model;

import edu.westga.cs1302.casino.resources.ExceptionMessages;

/**
 * The Card class.
 * 
 * @author CS 1302
 * @version Fall 2021
 */
public class Card {

	private Rank rank;
	private Suit suit;

	/**
	 * Creates a playing card with the specified rank and suit.
	 * 
	 * @precondition valid rank and suit
	 * @postcondition getValue() == value && getSuit() == suit
	 * @param rank the rank of this card
	 * @param suit the suit of this card
	 */
	public Card(Rank rank, Suit suit) {
		if (rank == null) {
			throw new IllegalArgumentException(ExceptionMessages.INVALID_RANK);
		}
		if (suit == null) {
			throw new IllegalArgumentException(ExceptionMessages.INVALID_SUIT);
		}
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * Returns the rank of this card.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the rank
	 */
	public int getRank() {
		return this.rank.getValue();
	}

	/**
	 * Returns the suit of this card.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the suit
	 */
	public String getSuit() {
		return this.suit.toString();
	}

	/**
	 * Checks if the card is a face card (JACK, QUEEN, KING).
	 * 
	 * @precondition
	 * @postcondition
	 * @return true if face card, false otherwise
	 */
	public boolean isFaceCard() {
		if (this.rank.getValue() > 10) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the card is a Ace card.
	 * 
	 * @precondition
	 * @postcondition
	 * @return true if Ace card, false otherwise
	 */
	public boolean isAceCard() {
		if (this.rank.getValue() == 1) {
			return true;
		}
		return false;
	}
}
