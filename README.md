# Blackjack Game
BlackJack &amp; Baccarat Game in Java

## Game Overview

The game project is divided into the following seven classes:

1. Application
2. Game
3. Model
4. Resources
5. Deck
6. View
7. ViewModel

## Game Specifications

1. The dealer serves as a permanent bank and never runs out of money.
2. The player joins the casino table with a specified amount of money. They place bets on each round and can play as long as they have enough money. The dealer matches the player's bet, resulting in a pot value equal to twice the bet amount.
3. To start the game, the player places a bet. Two cards are dealt to each player (dealer and human player) alternately. The dealer's first card is face down, and the second card is face up.
4. The player can see their own cards and can choose to stand (ask the dealer to reveal all cards) or hit (ask for additional cards one at a time).
5. Card values: Face cards (King, Queen, Jack) are worth 10 points, numbered cards are worth their pip value, and Aces can be worth 1 or 11 points.
6. A "soft hand" is a combination of an Ace and a non-face card, where the Ace can be counted as 1 or 11. A hand with one face card and one Ace represents Blackjack or "Natural 21".
7. The dealer must hit if their hand score is 17 or less and stand if their score is 18 or more.
8. The player can choose to hit or stand, even if the dealer busts by counting an Ace as 11.
9. If the player has a Natural 21, the dealer pays double the pot money, unless the dealer also has Natural 21, resulting in a tie.
10. The player wins if they have a higher total than the dealer (but not exceeding 21) or if the dealer busts. Ties result in the player getting their bet back.

## Implementation Details

The project involves refactoring the Card class to use enums for Suit and Rank. The Deck class is also updated to use Rank and Suit enums. The Player class provides general functionalities, while the HumanPlayer class extends it with additional features such as money management. The Blackjack class completes the game logic, including dealing cards, setting the pot, and resolving rounds.

## Running the Game

1. Uncomment the code in the Blackjack class and other related classes.
2. Build and run the application.
3. Follow the on-screen instructions to play the game.
