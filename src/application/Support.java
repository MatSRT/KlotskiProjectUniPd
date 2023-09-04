package application;

import java.util.Map;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Class of support used to control overlap/out of bound illegal movements and get pieces from the save file
*/

public class Support {

	/**
	 * Method to check if the move is within the bounds of the board
	 * @param newRow
	 * @param newColumn
	 * @param type
	 * @return true if the move doesn't put the piece outside the board, false otherwise
	 */
	
    public static boolean isMoveWithinBounds(int newRow, int newColumn, int type) {
		int maxRows=5;
		int maxColumns=4;
		
		//Define limits to moves based on type of piece
		
		switch(type) {
			case 1:
				maxRows=5;
				maxColumns=3;
				break;
			case 2:
				maxRows=4;
				maxColumns=4;
				break;
			case 3:
				maxRows=4;
				maxColumns=3;
				break;
			case 4:
				break;
			default:
				return false;
				
		}
	    return newRow >= 0 && newRow < maxRows && newColumn >= 0 && newColumn < maxColumns;
	}

    /**
     * Method to check for a piece if it's overlapping another
     * @param pieceToCheck
     * @param newRow
     * @param newColumn
     * @param initialPositions
     * @return true if overlap detected, false otherwise
     */
    
    
	public static boolean checkForOverlap(Rectangle pieceToCheck, int newRow, int newColumn, Map<Rectangle, Integer[]> initialPositions) {
	    int pieceRowCount = (int) Math.ceil(pieceToCheck.getHeight() / 100);
	    int pieceColumnCount = (int) Math.ceil(pieceToCheck.getWidth() / 100);
	    
	    for (Rectangle piece : initialPositions.keySet()) {
	        if (piece != pieceToCheck) {
	            int pieceRow = GridPane.getRowIndex(piece);
	            int pieceColumn = GridPane.getColumnIndex(piece);
	            
	            int otherPieceRowCount = (int) Math.ceil(piece.getHeight() / 100);
	            int otherPieceColumnCount = (int) Math.ceil(piece.getWidth() / 100);
	            
	            if (newRow + pieceRowCount > pieceRow && newRow < pieceRow + otherPieceRowCount &&
	                newColumn + pieceColumnCount > pieceColumn && newColumn < pieceColumn + otherPieceColumnCount) {
	                return true; // Overlap detected
	            }
	        }
	    }
	    
	    return false; // No overlap detected
	}

	/**
	 * Method to verify if the node of the gird pane is empty
	 * @param board
	 * @param row
	 * @param column
	 * @return node of the grid pane if there is a piece, null otherwise
	 */
	
    public static Node getNodeAtPosition(GridPane board, int row, int column) {
        for (Node node : board.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            // Check if the node has row and column indexes set
            if (rowIndex != null && columnIndex != null && rowIndex == row && columnIndex == column) {
                return node;
            }
        }
        return null;
    }
    
    /**
     * Method to check if the player won
     * @param board
     * @param piece
     * @return true if the goal piece (200x200) is in position 3,1, false otherwise
     */
    
    public static boolean isWin(GridPane board, Rectangle piece) {
        int targetRow = 3;
        int targetColumn = 1;

        Integer blockRow = GridPane.getRowIndex(piece);
        Integer blockColumn = GridPane.getColumnIndex(piece);

        if (blockRow != null && blockColumn != null && blockRow == targetRow && blockColumn == targetColumn) {
            return true; // Block is in the target position
        }

        return false; // Block is not in the target position
    }
    
    
}
