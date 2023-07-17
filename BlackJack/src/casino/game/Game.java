package edu.westga.cs1302.casino.game;

import java.util.ArrayList;

import edu.westga.cs1302.casino.model.Card;
import edu.westga.cs1302.casino.model.Deck;
import edu.westga.cs1302.casino.model.HumanPlayer;
import edu.westga.cs1302.casino.model.Player;
import edu.westga.cs1302.casino.resources.ExceptionMessages;
import edu.westga.cs1302.casino.resources.UI;

/**
 * The Game class.
 * 
 * @author CS1302 Sreekanth Gopi
 * @version Fall 2021
 */
public abstract class Game {

	public static final int HUMAN_PLAYER_MONEY = 100;
	public static final int NUM_CARDS_DEALT_INITIALLY = 2;
	private Deck deck;
	private Player dealer;
	private HumanPlayer humanPlayer;

	private int pot;

	private int dealerWins;
	private int humanWins;
	private int ties;

	private String message;

	/**
	 * Instantiates a game and starts a new round.
	 * 
	 * @precondition none
	 * @postcondition players are instantiated & all stats are 0 & new round starts
	 */

	public Game() {
		this.dealerWins = 0;
		this.humanWins = 0;
		this.ties = 0;
		this.dealer = new Player();
		this.humanPlayer = new HumanPlayer(HUMAN_PLAYER_MONEY);
		this.startNewRound();
	}

	/**
	 * Starts a new round of the game.
	 * 
	 * @precondition none
	 * @postcondition deck is instantiated and shuffled && getPot() == 0 && the
	 *                players have no cards
	 */
	public void startNewRound() {
		this.deck = new Deck();
		this.deck.shuffle();
		this.pot = 0;
		this.dealer.emptyHand();
		this.humanPlayer.emptyHand();
	}

	/**
	 * Sets the message of this game.
	 * 
	 * @precondition message != null && !message.isEmpty()
	 * @postcondition none
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		if (message == null) {
			throw new IllegalArgumentException(ExceptionMessages.NULL_GAME_MESSAGE);
		}
		if (message.isEmpty()) {
			throw new IllegalArgumentException(ExceptionMessages.EMPTY_GAME_MESSAGE);
		}
		this.message = message;
	}

	/**
	 * Deals a card to the specified player, form the top of the deck.
	 * 
	 * @precondition none
	 * @postcondition top card from deck goes to the specified player
	 * @param player the specified player
	 */
	public void dealCardTo(Player player) {
		if (player == null) {
			throw new IllegalArgumentException(ExceptionMessages.NULL_PLAYER);
		}
		Card card = this.deck.draw();
		player.addCard(card);
	}

	/**
	 * Gets the pot of this game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the pot
	 */
	public int getPot() {
		return this.pot;
	}

	/**
	 * Sets the pot for this game based on the specified bet.
	 * 
	 * @precondition pot > 0
	 * @postcondition getPot() == 2 * bet
	 * @param bet the amount bet by the player
	 */
	public void setPot(int bet) {
		if (bet <= 0) {
			throw new IllegalArgumentException(ExceptionMessages.INVALID_AMOUNT);
		}
		this.humanPlayer.bet(bet);
		this.pot = 2 * bet;
	}

	/**
	 * Deals the initial hands to the players.
	 * 
	 * @precondition none
	 * @postcondition players are dealt two cards and the human player gets first
	 *                card
	 */
	public void dealHands() {
		for (int count = 0; count < NUM_CARDS_DEALT_INITIALLY; count++) {
			this.dealCardTo(this.humanPlayer);
			this.dealCardTo(this.dealer);
		}
	}

	/**
	 * Record tie round.
	 * 
	 * @precondition message != null && !message.isEmpty()
	 * @postcondition message is set, the player gets half the pot (i.e., their
	 *                initial bet) and number of ties is incremented
	 * @param message the message to display
	 */
	public void registerTieRound(String message) {
		this.setMessage(message);
		this.humanPlayer.receive(this.getPot() / 2);
		this.ties++;
	}

	/**
	 * Record humanPlayer win.
	 * 
	 * @precondition message != null && !message.isEmpty()
	 * @postcondition message is set, the player gets the pot or twice the pot if
	 *                has natural, and the number of human player wins is
	 *                incremented
	 * @param message the message to display
	 */
	public void humanPlayerWinsRound(String message) {
		this.setMessage(message);
		this.humanWins++;
		if (this.isNatural(this.humanPlayer.getHand())) {
			this.humanPlayer.receive(2 * this.getPot());
		} else {
			this.humanPlayer.receive(this.getPot());
		}
	}

	/**
	 * Record dealer win.
	 * 
	 * @precondition message != null && !message.isEmpty()
	 * @postcondition message is set and the number
	 * @param message the message to display
	 */
	public void dealerWinsRound(String message) {
		if (this.humanPlayer.getMoney() == 0) {
			this.setMessage(message + System.lineSeparator() + UI.GAME_OVER);
		} else {
			this.setMessage(message);
			this.dealerWins++;
		}
	}

	/**
	 * Returns the statistics of this game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the stats as a string
	 */
	public String getStats() {
		return "You: " + this.humanWins + System.lineSeparator() + "Dealer: " + this.dealerWins + System.lineSeparator()
				+ "Ties: " + this.ties;
	}

	/**
	 * Checks if player has a natural hand.
	 * 
	 * @precondition player != null
	 * @postcondition none
	 * @param player the specified player
	 * @return true if the player's hand is a natural one, false otherwise
	 */
	public boolean hasNatural(Player player) {
		if (player == null) {
			throw new IllegalArgumentException(ExceptionMessages.NULL_PLAYER);
		}
		ArrayList<Card> hand = player.getHand();
		return this.isNatural(hand);
	}

	/**
	 * Gets the human player of this game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the human player
	 */
	public HumanPlayer getHumanPlayer() {
		return this.humanPlayer;
	}

	/**
	 * Gets the dealer of this game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the dealer
	 */
	public Player getDealer() {
		return this.dealer;
	}

	/**
	 * Gets the message of this game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Returns the player's hand of cards.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param player the specified player
	 * @return the dealer's cards
	 */
	public ArrayList<Card> getHand(Player player) {
		if (player == null) {
			throw new IllegalArgumentException(ExceptionMessages.NULL_PLAYER);
		}
		return player.getHand();
	}

	/**
	 * Checks if the specified hand is a "Natural" according to the Game rules.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param hand an array list of cards representing the player's hand
	 * @return true if hand is natural, false otherwise
	 */
	public abstract boolean isNatural(ArrayList<Card> hand);

	/**
	 * The player stands and now the dealer will play their turn and the game will
	 * reach a resolution.
	 * 
	 * @precondition player has decided to stand
	 * @postcondition game concluded
	 */
	public abstract void humanPlayerStands();

	/**
	 * Hit: player asks for an additional card from the deck.
	 * 
	 * @precondition none
	 * @postcondition player draws card, otherwise the message set to the game is "Must stand."
	 * @return true, since the dealer must always play
	 */
	public abstract boolean hit();
	
	/**
	 * Calculates the score of the hand, according to the Game rules. All card
	 * values are added.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param hand an array list of cards representing the player's hand
	 * @return the score
	 */
	public abstract int getScore(ArrayList<Card> hand);
}
