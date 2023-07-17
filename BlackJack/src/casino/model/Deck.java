package edu.westga.cs1302.casino.model;

import java.util.Random;

import edu.westga.cs1302.casino.resources.ExceptionMessages;

/**
 * This class represents a deck of playing cards.
 * 
 * @author CS1302
 * @version Fall 2021
 */
public class Deck {

	public static final int DECK_SIZE = 52;
	private Card[] cards;
	private int topIndex;

	/**
	 * Constructs a new deck of cards.
	 * 
	 * @precondition none
	 * @postcondition the deck with 52 distinct cards is instantiated
	 */
	public Deck() {
		this.topIndex = 0;
		this.cards = new Card[DECK_SIZE];

		
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				Card card = new Card(rank, suit);
				this.cards[this.topIndex++] = card;
			}
		}
	}

	/**
	 * Returns the top index of this deck.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the top index of this deck
	 */
	public int getTopIndex() {
		return this.topIndex;
	}

	/**
	 * Returns the card at the top of the deck.
	 * 
	 * @precondition getTopIndex() > 0
	 * @postcondition getCard(getTopIndex()) == null && getTopIndex() ==
	 *                getTopIndex()@prev - 1
	 * @return card at top of deck
	 * @throws IAE with message EMPTY_DECK
	 */
	public Card draw() {
		if (this.topIndex == 0) {
			throw new IllegalArgumentException(ExceptionMessages.EMPTY_DECK);
		}
		Card card = this.cards[--this.topIndex];
		this.cards[this.topIndex] = null;
		return card;
	}

	/**
	 * Shuffles the deck of cards using Knuth's algorithm.
	 * 
	 * @precondition none
	 * @postcondition the deck is shuffled
	 */
	public void shuffle() {
		Random random = new Random();
		for (int index = 0; index < this.cards.length; index++) {
			int randomIndex = random.nextInt(index + 1);
			Card card = this.cards[randomIndex];
			this.cards[randomIndex] = this.cards[index];
			this.cards[index] = card;
		}
	}
}
