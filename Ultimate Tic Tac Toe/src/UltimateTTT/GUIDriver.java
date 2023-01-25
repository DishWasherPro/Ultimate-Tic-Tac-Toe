package UltimateTTT;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Ultimate TicTacToe
 * There are nine TicTacToe boards put inside each box of a larger tic tac toe board.
 * The game is played inside the smaller TicTacToe boards, and winning them will claim that board
 * To prevent ties, rules are implemented to shift the opposing player depending on moves played.
 * 
 * @author Group from ICS4U
 *
 */
public class GUIDriver extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// For each set of buttons
    	GridPane bigBoard = new GridPane();
    	
    	// Gaps for boards
    	bigBoard.setVgap(20);
    	bigBoard.setHgap(20);
    	// Keeping track of which board is pressed
    	ButtonBoard[][] boardTracker = new ButtonBoard[3][3];
    	int count = 0;
    	// creating 9 small boards. Row by column because the boards were reversed
    	for(int bigBoardRow = 0; bigBoardRow < 3; bigBoardRow++) {
    		for(int bigBoardColumn = 0; bigBoardColumn < 3; bigBoardColumn++) {
    			
    			// Creating individual small board
        		GridPane smallBoard = new GridPane();
        		
        		// Creating the buttons in the class
        		ButtonBoard currentSmallBoard = new ButtonBoard(count, boardTracker);
        		// Keep track of which small board is being pressed
        		boardTracker[bigBoardColumn][bigBoardRow] = currentSmallBoard;
        		
        		Button[][] tempButtons = currentSmallBoard.returnButtons();       		
        		
        		// Adding the buttons to the small board
        		for (int smallBoardColumn = 0; smallBoardColumn < 3; smallBoardColumn++) {
        			for (int smallBoardRow = 0; smallBoardRow < 3; smallBoardRow++) {
        				tempButtons[smallBoardColumn][smallBoardRow].setMinSize(40, 40);
        				tempButtons[smallBoardColumn][smallBoardRow].setMaxSize(40, 40);
        				GridPane.setConstraints(tempButtons[smallBoardColumn][smallBoardRow], smallBoardColumn + 1, smallBoardRow + 1);
            			smallBoard.getChildren().add(tempButtons[smallBoardColumn][smallBoardRow]);			
            		}
        		}
        		// gap for buttons
        		smallBoard.setVgap(20);
    			smallBoard.setHgap(20);
    			
        		GridPane.setConstraints(smallBoard, bigBoardColumn, bigBoardRow);
        		bigBoard.getChildren().add(smallBoard);
        		count++;
        	}
    	}
    	
    	for (int column = 0; column < 3; column ++) {
    		for (int row = 0; row < 3; row ++) {
    			boardTracker[column][row].checkPressed();
    		}
    	}
    	
    	
    	// main group
        Group groupScene = new Group();
        // For Background Board
        Image image = new Image("tictactoe_main.png");
        ImageView board = new ImageView(image);
        
        primaryStage.setTitle("UTTT");

        
        // adding background image
        groupScene.getChildren().addAll(board, bigBoard);
        
        primaryStage.setScene(new Scene(groupScene, 600, 600));
        primaryStage.show();
    }
}
