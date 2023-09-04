package test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import application.Engine;
import application.MoveInfo;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {

    private Map<Rectangle, Integer[]> initialPositions;
    private Map<Rectangle, Integer[]> currentPositions;
    private Stack<MoveInfo> moveHistory;
    private Rectangle[] pieces;
    private GridPane board;
    
    
    @BeforeEach
    void setUp() {
        // Initialize test data, including initialPositions, currentPositions, moveHistory, board and alert
        initialPositions = new HashMap<>();
        currentPositions= new HashMap<>();
        moveHistory = new Stack<>();
        board = new GridPane();
        pieces = new Rectangle[3]; // Create an array of 3 pieces for testing
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = new Rectangle();
        }
        
    }

    /**
     * Test with this scenario:
     * selectedPiece is a small piece in position [1,1]
     * Test try to move it up by one with key W
     * selectedPiece should move without problems
     */
    
    @Test
    void testMovePieceWithLegalMove() {
        // Create a selectedPiece Rectangle
		Rectangle piece1 = new Rectangle(1,1,100,100);
		piece1.setId("piece1");
        Rectangle selectedPiece = piece1;
        initialPositions.put(piece1, new Integer[] {1,1});

        // Set its initial position on the grid
        GridPane.setRowIndex(selectedPiece, 1);
        GridPane.setColumnIndex(selectedPiece, 1);

        // Create a KeyEvent for moving the piece
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);
        // Call the movePiece method and check the result
        Engine result = Engine.movePiece(event, selectedPiece, board, initialPositions, false, moveHistory, 0);

        // Assert that the move was successful (legal) and that the position is updated
        assertTrue(result.isLegal());
        assertEquals(0, GridPane.getRowIndex(selectedPiece));
        assertEquals(1, GridPane.getColumnIndex(selectedPiece)); 
    }
    
    @Test
    void testMovePieceAithLegalMove() {
        // Create a selectedPiece Rectangle
		Rectangle piece1 = new Rectangle(1,1,100,100);
		piece1.setId("piece1");
        Rectangle selectedPiece = piece1;
        initialPositions.put(piece1, new Integer[] {1,1});

        // Set its initial position on the grid
        GridPane.setRowIndex(selectedPiece, 1);
        GridPane.setColumnIndex(selectedPiece, 1);

        // Create a KeyEvent for moving the piece
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false);
        // Call the movePiece method and check the result
        Engine result = Engine.movePiece(event, selectedPiece, board, initialPositions, false, moveHistory, 0);

        // Assert that the move was successful (legal) and that the position is updated
        assertTrue(result.isLegal());
        assertEquals(1, GridPane.getRowIndex(selectedPiece));
        assertEquals(0, GridPane.getColumnIndex(selectedPiece)); 
    }
    
    @Test
    void testMovePieceSithLegalMove() {
        // Create a selectedPiece Rectangle
		Rectangle piece1 = new Rectangle(1,1,100,100);
		piece1.setId("piece1");
        Rectangle selectedPiece = piece1;
        initialPositions.put(piece1, new Integer[] {1,1});

        // Set its initial position on the grid
        GridPane.setRowIndex(selectedPiece, 1);
        GridPane.setColumnIndex(selectedPiece, 1);

        // Create a KeyEvent for moving the piece
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.S, false, false, false, false);
        // Call the movePiece method and check the result
        Engine result = Engine.movePiece(event, selectedPiece, board, initialPositions, false, moveHistory, 0);

        // Assert that the move was successful (legal) and that the position is updated
        assertTrue(result.isLegal());
        assertEquals(2, GridPane.getRowIndex(selectedPiece));
        assertEquals(1, GridPane.getColumnIndex(selectedPiece)); 
    }
    
    @Test
    void testMovePieceDithLegalMove() {
        // Create a selectedPiece Rectangle
		Rectangle piece1 = new Rectangle(1,1,100,100);
		piece1.setId("piece1");
        Rectangle selectedPiece = piece1;
        initialPositions.put(piece1, new Integer[] {1,1});

        // Set its initial position on the grid
        GridPane.setRowIndex(selectedPiece, 1);
        GridPane.setColumnIndex(selectedPiece, 1);

        // Create a KeyEvent for moving the piece
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.D, false, false, false, false);
        // Call the movePiece method and check the result
        Engine result = Engine.movePiece(event, selectedPiece, board, initialPositions, false, moveHistory, 0);

        // Assert that the move was successful (legal) and that the position is updated
        assertTrue(result.isLegal());
        assertEquals(1, GridPane.getRowIndex(selectedPiece));
        assertEquals(2, GridPane.getColumnIndex(selectedPiece)); 
    }
    
    @Test
    void testMovePieceQithLegalMove() {
        // Create a selectedPiece Rectangle
		Rectangle piece1 = new Rectangle(1,1,100,100);
		piece1.setId("piece1");
        Rectangle selectedPiece = piece1;
        initialPositions.put(piece1, new Integer[] {1,1});

        // Set its initial position on the grid
        GridPane.setRowIndex(selectedPiece, 1);
        GridPane.setColumnIndex(selectedPiece, 1);

        // Create a KeyEvent for moving the piece
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.Q, false, false, false, false);
        // Call the movePiece method and check the result
        Engine result = Engine.movePiece(event, selectedPiece, board, initialPositions, false, moveHistory, 0);

        // Assert that the move was successful (legal) and that the position is updated
        assertFalse(result.isLegal());
 
    }

    /**
     * Test with this scenario:
     * selectedPiece is a small piece in position [0,0]
     * Test try to move it up by one with key W
     * selectedPiece should not move because the move is out of the grid bounds
     */
    
    @Test
    void testMovePieceWithIllegalMove() {
        // Create a selectedPiece Rectangle 
        Rectangle selectedPiece = new Rectangle(100, 100);

        // Set its initial position on the grid
        GridPane.setRowIndex(selectedPiece, 0);
        GridPane.setColumnIndex(selectedPiece, 0);

        // Create a KeyEvent for moving the piece
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);

        // Call the movePiece method and check the result
        Engine result = Engine.movePiece(event, selectedPiece, board, initialPositions, false, moveHistory, 0);

        // Assert that the move was illegal, and the position remains unchanged
        assertFalse(result.isLegal());
        assertEquals(0, GridPane.getRowIndex(selectedPiece)); 
        assertEquals(0, GridPane.getColumnIndex(selectedPiece));
    }

    /**
     * Test with this scenario:
     * Two pieces in two different positions stored in map initialPositions
     * Positions are changed
     * Reset method should bring the pieces to their initial position
     */
    
    @Test
    void testReset() {
        // Create Rectangle objects and add them to the board with initial positions
        Rectangle piece1 = new Rectangle();
        Rectangle piece2 = new Rectangle();
        
        // Set initial positions
        Integer[] initialPosition1 = {1, 1};
        Integer[] initialPosition2 = {2, 2};
        
        initialPositions.put(piece1, initialPosition1);
        initialPositions.put(piece2, initialPosition2);
        
        GridPane.setRowIndex(piece1, 3);
        GridPane.setColumnIndex(piece1, 3);
        GridPane.setRowIndex(piece2, 4);
        GridPane.setColumnIndex(piece2, 4);

        // Call the reset method
        Engine.reset(initialPositions);

        // Check if the positions have been reset correctly
        assertEquals(initialPosition1[0], GridPane.getRowIndex(piece1));
        assertEquals(initialPosition1[1], GridPane.getColumnIndex(piece1));
        assertEquals(initialPosition2[0], GridPane.getRowIndex(piece2));
        assertEquals(initialPosition2[1], GridPane.getColumnIndex(piece2));
    }
    
    /**
     * Test to simulate scenario:
     * moveHistory not empty
     * undo should restore the previous position of the piece
     */
    
    @Test
    void testUndoWithNonEmptyMoveHistory() {
        // Create a Rectangle object and set its initial position on the board
        Rectangle piece = new Rectangle();
        GridPane.setRowIndex(piece, 1);
        GridPane.setColumnIndex(piece, 1);

        // Create a MoveInfo object representing a move
        MoveInfo moveInfo = new MoveInfo(piece, 2, 2);

        // Push the move onto the moveHistory stack
        moveHistory.push(moveInfo);

        // Call the undo method
        Engine.undo(moveHistory);

        // Check if the undo correctly restores the previous position
        assertEquals(2, GridPane.getRowIndex(piece));
        assertEquals(2, GridPane.getColumnIndex(piece));
        assertTrue(moveHistory.isEmpty()); // Ensure the move is removed from the history
    }

    /**
     * Test to simulate this scenario:
     * moveHistory empty
     * undo should have no effect on the position of the piece
     */
    
    @Test
    void testUndoWithEmptyMoveHistory() {
        // Create a Rectangle object and set its initial position on the board
        Rectangle piece = new Rectangle();
        GridPane.setRowIndex(piece, 1);
        GridPane.setColumnIndex(piece, 1);

        // Call the undo method
        Engine.undo(moveHistory);

        // Check if the undo does not change the position and moveHistory remains empty
        assertEquals(1, GridPane.getRowIndex(piece));
        assertEquals(1, GridPane.getColumnIndex(piece));
        assertTrue(moveHistory.isEmpty());
    }
    
    @Test
    void testSave() throws IOException {
        // Create test data for currentPositions
		Rectangle piece1 = new Rectangle(100,100);
		piece1.setId("piece1");
		Rectangle piece2 = new Rectangle(100,100);
		piece2.setId("piece2");
		currentPositions.put(piece1, new Integer[] {1,2});
		currentPositions.put(piece2, new Integer[] {3,3});
        
        // Define the real directory where the file will be created
        String currentDirectory = System.getProperty("user.dir");
        String directoryPath = currentDirectory + File.separator + "src" + File.separator + "Save-Config" + File.separator; 

        // Call the save method
        Engine.save(board, currentPositions, 42);

        // Construct the file path to check if the file exists
        Path savedFilePath = Paths.get(directoryPath, "board_state.json");
        File savedFile = savedFilePath.toFile();

        // Verify that the JSON file was created
        assertTrue(savedFile.exists());

        // Read the JSON content from the saved file
        JsonObject jsonObject = readJsonFromFile(savedFile);

        // Verify the content of the saved JSON
        assertEquals(42, jsonObject.get("numberOfMoves").getAsInt());
        JsonObject initialPositionsObject = jsonObject.getAsJsonObject("initialPositions");
        assertEquals(2, initialPositionsObject.get(piece1.getId()).getAsJsonObject().get("x").getAsInt());
        assertEquals(1, initialPositionsObject.get(piece1.getId()).getAsJsonObject().get("y").getAsInt());
        assertEquals(3, initialPositionsObject.get(piece2.getId()).getAsJsonObject().get("x").getAsInt());
        assertEquals(3, initialPositionsObject.get(piece2.getId()).getAsJsonObject().get("y").getAsInt());
    }

    @Test
    public void testLoad() throws IOException {
        String jsonFilePath = "src/Save-Config/board_stateEx.json";
        
		Rectangle piece1 = new Rectangle(100,200);
		piece1.setId("piece1");
		Rectangle piece2 = new Rectangle(100,200);
		piece2.setId("piece2");
		
		initialPositions.put(piece1, new Integer[] {1,2});
		initialPositions.put(piece2, new Integer[] {3,3});
		
		// Call the load method
		int numberOfMoves = Engine.load(jsonFilePath, board, initialPositions);

		// Assert the expected values
		assertEquals(42, numberOfMoves);

		// Assert the positions of pieces loaded from the JSON
		assertEquals(0, (int) initialPositions.get(piece1)[0]);
		assertEquals(0, (int) initialPositions.get(piece1)[1]);
		assertEquals(2, (int) initialPositions.get(piece2)[0]);
		assertEquals(0, (int) initialPositions.get(piece2)[1]);

    }

    @Test
    public void testLoadConfiguration() throws IOException {
        String jsonFilePath = "src/Save-Config/config1.json";
        
		Rectangle piece1 = new Rectangle(100,200);
		piece1.setId("piece1");
		Rectangle piece2 = new Rectangle(100,100);
		piece2.setId("piece2");
		
		initialPositions.put(piece1, new Integer[] {0,0});
		initialPositions.put(piece2, new Integer[] {0,3});
		
		//call the loadConfiguration method
		
		Engine.loadConfiguration(jsonFilePath, board, initialPositions);

		// Assert the positions of pieces loaded from the JSON
		assertEquals(0, (int) initialPositions.get(piece1)[0]);
		assertEquals(0, (int) initialPositions.get(piece1)[1]);
		assertEquals(0, (int) initialPositions.get(piece2)[0]);
		assertEquals(3, (int) initialPositions.get(piece2)[1]);

    }
    
    //Helper method to read form json File
    private JsonObject readJsonFromFile(File file) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }
        return JsonParser.parseString(jsonContent.toString()).getAsJsonObject();
    }
    

}

