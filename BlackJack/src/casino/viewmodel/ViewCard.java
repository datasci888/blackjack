package edu.westga.cs1302.casino.viewmodel;

import edu.westga.cs1302.casino.model.Card;
import edu.westga.cs1302.casino.resources.ExceptionMessages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 * The class ViewCard.
 * 
 * @author CS1302
 * @version Fall 2021
 */
public class ViewCard extends Shape {

	public static final double CARD_WIDTH = 110;
	public static final double CARD_HEIGHT = 170;
	public static final String CARD_BACK_IMAGE = "wolfie.jpg";
	public static final double CARD_ARC = 20;

	public static final double CARD_GRID_SYMBOL_SIZE = 25;
	public static final double CARD_CENTER_SYMBOL_SIZE = 25;
	public static final double CARD_CORNER_SYMBOL_SIZE = 14;
	public static final double SYMBOL_Y_OFFSET = 4;
	public static final double SYMBOL_X_OFFSET = 4;

	private Card card;

	/**
	 * Returns a card face down.
	 * 
	 * @precondition card != null
	 * @postcondition none
	 * @param card the card to create a visual representation for
	 * @return the card face down
	 */
	public Node faceDown(Card card) {
		if (card == null) {
			throw new IllegalArgumentException(ExceptionMessages.NULL_CARD);
		}
		this.card = card;
		return this.designBackOfCard();
	}

	/**
	 * Returns a card face up.
	 * 
	 * @precondition card != null
	 * @postcondition none
	 * @param card the card to create a visual representation for
	 * @return the card face up
	 */
	public Node faceUp(Card card) {
		if (card == null) {
			throw new IllegalArgumentException(ExceptionMessages.NULL_CARD);
		}
		this.card = card;
		return this.designFrontOfCard();
	}

	private Node designFrontOfCard() {
		Pane pane = new Pane();
		Shape shape = this.drawRectangle();
		pane.getChildren().add(shape);
		shape.setFill(Color.WHITE);
		this.drawCorners(pane);
		this.drawCenter(pane);
		return pane;
	}

	private Node designBackOfCard() {
		Pane pane = new Pane();
		Shape shape = this.drawRectangle();

		try (FileInputStream input = new FileInputStream(CARD_BACK_IMAGE)) {
			Image image = new Image(input);
			ImagePattern pattern = new ImagePattern(image);
			shape.setFill(pattern);
			pane.getChildren().add(shape);
			return pane;
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Image file not found.");
		} catch (IOException iOException) {
			System.err.println(iOException.getMessage());
		}
		return pane;
	}

	private void drawCorners(Pane pane) {
		int rank = this.card.getRank();
		ViewSuit suit = ViewSuit.parseSuit(this.card.getSuit());
		this.drawTopLeftSymbols(pane, rank, suit);
		this.drawBottomRightSymbols(pane, rank, suit);
	}

	private void drawTopLeftSymbols(Pane face, int rank, ViewSuit suit) {
		Text cardValue = this.drawValue();
		cardValue.setRotate(180);
		cardValue.relocate(SYMBOL_X_OFFSET, 0);

		Text cardSuit = this.drawSymbol();
		cardValue.setRotate(0);
		double xPos = cardValue.getBoundsInParent().getMinX()
				+ (cardValue.getBoundsInParent().getWidth() - cardSuit.getBoundsInParent().getWidth()) / 2.0;
		double yPos = cardValue.getBoundsInParent().getMaxY() - SYMBOL_Y_OFFSET;
		cardSuit.relocate(xPos, yPos);
		face.getChildren().addAll(cardValue, cardSuit);
	}

	private void drawBottomRightSymbols(Pane face, int rank, ViewSuit suit) {

		Text cardValue = this.drawValue();
		cardValue.setRotate(180);
		double xPos = CARD_WIDTH - cardValue.getBoundsInParent().getWidth() - SYMBOL_X_OFFSET;
		double yPos = CARD_HEIGHT - cardValue.getBoundsInParent().getHeight();
		cardValue.relocate(xPos, yPos);

		Text cardSuit = this.drawSymbol();
		cardSuit.setRotate(180);
		xPos = cardValue.getBoundsInParent().getMaxX()
				- (cardValue.getBoundsInParent().getWidth() + cardSuit.getBoundsInParent().getWidth()) / 2.0;
		yPos = cardValue.getBoundsInParent().getMinY() - cardSuit.getBoundsInParent().getHeight() + SYMBOL_Y_OFFSET;
		cardSuit.relocate(xPos, yPos);
		face.getChildren().addAll(cardValue, cardSuit);
	}

	private static Text createTextCentered(String name, ViewSuit suit, double fontSize) {

		Text text = new Text(name);
		text.setBoundsType(TextBoundsType.VISUAL);
		text.setFill(suit.getColor());
		text.setFont(Font.font(null, fontSize));

		return text;
	}

	private void drawCenter(Pane pane) {

		int rank = this.card.getRank();
		ViewSuit suit = ViewSuit.parseSuit(this.card.getSuit());

		if (rank == 1) {
			Text text = createTextCentered(suit.getSymbol(), suit, CARD_CENTER_SYMBOL_SIZE);
			text.relocate((CARD_WIDTH - text.getBoundsInParent().getWidth()) / 2,
					(CARD_HEIGHT - text.getBoundsInParent().getHeight()) / 2);

			pane.getChildren().addAll(text);

		} else if (rank == 12 || rank == 13 || rank == 14) {
			Text text = createTextCentered(suit.getSymbol(), suit, CARD_CENTER_SYMBOL_SIZE);
			text.relocate((CARD_WIDTH - text.getBoundsInParent().getWidth()) / 2,
					(CARD_HEIGHT - text.getBoundsInLocal().getHeight()) / 2);
			pane.getChildren().addAll(text);

		} else {

			GridPane gridPane = this.drawCenter(rank, suit);
			gridPane.setGridLinesVisible(false);
			gridPane.setHgap(0);
			gridPane.setVgap(0);
			gridPane.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
			gridPane.setPadding(new Insets(10));
			pane.getChildren().addAll(gridPane);
		}

	}

	private GridPane drawCenter(int rank, ViewSuit suit) {
		GridPane gridPane = new GridPane();
		switch (rank) {
		case 2:
			this.drawCenterTwo(suit, gridPane);
			break;
		case 3:
			this.drawCenterThree(suit, gridPane);
			break;
		case 4:
			this.drawCenterFour(suit, gridPane);
			break;
		case 5:
			this.drawCenterFive(suit, gridPane);
			break;
		case 6:
			this.drawCenterSix(suit, gridPane);
			break;
		case 7:
			this.drawCenterSeven(suit, gridPane);
			break;
		case 8:
			this.drawCenterEight(suit, gridPane);
			break;
		case 9:
			this.drawCenterNine(suit, gridPane);
			break;
		case 10:
			this.drawCenterTen(suit, gridPane);
			break;
		default:
			break;
		}
		return gridPane;
	}

	private void drawCenterTen(ViewSuit suit, GridPane gridPane) {
		this.drawCenterThree(suit, gridPane);
		gridPane.add(createGridSymbol(suit, true), 0, 3);
		gridPane.add(createGridSymbol(suit), 1, 0, 1, 2);
		gridPane.add(createGridSymbol(suit), 2, 0);
		gridPane.add(createGridSymbol(suit), 2, 1);
		gridPane.add(createGridSymbol(suit, true), 2, 2);
		gridPane.add(createGridSymbol(suit, true), 2, 3);
		gridPane.add(createGridSymbol(suit, true), 1, 2, 1, 2);
	}

	private void drawCenterNine(ViewSuit suit, GridPane gridPane) {
		this.drawCenterThree(suit, gridPane);
		gridPane.add(createGridSymbol(suit, true), 0, 3);
		gridPane.add(createGridSymbol(suit), 1, 1, 1, 2);
		gridPane.add(createGridSymbol(suit), 2, 0);
		gridPane.add(createGridSymbol(suit), 2, 1);
		gridPane.add(createGridSymbol(suit, true), 2, 2);
		gridPane.add(createGridSymbol(suit, true), 2, 3);
	}

	private void drawCenterEight(ViewSuit suit, GridPane gridPane) {
		this.drawCenterThree(suit, gridPane);
		gridPane.add(createGridSymbol(suit), 1, 0, 1, 2);
		gridPane.add(createGridSymbol(suit, true), 1, 1, 1, 2);
		gridPane.add(createGridSymbol(suit), 2, 0);
		gridPane.add(createGridSymbol(suit), 2, 1);
		gridPane.add(createGridSymbol(suit, true), 2, 2);
	}

	private void drawCenterSeven(ViewSuit suit, GridPane gridPane) {
		this.drawCenterThree(suit, gridPane);
		gridPane.add(createGridSymbol(suit), 1, 0, 1, 2);
		gridPane.add(createGridSymbol(suit), 2, 0);
		gridPane.add(createGridSymbol(suit), 2, 1);
		gridPane.add(createGridSymbol(suit, true), 2, 2);
	}

	private void drawCenterSix(ViewSuit suit, GridPane gridPane) {
		this.drawCenterThree(suit, gridPane);
		gridPane.add(createGridSymbol(suit), 1, 0);
		gridPane.add(createGridSymbol(suit), 1, 1);
		gridPane.add(createGridSymbol(suit, true), 1, 2);
	}

	private void drawCenterFive(ViewSuit suit, GridPane gridPane) {
		gridPane.add(createGridSymbol(suit), 0, 0);
		gridPane.add(createGridSymbol(suit, true), 0, 2);
		gridPane.add(createGridSymbol(suit), 1, 1);
		gridPane.add(createGridSymbol(suit), 2, 0);
		gridPane.add(createGridSymbol(suit, true), 2, 2);
	}

	private void drawCenterFour(ViewSuit suit, GridPane gridPane) {
		this.drawCenterTwo(suit, gridPane);
		gridPane.add(createGridSymbol(suit), 1, 0);
		gridPane.add(createGridSymbol(suit, true), 1, 1);
	}

	private void drawCenterThree(ViewSuit suit, GridPane gridPane) {
		gridPane.add(createGridSymbol(suit), 0, 0);
		gridPane.add(createGridSymbol(suit), 0, 1);
		gridPane.add(createGridSymbol(suit, true), 0, 2);
	}

	private void drawCenterTwo(ViewSuit suit, GridPane gridPane) {
		gridPane.add(createGridSymbol(suit), 0, 0);
		gridPane.add(createGridSymbol(suit, true), 0, 1);
	}

	private String getLetter(int rank) {
		if (rank == 1) {
			return "A";
		} else if (rank == 12) {
			return "J";
		} else if (rank == 13) {
			return "Q";
		} else if (rank == 14) {
			return "K";
		} else {
			return Integer.toString(rank);
		}
	}

	private Rectangle drawRectangle() {
		Rectangle shape = new Rectangle();
		shape.setWidth(CARD_WIDTH);
		shape.setHeight(CARD_HEIGHT);
		shape.setArcWidth(CARD_ARC);
		shape.setArcHeight(CARD_ARC);
		shape.setStroke(Color.BLACK);
		shape.setFill(Color.TRANSPARENT);
		return shape;
	}

	private Text drawValue() {
		ViewSuit suit = ViewSuit.parseSuit(this.card.getSuit());
		String value = this.getLetter(this.card.getRank());
		Text text = new Text(value);
		text.setTextOrigin(VPos.TOP);
		text.setFill(suit.getColor());
		text.setFont(Font.font(null, CARD_CORNER_SYMBOL_SIZE));
		return text;
	}

	private Text drawSymbol() {
		ViewSuit suit = ViewSuit.parseSuit(this.card.getSuit());
		String symbol = suit.getSymbol();
		Text text = new Text(symbol);
		text.setTextOrigin(VPos.TOP);
		text.setFill(suit.getColor());
		text.setFont(Font.font(null, CARD_CORNER_SYMBOL_SIZE));
		return text;
	}

	private static Text createGridSymbol(ViewSuit suit) {
		return createGridSymbol(suit, false);
	}

	private static Text createGridSymbol(ViewSuit suit, boolean rotate) {
		Text text = new Text(suit.getSymbol());
		text.setTextOrigin(VPos.CENTER);
		text.setFill(suit.getColor());
		text.setFont(Font.font(null, CARD_GRID_SYMBOL_SIZE));
		if (rotate) {
			text.setRotate(180);
		}
		GridPane.setHalignment(text, HPos.CENTER);
		GridPane.setHgrow(text, Priority.ALWAYS);
		GridPane.setValignment(text, VPos.CENTER);
		GridPane.setVgrow(text, Priority.ALWAYS);
		return text;
	}

}
