package edu.westga.cs1302.casino.game;

import java.util.ArrayList;

import edu.westga.cs1302.casino.model.Card;
import edu.westga.cs1302.casino.model.Player;
import edu.westga.cs1302.casino.resources.UI;

/**
 * The Baccarat class.
 * 
 * @author CS1302
 * @version Fall 2021
 */
public class Baccarat extends Game {

	private static final int TEN_OR_FACECARD = 10;
	private static final int THRESHOLD = 8;

	/**
	 * Instantiates a game and starts a new round.
	 * 
	 * @precondition none
	 * @postcondition players are instantiated & all stats are 0 & new round starts
	 */
	public Baccarat() {
		super();
	}

	/**
	 * Starts a new round of the game.
	 * 
	 * @precondition none
	 * @postcondition deck is instantiated and shuffled && getPot() == 0 && the
	 *                players have no cards
	 */
	@Override 
	public void startNewRound() {
		super.startNewRound();
		super.setMessage(GameType.BACCARAT.toString());
	}

	/**
	 * Hit: player asks for an additional card from the deck.
	 * 
	 * @precondition none
	 * @postcondition player draws card if has less than 3 cards and a score of at
	 *                most 5, otherwise the message set to the game is "Must stand."
	 * @return true, since the dealer must always play
	 */
	@Override
	public boolean hit() {
		
		Player human = this.getHumanPlayer();
		int humanScore = this.getScore(human.getHand());
		
		if (human.getNumCardsInHand() < 3 && humanScore <= 5) {
			this.dealCardTo(human);
		} else {
			this.setMessage(UI.MUST_STAND);
		}
		return true;
	}

	/**
	 * The player stands and now the dealer will play their turn and the game will
	 * reach a resolution.
	 * 
	 * @precondition player has decided to stand
	 * @postcondition game concluded
	 */
	@Override
	public void humanPlayerStands() {
		this.dealerPlays();
		this.getGameResolution();
	}

	private void dealerPlays() {

		Player dealer = this.getDealer();
		int dealerScore = this.getScore(dealer.getHand());
		Player human = this.getHumanPlayer();
		ArrayList<Card> handHand = human.getHand();

		if (handHand.size() == 3) {
			Card optionalThirdCard = handHand.get(2);
			int thirdCardRank = optionalThirdCard.getRank();
			if ((thirdCardRank > 9 || thirdCardRank == 1) && dealerScore < 3) {
				this.dealCardTo(dealer);
			} else if ((thirdCardRank == 8 && dealerScore <= 2) || (thirdCardRank >= 6 && dealerScore <= 6)
					|| (thirdCardRank >= 5 && dealerScore <= 5) || (thirdCardRank >= 2 && dealerScore <= 4)) {
				this.dealCardTo(dealer);
			}
		}
	}

	private void getGameResolution() {

		Player dealer = this.getDealer();
		Player human = this.getHumanPlayer();
		int humanScore = this.getScore(human.getHand());
		int dealerScore = this.getScore(dealer.getHand());
		boolean humanHasNatural = this.hasNatural(this.getHumanPlayer());
		boolean dealerHasNatural = this.hasNatural(dealer);

		if (humanHasNatural) {
			if (dealerHasNatural) {
				this.registerTieRound(UI.STANDOFF_NATURAL);
			} else {
				this.humanPlayerWinsRound(UI.PLAYER_WINS_DOUBLE);
			}
		} else if (humanScore >= THRESHOLD) {
			if (dealerHasNatural) {
				this.dealerWinsRound(UI.DEALER_WINS_NATURAL);
			} else if (dealerScore >= THRESHOLD) {
				this.registerTieRound(UI.STANDOFF);
			} else {
				this.humanPlayerWinsRound(UI.PLAYER_WINS);
			}
		} else if (humanScore < dealerScore) {
			if (dealerHasNatural) {
				this.registerTieRound(UI.DEALER_WINS_NATURAL);
			} else {
				this.dealerWinsRound(UI.DEALER_WINS);
			}
		} else if (humanScore > dealerScore) {
			this.humanPlayerWinsRound(UI.PLAYER_WINS);
		} else {
			this.registerTieRound(UI.TIE);
		}
	}

	/**
	 * Checks if the specified hand is a "Natural" according to the Baccarat rules.
	 * A "Natural" is a hand of exactly two cards totaling 8 or 9.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param hand an array list of cards representing the player's hand
	 * @return true if hand is natural, false otherwise
	 */
	@Override
	public boolean isNatural(ArrayList<Card> hand) {
		if ((this.getScore(hand) > THRESHOLD && hand.size() == 2)) {
			return true;
		}
		return false;
	}

	/**
	 * Calculates the score of the hand, according to the Baccarat rules. All card
	 * values are added.
	 * 
	 * Cards ACE through NINE have pip value, while TEN, J, Q, and K have a value of
	 * zero. If a hand score is more than 10, the first digit is dropped and the
	 * second digit becomes the value of the hand. For example, NINE and FOUR add up
	 * to 13 but the hand score is 3.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param hand an array list of cards representing the player's hand
	 * @return the score
	 */
	@Override
	public int getScore(ArrayList<Card> hand) {
		int score = 0;
		
		for (Card currCard : hand) {
			if (currCard.getRank() >= TEN_OR_FACECARD) {
				score += 0;
			} else {
				score += currCard.getRank();
			}
		}
		if (score > 10) {
			return score % 10;
		}
		return score;
	}

	}
