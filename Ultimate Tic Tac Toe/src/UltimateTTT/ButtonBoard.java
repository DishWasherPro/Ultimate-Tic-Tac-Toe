package UltimateTTT;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
/**
 * This class creates a 3 by 3 board made of buttons, which is then showed off in the GUIDriver. 
 * This class allows users to place their pieces, and keeps track of the rules of ultimate tic-tac-toe.
 * @author 334808300
 *
 */
public class ButtonBoard {
	static String[][] mainBoard = new String[3][3]; // keeps track of small boards
	static boolean[] smallBoardWon = new boolean[9]; // keeps track of won positions
	static String playerTurn = "X";

	private Button[][] board = new Button[3][3]; // the buttons
	private String[][] buttonState = new String[3][3]; // X or O state of buttons
	private int boardNum;// counts boards
	private boolean[][] takenPosition = new boolean[3][3]; // keeps track of player claimed buttons
	private int positionCount; // counts buttons in boards

	// keeps track of each small board
	private ButtonBoard[][] eachBoard;

	/**
	 * Creates button boards with 3 by 3 buttons and sets all
	 * 
	 * @param boardNum
	 * @param eachBoard
	 */
	public ButtonBoard(int boardNum, ButtonBoard[][] eachBoard) {
		this.boardNum = boardNum;
		this.eachBoard = eachBoard;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// creating board and filling empty spaces in arrays
				board[i][j] = new Button();
				board[i][j].setMinSize(47, 47);
				board[i][j].setStyle("-fx-background-color: #0a640a");
				mainBoard[i][j] = " ";
				buttonState[i][j] = " ";
			}
		}
	}

	/**
	 * The core of the buttons, keeping track of who's claiming what position and if
	 * they're winning or not. Also where all actions for buttons are activated
	 * Checks after every press
	 */
	public void checkPressed() {
		Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 18);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int column = i;
				int row = j;
				Button currentButton = board[i][j];
				// Triggers when any buttons pressed
				board[i][j].setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// places player piece on a separate array and checks it off as taken square
						buttonState[row][column] = playerTurn;
						takenPosition[row][column] = true;
						positionCount++;

						// setting button text
						currentButton.setText(playerTurn);
						currentButton.setFont(font);
						// changing X and O colours
						if (playerTurn == "O") {
							currentButton.setStyle("-fx-background-color: #0789f2");
						} else {
							currentButton.setStyle("-fx-background-color: #fa0718");
						}

						// disables all buttons in each board
						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 3; j++) {
								eachBoard[i][j].disableBoard();
							}
						}
						// checks if board has ended in a tie
						if (positionCount == 9) {
							smallBoardWon[boardNum] = true; // makes board won with no winner
						}

						// Checks if selected board is won
						if (eachBoard[column][row].isWon()) {
							for (int i = 0; i < 3; i++) {
								for (int j = 0; j < 3; j++) {
									eachBoard[i][j].enableBoard(); // enables other boards
								}
							}
						} else {
							eachBoard[column][row].enableBoard(); // enables only selected board
						}

						// Winning a board
						if (checkWin(playerTurn, buttonState)) {
							// sets the boolean of the board won state to won
							smallBoardWon[boardNum] = true;

							// Converting array values to 2d array values using math
							mainBoard[boardNum / 3][boardNum % 3] = playerTurn;

							for (int i = 0; i < 3; i++) {
								for (int j = 0; j < 3; j++) {
									board[i][j].setDisable(true); // disables all buttons in board in won board
									takenPosition[i][j] = true; // sets all buttons to taken to prevent being enabled

									if (playerTurn.equals("X")) {
										board[i][j].setStyle("-fx-background-color: #fa0718");
									} else {
										board[i][j].setStyle("-fx-background-color: #0789f2");
									}

								}
							}
							// checking if specific board is won
							if (eachBoard[column][row].isWon()) {
								for (int i = 0; i < 3; i++) {
									for (int j = 0; j < 3; j++) {
										eachBoard[i][j].enableBoard(); // enables all not won boards
									}
								}
							}

							// checking big board win
							if (checkWin(playerTurn, mainBoard)) {
								for (int i = 0; i < 3; i++) {
									for (int j = 0; j < 3; j++) {
										eachBoard[i][j].disableBoard();// disables all boards to end game
									}
								}

							}

						}

						// player alternating
						if (playerTurn.equals("X")) {
							playerTurn = "O";
						} else {
							playerTurn = "X";
						}

						currentButton.setDisable(true); // disables clicked button
					}
				});
			}
		}
	}

	/**
	 * returns buttons for GUIDriver to display and edit
	 * 
	 * @return
	 */
	public Button[][] returnButtons() {
		return board;
	}

	/**
	 * checks if a board has a winning position for inputed letter
	 * 
	 * @param letter
	 * @param boardPosition
	 * @return
	 */
	public boolean checkWin(String letter, String[][] boardPosition) {
		int countRow;
		int countColumn;

		for (int i = 0; i < 3; i++) {
			countRow = 0; // count to check row won boards
			countColumn = 0; // count to check column won boards
			for (int row = 0; row < 3; row++) {
				if (boardPosition[row][i].equals(letter)) { // goes through each row
					countRow++;
				}
			}

			for (int column = 0; column < 3; column++) {
				if (boardPosition[i][column].equals(letter)) { // goes through each column
					countColumn++;
				}
			}

			if (countRow == 3 || countColumn == 3) { // checks for 3 claimed spots
				return true;
			}

		}
		// if statements to check the diagonals
		if (boardPosition[1][1].equals(letter)) {
			if (boardPosition[0][0].equals(letter) && boardPosition[2][2].equals(letter)) {
				return true;
			}
			if (boardPosition[2][0].equals(letter) && boardPosition[0][2].equals(letter)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * disables ALL the boards
	 */
	public void disableBoard() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				board[row][column].setDisable(true);
				// Changing colours back
				if (!takenPosition[column][row]) { // checks if button has been claimed by player
					board[row][column].setStyle("-fx-background-color: #035636");
				}

			}
		}
	}

	/**
	 * reactivates all the buttons in a SMALL BOARD except the ones that have been
	 * claimed
	 */
	public void enableBoard() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (!takenPosition[column][row]) { // checks if player has claimed the button
					board[row][column].setDisable(false);
					board[row][column].setStyle("-fx-background-color: #0a640a");
				}
			}
		}
	}

	/**
	 * returns if THIS board is claimed by a player
	 * 
	 * @return
	 */
	public boolean isWon() {
		return smallBoardWon[boardNum];
	}

}
