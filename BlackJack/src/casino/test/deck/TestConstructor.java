package edu.westga.cs1302.casino.test.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs1302.casino.model.Deck;

/**
 * Tests the correct functionality of Deck.
 * 
 * @author CS1302
 * @version Fall 2021
 */
public class TestConstructor {

	@Test
	public void testValidCreation() {
		Deck deck = new Deck();
		assertEquals(deck.getTopIndex(), Deck.DECK_SIZE);
	}
}
