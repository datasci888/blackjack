package edu.westga.cs1302.casino.test.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs1302.casino.model.Deck;
import edu.westga.cs1302.casino.resources.ExceptionMessages;

/**
 * Ensures correct functionality of Deck.
 * 
 * @author CS 1302
 * @version Fall 2021
 */
public class TestDraw {

	@Test
	public void testOneDraw() {
		Deck deck = new Deck();
		assertEquals(Deck.DECK_SIZE, deck.getTopIndex());
		deck.draw();
		assertEquals(51, deck.getTopIndex());
	}

	@Test
	public void testDrawAllCardsException() {
		Deck deck = new Deck();
		assertEquals(deck.getTopIndex(), Deck.DECK_SIZE);
		for (int i = 0; i < Deck.DECK_SIZE; i++) {
			deck.draw();
		}
		assertEquals(0, deck.getTopIndex());
		assertThrows(IllegalArgumentException.class, () -> deck.draw());
		IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> deck.draw());
		assertEquals(ExceptionMessages.EMPTY_DECK, exc.getMessage());
	}

}
