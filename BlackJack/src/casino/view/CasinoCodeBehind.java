package edu.westga.cs1302.casino.view;

import java.util.ArrayList;
import java.util.Optional;

import edu.westga.cs1302.casino.game.GameType;
import edu.westga.cs1302.casino.model.Card;
import edu.westga.cs1302.casino.resources.UI;
import edu.westga.cs1302.casino.viewmodel.ViewCard;
import edu.westga.cs1302.casino.viewmodel.ViewModel;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Casino defines the "controller" for casino.fxml.
 * 
 * @author CS1302
 * @version Fall 2021
 */
public class CasinoCodeBehind {

	private static final Color CASINO_TABLE_BACKGROUND = Color.GREEN.deriveColor(1, 1, 1, 0.8);

	private ViewCard viewCard;
	private ViewModel theViewModel;

	@FXML
	private AnchorPane pane;

	@FXML
	private HBox playerBox;

	@FXML
	private HBox dealerBox;

	@FXML
	private Button playButton;

	@FXML
	private Button standButton;

	@FXML
	private Button dealButton;

	@FXML
	private Button betButton;

	@FXML
	private Button hitButton;

	@FXML
	private Button checkoutButton;

	@FXML
	private Label gameLabel;

	@FXML
	private Label statsLabel;

	@FXML
	private Label playerScoreLabel;

	@FXML
	private Label dealerScoreLabel;

	@FXML
	private Label potLabel;

	@FXML
	private Label playerMoneyLabel;

	@FXML
	private Label userQuestion;

	@FXML
	private TextField betTextField;

	@FXML
	private Label invisible;

	/**
	 * Instantiates a new casino-code-behind.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public CasinoCodeBehind() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Welcome!");
		alert.setHeaderText("Which game would you like to play?");
		ButtonType blackjackButton = new ButtonType(GameType.BLACKJACK.toString());
		ButtonType baccaratButton = new ButtonType(GameType.BACCARAT.toString());
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(blackjackButton, baccaratButton, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == blackjackButton) {
			this.theViewModel = new ViewModel(GameType.BLACKJACK);
		} else if (result.get() == baccaratButton) {
			this.theViewModel = new ViewModel(GameType.BACCARAT);
		} else {
			System.exit(0);
		}
		this.viewCard = new ViewCard();
	}

	/**
	 * Initializes the GUI components, binding them to the view model properties
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	@FXML
	public void initialize() {
		this.initializeUI();
		this.bindComponentsToViewModel();
		this.bindButtonsDisableProperty();
		this.setupListeners();
	}

	private void initializeUI() {
		BackgroundFill fill = new BackgroundFill(CASINO_TABLE_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(fill);
		this.pane.setBackground(background);
	}

	private void bindComponentsToViewModel() {
		this.gameLabel.textProperty().bind(this.theViewModel.gameLabelProperty());
		this.betTextField.textProperty().bindBidirectional(this.theViewModel.betTextFieldProperty());
		this.potLabel.textProperty().bindBidirectional(this.theViewModel.potLabelProperty());
		this.playerScoreLabel.textProperty().bindBidirectional(this.theViewModel.playerScoreLabelProperty());
		this.dealerScoreLabel.textProperty().bindBidirectional(this.theViewModel.dealerScoreLabelProperty());
		this.playerMoneyLabel.textProperty().bindBidirectional(this.theViewModel.playerMoneyLabelProperty());
		this.statsLabel.textProperty().bindBidirectional(this.theViewModel.statsLabelProperty());
	}

	private void bindButtonsDisableProperty() {
		this.standButton.disableProperty()
				.bind(this.playerScoreLabel.textProperty().isEmpty()
						.or(this.dealerScoreLabel.textProperty().isNotEmpty())
						.or((this.statsLabel.textProperty().isNotEmpty())));
		this.hitButton.disableProperty().bind(this.playerScoreLabel.textProperty().isEmpty()
				.or(this.dealerScoreLabel.textProperty().isNotEmpty()).or((this.statsLabel.textProperty().isNotEmpty())
						.or(this.gameLabel.textProperty().isEqualToIgnoreCase(UI.MUST_STAND))));
		this.betTextField.disableProperty().bind(this.potLabel.textProperty().isNotEmpty());
		this.betButton.disableProperty().bind(this.potLabel.textProperty().isNotEmpty());
		this.dealButton.disableProperty()
				.bind(this.playerScoreLabel.textProperty().isNotEmpty().or(this.potLabel.textProperty().isEmpty()));
		this.playButton.disableProperty().bind(this.statsLabel.textProperty().isEmpty());
		this.checkoutButton.disableProperty().bind(this.potLabel.textProperty().isNotEmpty());
	}

	private void setupListeners() {

		this.theViewModel.gameLabelProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != oldValue) {
				this.theViewModel.updatePlayerMoneyLabelProperty();
				if (newValue.contains(UI.GAME_OVER)) {
					this.gameOver();
				}
			}
		});
	}

	@FXML
	private void onBet() {
		this.theViewModel.bet();
	}

	@FXML
	private void onDeal() {
		this.theViewModel.deal();
		this.viewDeal();
	}

	@FXML
	private void onHit() {
		this.theViewModel.hit();
		this.displayPlayerCards();
	}

	@FXML
	private void onStand() {
		this.theViewModel.playerStands();
		this.displayDealerCards();
	}

	@FXML
	private void onPlay() {
		this.theViewModel.play();
		this.playerBox.getChildren().clear();
		this.dealerBox.getChildren().clear();
	}

	@FXML
	private void onCheckout() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Checkout");
		alert.setHeaderText("Cash out and leave the casino?");
		alert.setContentText("You can cash out " + this.playerMoneyLabel.getText());
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Stage stage = (Stage) this.checkoutButton.getScene().getWindow();
			stage.close();
		}
	}

	private void viewDeal() {
		this.displayPlayerCards();
		this.displayDealerInitialCards();
	}

	private void displayPlayerCards() {
		this.playerBox.getChildren().clear();
		ArrayList<Card> playerHand = this.theViewModel.getPlayerHand();
		for (Card card : playerHand) {
			this.playerBox.getChildren().add(this.viewCard.faceUp(card));
		}
		this.theViewModel.updatePlayerScoreLabelProperty();
	}

	private void displayDealerInitialCards() {
		this.dealerBox.getChildren().clear();
		ArrayList<Card> dealerHand = this.theViewModel.getDealerHand();
		this.dealerBox.getChildren().add(this.viewCard.faceDown(dealerHand.get(0)));
		this.dealerBox.getChildren().add(this.viewCard.faceUp(dealerHand.get(1)));
	}

	private void displayDealerCards() {
		this.dealerBox.getChildren().clear();
		ArrayList<Card> dealerHand = this.theViewModel.getDealerHand();
		for (Card card : dealerHand) {
			this.dealerBox.getChildren().add(this.viewCard.faceUp(card));
		}
		this.theViewModel.updateDealerScoreLabelProperty();
	}

	private void gameOver() {
		this.displayPlayerCards();
		this.displayDealerCards();

		this.gameLabel.setTextFill(Color.RED);
		this.playerMoneyLabel.setTextFill(Color.RED);
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(UI.GAME_OVER);
		alert.setHeaderText(UI.OUT_OF_MONEY);
		alert.setContentText(UI.GOOD_BYE);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Stage stage = (Stage) this.checkoutButton.getScene().getWindow();
			stage.close();
		}
	}
}
