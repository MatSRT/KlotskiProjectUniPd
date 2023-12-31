package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



/**
 *  Controller class to interrogate the javaFX GUI
 *	Implements move counter, a map to store the initial positions and a stack used to track moves history
 *	Rectangle objects are used since there is no need to extend it to describe the pieces of the puzzle
 */

public class Controller implements Initializable {
	
	private int moves=0; // Move counter. Default value: 0
	private int loadedMoves=0; // Move Counter loaded from save file
	private boolean legal; // Boolean value used to check if the move is either valid (legal) or not
	private boolean isLoad; //Boolean value used to update move counter if reset after load
    private Stage stage;
    
    private Map<Rectangle, Integer[]> initialPositions = new HashMap<>(); // Map to store initial positions
    private Map<Rectangle, Integer[]> currentPositions = new HashMap<>(); // Map to store current positions of every piece in order to be able to save/load the board
    private Stack<MoveInfo> moveHistory = new Stack<>(); // Stack to store moves history
    private Engine result; // Engine object used to get the result of the movement action of a piece

	
    private Rectangle selectedPiece; // Rectangle object to identify the piece selected with mouse
	public Rectangle[] pieces; //Array used to handle all the Rectangle objects on the board
	
    /**
     * Elements injected from FXML file.
     */
	
	@FXML
	public AnchorPane anchorPane;
    @FXML
    private GridPane board;
    
	@FXML
	private Rectangle piece1;
	@FXML
	private Rectangle piece2;
	@FXML
	private Rectangle piece3;
	@FXML
	private Rectangle piece4;
	@FXML
	private Rectangle piece5;
	@FXML
	private Rectangle piece6;
	@FXML
	private Rectangle piece7;
	@FXML
	private Rectangle piece8;
	@FXML
	private Rectangle piece9;
	@FXML
	private Rectangle piece10;
	
	@FXML
	private Label MoveCounter;
	
	
	
	/**
	 * Method to initialize the controller
	 * Add the EventHandler to the anchor pane and save the initial position for all the pieces in order to be able to reset the board
	 */
	
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandler);
        
        pieces = new Rectangle[]{piece1, piece2, piece3, piece4,piece5,piece6,piece7,piece8,piece9,piece10};
        
        // Store initial positions for pieces
        for (Rectangle piece : pieces) {
        	initialPositions.put(piece, new Integer[]{GridPane.getRowIndex(piece), GridPane.getColumnIndex(piece)});
        	Integer[] initialPosition = initialPositions.get(piece);
            currentPositions.put(piece, initialPosition.clone());
        }
        // Attach event handlers to the pieces
        for (Rectangle piece : pieces) {
        	piece.setOnMouseClicked(this::pieceClicked);
        }
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Method to highlight the piece selected on the board with the mouse
     * @param event: register the click of the mouse on a piece
     */
    private void pieceClicked(MouseEvent event) {
    	if (selectedPiece != null) {
            selectedPiece.setStroke(Color.BLACK); // Reset the stroke color of previously selected piece
            
        }

        selectedPiece = (Rectangle) event.getSource();
        selectedPiece.setStroke(Color.YELLOW); // Highlight the selected piece
    }
    
    /**
     * Lambda expression to move the selected piece calling the Engine method "move"
     * If the move is legal (no out of bound and no overlap with other pieces)
     * the selected piece is moved and currentPositions is updated in order to be able to save the game
     * moveHistory is updated in order to be able to undo the move
     */
    
    public EventHandler<KeyEvent> keyEventHandler = event -> {
        result = Engine.movePiece(event, selectedPiece, board, initialPositions, legal, moveHistory, moves);
        legal = result.isLegal();

        if (legal) {
            moves++;
            MoveCounter.setText("Moves: " + moves);
            
            //store the position of every piece
            for (Rectangle piece : pieces) {
                int pieceRow = GridPane.getRowIndex(piece);
                int pieceColumn = GridPane.getColumnIndex(piece);
                currentPositions.put(piece, new Integer[]{pieceRow, pieceColumn});
            }
        }
        
        //checking if the player won the game
        if (selectedPiece.getWidth() == 200 && selectedPiece.getHeight() == 200) { 
            if(Support.isWin(board, selectedPiece)) { 
            	Alert win=new Alert(AlertType.CONFIRMATION);
            	win.setTitle("WIN");
            	win.setHeaderText("You won, game now will restart");
            	win.setContentText("Do you want to play another game?");
            	win.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            	if(win.showAndWait().get()==ButtonType.YES){
            		Engine.reset(initialPositions);
            		moves=0;
            		MoveCounter.setText("Moves: "+moves);
            	}else {
            		Alert ex=new Alert(AlertType.INFORMATION);
            		ex.setHeaderText("Exit");
            		ex.setContentText("Game will now exit. Thank you for playing");
            		ex.showAndWait();
            		stage.close();
            	}
            	
            }
        }
    };
    
    /**
     * Method to call the reset of the board
     * reset the move counter to zero
     * @param e ActionEvent when button "Reset" is pressed
     */
    
    public void reset(ActionEvent e) {
    	Engine.reset(initialPositions);
    	moveHistory.clear();
    	if(isLoad==true) {
    		moves=loadedMoves;
    	}else {
    		moves=0;
    	}
		MoveCounter.setText("Moves: "+moves);
	}
    
    /**
     * Method to handle the interaction between the undo button and the undo method in Engine class
     * move counter get decremented by one
     * if there is no move to undo the controller will show an alert on screen
     * @param e ActionEvent when button "undo" is pressed
     */
	
	public void undo(ActionEvent e) {

		if(!moveHistory.isEmpty()&&moves>loadedMoves) {
			Engine.undo(moveHistory);
			moves--;
			MoveCounter.setText("Moves: " + moves);
		}else {
			Alert warning=new Alert(AlertType.INFORMATION);
			warning.setTitle("Warning");
			warning.setHeaderText("Warning");
			warning.setContentText("There is no move to undo");
			warning.showAndWait();
		}
	}
    /**
     * Method to handle the interaction between the NEXT BEST MOVE button and the solver method
     * Solver can solve only the default configuration saving all the moves in Soluzioni.txt
     * @param e ActionEvent when button "undo" is pressed
     */
	public void nbm(ActionEvent e) {
		try {
			Loader.solvePuzzle();
		} catch (IOException | InterruptedException e1) {
			
			e1.printStackTrace();
		}
		Alert warning=new Alert(AlertType.INFORMATION);
		warning.setTitle("Solution");
		warning.setHeaderText("Solution");
		warning.setContentText("Solution for default configuration generated in file Soluzioni.txt");
		warning.showAndWait();
	}
	
	/**
	 * Method to handle the interaction between the save button and the save method of Engine class
	 * @param e ActionEvent when button "save" is pressed
	 */
	
	public void save(ActionEvent e) {
		Engine.save(board, currentPositions, moves);
        // Alert to signal to the player that the game has been saved
		Alert saved=new Alert(AlertType.INFORMATION);
		saved.setTitle("Saving");
		saved.setHeaderText("Saving");
		saved.setContentText("Game saved in file board_state.json");
		saved.showAndWait();
	}
	
	/**
	 * Method to handle the interaction between the load button and the load method of Engine class
	 * move counter is set based on the move counter saved on the save file
	 * @param e ActionEvent when button "load" is pressed
	 */
	
	public void load(ActionEvent e) {
		isLoad=true;
		moves=Engine.load("src/Save-Config/board_state.json", board, initialPositions);
		loadedMoves=moves;
		MoveCounter.setText("Moves: "+moves);
	}
	
	/**
	 * Method to exit the game with "exit" button
	 * A pop up alert is shown to inform the player that the game will close
	 * @param e ActionEvent when button "exit" is pressed
	 */
	
	public void exit(ActionEvent e) {
		Alert ex=new Alert(AlertType.CONFIRMATION);
		ex.setHeaderText("Exit");
		ex.setContentText("Are you sure you want to exit?");
		if(ex.showAndWait().get()==ButtonType.OK) {
			stage.close();
		}
	}
	
	/**
	 * Method to handle the interaction between the config1 button and the loadConfiguration method of Engine class
	 * @param e ActionEvent when button "1" is pressed
	 */
	
	public void Config1(ActionEvent e) {
		
		//new game started
		moves=0;
		isLoad=false;
		loadedMoves=0;
		MoveCounter.setText("Moves: "+moves);
		Engine.loadConfiguration("src/Save-Config/config1.json", board, initialPositions);
		Alert config=new Alert(AlertType.INFORMATION);
		config.setTitle("Loading Configuration");
		config.setHeaderText("Configuration loaded");
		config.setContentText("Configuration loaded from file, good game");
		config.showAndWait();
	}
	
	/**
	 * Method to handle the interaction between the config2 button and the loadConfiguration method of Engine class
	 * @param e ActionEvent when button "2" is pressed
	 */
	public void Config2(ActionEvent e) {
		
		//new game started
		moves=0;
		isLoad=false;
		loadedMoves=0;
		MoveCounter.setText("Moves: "+moves);
		Engine.loadConfiguration("src/Save-Config/config2.json", board, initialPositions);
		Alert config=new Alert(AlertType.INFORMATION);
		config.setTitle("Loading Configuration");
		config.setHeaderText("Configuration loaded");
		config.setContentText("Configuration loaded from file, good game");
		config.showAndWait();
	}
	
	/**
	 * Method to handle the interaction between the config3 button and the loadConfiguration method of Engine class
	 * @param e ActionEvent when button "3" is pressed
	 */
	
	public void Config3(ActionEvent e) {
		
		//new game started
		moves=0;
		isLoad=false;
		loadedMoves=0;
		MoveCounter.setText("Moves: "+moves);
		Engine.loadConfiguration("src/Save-Config/config3.json", board, initialPositions);
		Alert config=new Alert(AlertType.INFORMATION);
		config.setTitle("Loading Configuration");
		config.setHeaderText("Configuration loaded");
		config.setContentText("Configuration loaded from file, good game");
		config.showAndWait();
	}
	
	/**
	 * Method to handle the interaction between the Default button and the loadConfiguration method of Engine class
	 * @param e ActionEvent when button "Default" is pressed
	 */
	
	public void Default(ActionEvent e) {
		
		//new game started
		moves=0;
		isLoad=false;
		loadedMoves=0;
		MoveCounter.setText("Moves: "+moves);
		Engine.loadConfiguration("src/Save-Config/defConfig.json", board, initialPositions);
		Alert config=new Alert(AlertType.INFORMATION);
		config.setTitle("Loading Configuration");
		config.setHeaderText("Configuration loaded");
		config.setContentText("Configuration loaded from file, good game");
		config.showAndWait();
	}

}
