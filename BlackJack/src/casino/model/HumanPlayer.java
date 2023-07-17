package edu.westga.cs1302.casino.model;

import edu.westga.cs1302.casino.resources.ExceptionMessages;

/**
 * The class HumanPlayer.
 * 
 * @author CS1302
 * @version Fall 2021
 */
public class HumanPlayer extends Player {

	private int money;

	/**
	 * Instantiates a new human player.
	 * 
	 * @precondition money > 0
	 * @postcondition getMoney() == money
	 * @param money this player's money
	 */
	public HumanPlayer(int money) {
		super();
		if (money < 0) {
			throw new IllegalArgumentException(ExceptionMessages.CANNOT_ENTER_CASINO);
		}
		this.money = money;
	}

	/**
	 * Returns this player's money.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the money
	 */
	public int getMoney() {
		return this.money;
	}

	/**
	 * Updates this player's money with the specified amount.
	 * 
	 * @precondition none
	 * @postcondition getMoney() == getMoney()@prev + amount
	 * @param amount the amount to set
	 */
	public void receive(int amount) {
		this.money += amount;
	}

	/**
	 * The player places a bet in the specified amount.
	 * 
	 * @precondition 0 < amount <= getMoney()
	 * @postcondition getMoney() == getMoney()@prev - amount
	 * @param amount the amount to bet
	 */
	public void bet(int amount) {
		if (amount > this.money || amount <= 0) {
			throw new IllegalArgumentException(ExceptionMessages.INVALID_AMOUNT);
		}
		this.money -= amount;
	}

}
