package edu.westga.cs1302.casino.game;

import java.util.ArrayList;

import edu.westga.cs1302.casino.model.Card;
import edu.westga.cs1302.casino.model.Player;
import edu.westga.cs1302.casino.resources.GameConstants;
import edu.westga.cs1302.casino.resources.UI;

/**
 * The Blackjack class.
 * 
 * @author CS1302 Sreekanth Gopi
 * @version Fall 2021
 */
public class Blackjack extends Game {
	public static final int THRESHOLD = 17;

	/**
	 * Instantiates and starts a new game of Blackjack.
	 * 
	 * @precondition none
	 * @postcondition a new game is started
	 */
	public Blackjack() {
		super();
	}

	/**
	 * Starts a new round of BlackJack.
	 * 
	 * @precondition none
	 * @postcondition ready to play
	 */
	@Override
	public void startNewRound() {
		super.startNewRound();
		super.setMessage(GameType.BLACKJACK.toString());
	}

	/**
	 * Checks if the player has Natural 21. A hand containing one face card (i.e.,
	 * King, Queen, Jack) and one ace represents Blackjack or “Natural 21”.
	 * 
	 * @precondition
	 * @postcondition
	 * @return true if player has Natural 21, false otherwise
	 */
	@Override
	public boolean isNatural(ArrayList<Card> hand) {
		if (hand.isEmpty()) {
			throw new IllegalArgumentException("Cards are empty");
		}

		int totalValue = this.getScore(hand);
		int numOfFaceCards = 0;
		int numOfAceCards = 0;

		for (Card card : hand) {

			if (card.isAceCard()) {
				numOfAceCards += 1;
			}
			if (card.isFaceCard()) {
				numOfFaceCards += 1;
			}
		}

		if (totalValue == 21 && numOfFaceCards == 1 && numOfAceCards == 1 && hand.size() == 2) {
			return true;
		}
		return false;
	}

	/**
	 * Hit: player asks for an additional card from the deck.
	 * 
	 * @precondition none
	 * @postcondition one card was dealt from the deck and added to the player's
	 *                hand; if the player busts, the dealer wins the round and the
	 *                message of the game is "Bust - you lose."
	 * @return false if player busted, true otherwise
	 */
	@Override
	public boolean hit() {
		super.dealCardTo(this.getHumanPlayer());
		if (this.getScore(this.getHand(this.getHumanPlayer())) > 21) {
			this.dealerWinsRound(UI.PLAYER_BUSTS);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns the total blackjackscore. Aces may be worth 1 or 11 points based on
	 * the player's hand. If the player has one Ace and three other non-Ace cards,
	 * and if the sum of the cards is <= 10, then the Ace will be worth 11 points,
	 * otherwise it will be worth 1 point. If the last drawn card creates a bust by
	 * counting the ace as 11, the player simply counts the ace as a 1 and continues
	 * to play (stand or hit).
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the number of cards in hand.
	 */
	@Override
	public int getScore(ArrayList<Card> hand) {
		int score = 0;
		int value = 10;

		for (Card currCard : hand) {
			if (currCard.isAceCard()) {
				value = currCard.getRank() + GameConstants.ACE_INCREMENT;
			} else if (currCard.isFaceCard()) {
				value = 10;
			} else {
				value = currCard.getRank();
			}
			score += value;
			if (currCard.isAceCard() && score > 21) {
				score -= (GameConstants.ACE_INCREMENT);
			}
		}
		return score;
	}

	/**
	 * The player has finished their turn and not busted, so now they stand. The
	 * dealer plays their turn and the game ends.
	 * 
	 * @precondition the player stands
	 * @postcondition the game reached a resolution
	 */
	@Override
	public void humanPlayerStands() {
		Player dealer = this.getDealer();
		Player human = this.getHumanPlayer();
		int humanScore = this.getScore(human.getHand());
		int dealerScore = this.getScore(dealer.getHand());
		boolean humanHasNatural = this.hasNatural(this.getHumanPlayer());
		boolean dealerHasNatural = this.hasNatural(dealer);
		
		while (this.getScore(dealer.getHand()) <= Blackjack.THRESHOLD) {
			super.dealCardTo(dealer);
		}

		if (humanHasNatural && dealerHasNatural) {
			this.registerTieRound(UI.STANDOFF_NATURAL);

		} else if (this.getScore(dealer.getHand()) == GameConstants.TWENTY_ONE
				&& humanScore == GameConstants.TWENTY_ONE) {
			this.registerTieRound(UI.STANDOFF);

		} else if (humanHasNatural && !dealerHasNatural) {

			this.humanPlayerWinsRound(UI.PLAYER_WINS);

		} else if (this.isNatural(dealer.getHand()) && !humanHasNatural) {

			this.dealerWinsRound(UI.DEALER_WINS);

		} else if (humanScore == GameConstants.TWENTY_ONE
				&& dealerScore != GameConstants.TWENTY_ONE && !humanHasNatural) {

			this.humanPlayerWinsRound(UI.PLAYER_WINS);

		} else if (dealerScore == GameConstants.TWENTY_ONE
				&& humanScore != GameConstants.TWENTY_ONE && !dealerHasNatural) {

			this.dealerWinsRound(UI.DEALER_WINS);

		} else if (this.getScore(dealer.getHand()) > GameConstants.TWENTY_ONE) {

			this.humanPlayerWinsRound(UI.DEALER_BUSTS);

		} else if (humanScore > this.getScore(dealer.getHand())
				&& dealerScore < GameConstants.TWENTY_ONE
				&& humanScore < GameConstants.TWENTY_ONE) {
			
			this.humanPlayerWinsRound(UI.PLAYER_WINS);

		} else if (this.getScore(dealer.getHand()) > this.getScore(human.getHand())
				&& dealerScore < GameConstants.TWENTY_ONE
				&& humanScore < GameConstants.TWENTY_ONE) {

			this.dealerWinsRound(UI.DEALER_WINS);

		} else if (humanScore == this.getScore(dealer.getHand())
				&& dealerScore < GameConstants.TWENTY_ONE
				&& humanScore < GameConstants.TWENTY_ONE) {
			this.registerTieRound(UI.TIE);
		}

	}

}
