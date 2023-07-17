package edu.westga.cs1302.casino.viewmodel;

import java.util.ArrayList;

import edu.westga.cs1302.casino.game.Baccarat;
import edu.westga.cs1302.casino.game.Blackjack;
import edu.westga.cs1302.casino.game.Game;
import edu.westga.cs1302.casino.game.GameType;
import edu.westga.cs1302.casino.model.Card;
import edu.westga.cs1302.casino.model.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class CasinoViewModel.
 * 
 * @author CS 1302
 * @version Fall2021
 */
public class ViewModel {

	private Game game;

	private StringProperty betTextFieldProperty;
	private StringProperty potLabelProperty;
	private StringProperty playerScoreLabelProperty;
	private StringProperty playerMoneyLabelProperty;
	private StringProperty gameLabelProperty;
	private StringProperty dealerScoreLabelProperty;
	private StringProperty statsLabelProperty;

	/**
	 * Instantiates a new casino view model.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param gameType the type of the game to play
	 */
	public ViewModel(GameType gameType) {

		if (gameType == GameType.BLACKJACK) {
			this.game = new Blackjack();
		} else {
			this.game = new Baccarat();
		}

		this.betTextFieldProperty = new SimpleStringProperty();
		this.potLabelProperty = new SimpleStringProperty();
		this.gameLabelProperty = new SimpleStringProperty();
		this.playerScoreLabelProperty = new SimpleStringProperty();
		this.playerMoneyLabelProperty = new SimpleStringProperty();
		this.dealerScoreLabelProperty = new SimpleStringProperty();
		this.statsLabelProperty = new SimpleStringProperty();

		this.updateGameLabelProperty();
		this.updatePlayerMoneyLabelProperty();
	}

	/**
	 * Updates the game label property.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void updateGameLabelProperty() {
		this.gameLabelProperty.set(this.game.getMessage());
	}

	/**
	 * Updates the statistics label property.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void updateStatsLabelProperty() {
		this.statsLabelProperty.set(this.game.getStats());
	}

	/**
	 * The stats label property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the stats label property
	 */
	public StringProperty statsLabelProperty() {
		return this.statsLabelProperty;
	}

	/**
	 * The player score label property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the player score label property
	 */
	public StringProperty playerScoreLabelProperty() {
		return this.playerScoreLabelProperty;
	}

	/**
	 * Updates the player's score.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void updatePlayerScoreLabelProperty() {
		int score = this.game.getScore(this.game.getHumanPlayer().getHand());
		this.playerScoreLabelProperty.set("Score: " + score);
	}

	/**
	 * The player money property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the player money label property
	 */
	public StringProperty playerMoneyLabelProperty() {
		return this.playerMoneyLabelProperty;
	}

	/**
	 * Updates the label of the player's money.
	 * 
	 * @precondition message != null && !message.isEmpty()
	 * @postcondition none
	 * @param message the message
	 */
	public void updatePlayerMoneyLabelProperty(String message) {
		if (message == null || message.isEmpty()) {
			throw new IllegalArgumentException("Message can't be null.");
		}
		int money = this.game.getHumanPlayer().getMoney();
		this.playerMoneyLabelProperty.set(message + "\n$" + money);
	}

	/**
	 * Updates the label of the player's money.
	 * 
	 * @precondition none)
	 * @postcondition none
	 */
	public void updatePlayerMoneyLabelProperty() {
		int money = this.game.getHumanPlayer().getMoney();
		this.playerMoneyLabelProperty.set("$" + money);
	}

	/**
	 * The dealer score label property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the dealer score label property
	 */
	public StringProperty dealerScoreLabelProperty() {
		return this.dealerScoreLabelProperty;
	}

	/**
	 * Updates the dealer's score.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void updateDealerScoreLabelProperty() {
		int score = this.game.getScore(this.game.getDealer().getHand());
		this.dealerScoreLabelProperty.set("Score: " + score);
	}

	/**
	 * The game label property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the game label property
	 */
	public StringProperty gameLabelProperty() {
		return this.gameLabelProperty;
	}

	/**
	 * Set text field property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the bet text field property
	 */
	public StringProperty betTextFieldProperty() {
		return this.betTextFieldProperty;
	}

	/**
	 * Updates the pot amount.
	 *
	 * @precondition none
	 * @postcondition none
	 */
	public void updatePotLabelProperty() {
		int pot = this.game.getPot();
		this.potLabelProperty.set("$" + pot);
	}

	/**
	 * Pot label property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the potLabel property
	 */
	public StringProperty potLabelProperty() {
		return this.potLabelProperty;
	}

	/**
	 * Deals the cards.
	 *
	 * @precondition none
	 * @postcondition none
	 */
	public void deal() {
		this.game.dealHands();
	}

	/**
	 * Starts a new round of this game.
	 *
	 * @precondition none
	 * @postcondition none
	 */
	public void play() {
		this.game.startNewRound();
		this.resetFields();
	}

	/**
	 * Resets all form fields.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	private void resetFields() {
		this.updateGameLabelProperty();
		this.updatePlayerMoneyLabelProperty();
		this.betTextFieldProperty.set("");
		this.playerScoreLabelProperty.set("");
		this.dealerScoreLabelProperty.set("");
		this.potLabelProperty.set("");
		this.statsLabelProperty.set("");
	}

	/**
	 * Make bet and set pot.
	 *
	 * @precondition none
	 * @postcondition none
	 */
	public void bet() {
		try {
			int bet = Integer.parseInt(this.betTextFieldProperty.get());
			this.game.setPot(bet);
			this.updatePotLabelProperty();
			this.updatePlayerMoneyLabelProperty();
		} catch (Exception exception) {
			this.updatePlayerMoneyLabelProperty("\'" + this.betTextFieldProperty.get() + "\' is invalid, try again.");
			this.betTextFieldProperty.set("");
			return;
		}
	}

	/**
	 * Player asks for card.
	 *
	 * @precondition none
	 * @postcondition none
	 */
	public void hit() {
		if (this.game.hit()) {
			this.updatePlayerScoreLabelProperty();
		} else {
			this.updateStatsLabelProperty();
		}
		this.updateGameLabelProperty();
	}

	/**
	 * Player stands.
	 *
	 * @precondition none
	 * @postcondition none
	 */
	public void playerStands() {
		this.game.humanPlayerStands();
		this.updateDealerScoreLabelProperty();
		this.updateGameLabelProperty();
		this.updateStatsLabelProperty();
	}

	/**
	 * Gets the player's hand of cards.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the hand of cards from player
	 */
	public ArrayList<Card> getPlayerHand() {
		Player humanPlayer = this.game.getHumanPlayer();
		return this.game.getHand(humanPlayer);
	}

	/**
	 * Gets the dealer cards.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the hand of cards from dealer
	 */
	public ArrayList<Card> getDealerHand() {
		Player dealer = this.game.getDealer();
		return this.game.getHand(dealer);
	}
}
