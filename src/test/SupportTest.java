package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Support;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

class SupportTest {

	private Map<Rectangle, Integer[]> initialPositions;
	private GridPane board;
    private Rectangle piece1;
    private Rectangle piece2;
    private Rectangle piece3;

    @BeforeEach
    void setUp() {
        initialPositions = new HashMap<>();
        piece1 = new Rectangle(100,100);
        piece2 = new Rectangle(100,200);
        piece3 = new Rectangle(200,100);
        piece1.setId("piece1");
        piece2.setId("piece2");
        piece3.setId("piece3");
        GridPane.setRowIndex(piece1, 0);
        GridPane.setColumnIndex(piece1, 0);
        initialPositions.put(piece1, new Integer[]{0, 0});

        GridPane.setRowIndex(piece2, 1);
        GridPane.setColumnIndex(piece2, 1);
        initialPositions.put(piece2, new Integer[]{1, 1});

        GridPane.setRowIndex(piece3, 2);
        GridPane.setColumnIndex(piece3, 2);
        initialPositions.put(piece3, new Integer[]{2, 2});
    }
	
    @Test
    void testIsMoveWithinBoundsForType1() {
        // Test with type 1, where maxRows=5 and maxColumns=3
        assertTrue(Support.isMoveWithinBounds(0, 0, 1));
        assertTrue(Support.isMoveWithinBounds(4, 2, 1));

        // Out of bounds cases
        assertFalse(Support.isMoveWithinBounds(5, 2, 1));
        assertFalse(Support.isMoveWithinBounds(2, 3, 1));
        assertFalse(Support.isMoveWithinBounds(-1, 0, 1));
    }

    @Test
    void testIsMoveWithinBoundsForType2() {
        // Test with type 2, where maxRows=4 and maxColumns=4
        assertTrue(Support.isMoveWithinBounds(0, 0, 2));
        assertTrue(Support.isMoveWithinBounds(3, 3, 2));

        // Out of bounds cases
        assertFalse(Support.isMoveWithinBounds(4, 2, 2));
        assertFalse(Support.isMoveWithinBounds(2, 4, 2));
        assertFalse(Support.isMoveWithinBounds(-1, 0, 2));
    }

    @Test
    void testIsMoveWithinBoundsForType3() {
        // Test with type 3, where maxRows=4 and maxColumns=3
        assertTrue(Support.isMoveWithinBounds(0, 0, 3));
        assertTrue(Support.isMoveWithinBounds(3, 2, 3));

        // Out of bounds cases
        assertFalse(Support.isMoveWithinBounds(4, 2, 3));
        assertFalse(Support.isMoveWithinBounds(2, 3, 3));
        assertFalse(Support.isMoveWithinBounds(-1, 0, 3));
    }

    @Test
    void testIsMoveWithinBoundsForInvalidType() {
        // Test with an invalid type (default case)
        // The method should return false for any input since it's trying to move an illegal piece
        assertFalse(Support.isMoveWithinBounds(0, 0, 0));
        assertFalse(Support.isMoveWithinBounds(3, 3, 0));
        assertFalse(Support.isMoveWithinBounds(5, 2, 0));
        assertFalse(Support.isMoveWithinBounds(2, 4, 0));
        assertFalse(Support.isMoveWithinBounds(-1, 0, 0));
    }

    @Test
    void testCheckForOverlapNoOverlap() {
        Rectangle pieceToCheck = new Rectangle(100, 100);
        GridPane.setRowIndex(pieceToCheck, 3);
        GridPane.setColumnIndex(pieceToCheck, 3);

        assertFalse(Support.checkForOverlap(pieceToCheck, 3, 3, initialPositions));
    }

    @Test
    void testCheckForOverlapFullOverlap() {
        Rectangle pieceToCheck = new Rectangle(150, 150);
        GridPane.setRowIndex(pieceToCheck, 1);
        GridPane.setColumnIndex(pieceToCheck, 1);

        assertTrue(Support.checkForOverlap(pieceToCheck, 1, 1, initialPositions));
    }

    @Test
    void testCheckForOverlapPartialOverlap() {
        Rectangle pieceToCheck = new Rectangle(100, 100);
        GridPane.setRowIndex(pieceToCheck, 1);
        GridPane.setColumnIndex(pieceToCheck, 1);
        assertTrue(Support.checkForOverlap(pieceToCheck, 1, 1, initialPositions));
    }
    
    @BeforeEach
    void setUpNode() {
        // Create a new GridPane for each test
        board = new GridPane();
    }
    
    @Test
    void testGetNodeAtPositionNodeFound() {
        // Create a Rectangle and add it to the GridPane at row 1, column 1
        Rectangle expectedNode = new Rectangle();
        GridPane.setRowIndex(expectedNode, 1);
        GridPane.setColumnIndex(expectedNode, 1);
        board.getChildren().add(expectedNode);

        // Call the getNodeAtPosition method to find the node at row 1, column 1
        Node actualNode = Support.getNodeAtPosition(board, 1, 1);

        // Assert that the expected node is returned
        assertEquals(expectedNode, actualNode);
    }

    @Test
    void testGetNodeAtPositionNodeNotFound() {
        // Call the getNodeAtPosition method when no node is present at row 2, column 2
        Node actualNode = Support.getNodeAtPosition(board, 2, 2);

        // Assert that null is returned, indicating no node was found
        assertNull(actualNode);
    }
    
    @Test
    void testIsWinBlockInTargetPosition() {
        // Create a new GridPane for testing
        GridPane board = new GridPane();

        // Create a Rectangle and add it to the GridPane at the target position
        Rectangle block = new Rectangle();
        GridPane.setRowIndex(block, 3);
        GridPane.setColumnIndex(block, 1);
        board.getChildren().add(block);

        // Call the isWin method to check if the block is in the target position
        boolean result = Support.isWin(board, block);

        // Assert that the method returns true when the block is in the target position
        assertTrue(result);
    }

    @Test
    void testIsWinBlockNotInTargetPosition() {
        // Create a new GridPane for testing
        GridPane board = new GridPane();

        // Create a Rectangle and add it to the GridPane at a different position
        Rectangle block = new Rectangle();
        GridPane.setRowIndex(block, 2);
        GridPane.setColumnIndex(block, 2);
        board.getChildren().add(block);

        // Call the isWin method to check if the block is in the target position
        boolean result = Support.isWin(board, block);

        // Assert that the method returns false when the block is not in the target position
        assertFalse(result);
    }
    
}
