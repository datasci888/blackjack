package edu.westga.cs1302.casino.viewmodel;

import edu.westga.cs1302.casino.resources.ExceptionMessages;
import javafx.scene.paint.Color;

/**
 * The Suit Design class creates the design of the suit in terms of color and
 * symbol. More unicode characters/symbols can be found at the following link:
 * http://en.wikipedia.org/wiki/Playing_cards_in_Unicode
 * 
 * @author CS1302
 * @version Fall 2021
 */
public enum ViewSuit {

	SPADES(Color.BLACK, "\u2660"), HEARTS(Color.RED, "\u2665"), CLUBS(Color.BLACK, "\u2663"),
	DIAMONDS(Color.RED, "\u2666");

	private Color color;
	private String symbol;

	ViewSuit(Color color, String symbol) {
		this.color = color;
		this.symbol = symbol;
	}

	/**
	 * Returns the color of this suit design.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Returns the symbol of this suit design.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the symbol
	 */
	public String getSymbol() {
		return this.symbol;
	}

	/**
	 * Parses the suit design type.
	 *
	 * @precondition suitString != null && Suit.values() contains suitString
	 * @postcondition none
	 * 
	 * @param suitString the suit to be parsed
	 * @return the animal type as type AnimalType
	 */
	public static ViewSuit parseSuit(String suitString) {
		suitString = suitString.toUpperCase();
		switch (suitString) {
		case "SPADES":
			return SPADES;
		case "CLUBS":
			return CLUBS;
		case "DIAMONDS":
			return DIAMONDS;
		case "HEARTS":
			return HEARTS;
		default:
			throw new IllegalArgumentException(ExceptionMessages.INVALID_SUIT);
		}
	}
}