package edu.westga.cs1302.casino.model;

import java.util.ArrayList;

import edu.westga.cs1302.casino.resources.ExceptionMessages;

/**
 * The class Player.
 * 
 * @author CS1302
 * @version Fall 2021
 */
public class Player {

	private ArrayList<Card> hand;

	/**
	 * Instantiates a new player.
	 * 
	 * @precondition none
	 * @postcondition getHand().isEmpty()
	 */
	public Player() {
		this.hand = new ArrayList<Card>();
	}

	/**
	 * Adds a card to this player's hand of cards.
	 * 
	 * @precondition hand != null
	 * @postcondition getHand() == hand
	 * @param card the card to add
	 */
	public void addCard(Card card) {
		if (card == null) {
			throw new IllegalArgumentException(ExceptionMessages.NULL_CARD);
		}
		this.hand.add(card);
	}

	/**
	 * Empties hand.
	 * 
	 * @precondition none
	 * @postcondition getHane().size == 0
	 */
	public void emptyHand() {
		this.hand = new ArrayList<Card>();
	}

	/**
	 * Gets the cards in this player's hand.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the number of cards
	 */
	public ArrayList<Card> getHand() {
		return this.hand;
	}

	/**
	 * Gets the number of cards in this player's hand.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the number of cards
	 */
	public int getNumCardsInHand() {
		return this.hand.size();
	}

}
