package edu.cmu.cs214.hw2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the game board for Santorini
 */
public class Board {
    private Space[] space;
    private static final int BOARD_SIZE = 5;

    /**
     * Constructor to create a board
     */
    public Board() {
        initializeBoard();
    }

    public Board(Space[] space) {
        this.space = Arrays.copyOf(space, BOARD_SIZE * BOARD_SIZE);
    }
    /**
     * Initialize the board with empty spaces
     */
    public void initializeBoard() {
        space = new Space[BOARD_SIZE * BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                space[row * BOARD_SIZE + col] = new Space(
                    row, 
                    col,
                    0,
                    false,
                    null);
            }
        }
    }

    /**
     * Get available moves for a worker
     * @param worker The worker to check moves for
     * @return List of spaces the worker can move to
     */
    public List<Space> getAvailableMoves(Worker worker) {
        List<Space> availableMoves = new ArrayList<>();
        Space currentPos = worker.getPos();
        
        if (currentPos == null) {
            return availableMoves;
        }

        int currentRow = currentPos.getRow();
        int currentCol = currentPos.getCol();

        // Check all adjacent spaces (8 directions)
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue; // Skip current position
                }

                int newRow = currentRow + rowOffset;
                int newCol = currentCol + colOffset;

                if (isValidPosition(newRow, newCol)) {
                    Space targetSpace = space[newRow * BOARD_SIZE + newCol];
                    if (worker.canOccupy(targetSpace)) {
                        availableMoves.add(targetSpace);
                    }
                }
            }
        }

        return availableMoves;
    }

    /**
     * Get buildable spaces around a worker
     * @param worker The worker to check buildable spaces for
     * @return List of spaces where the worker can build
     */
    public List<Space> getBuildableSpaces(Worker worker) {
        List<Space> buildableSpaces = new ArrayList<>();
        Space currentPos = worker.getPos();
        
        if (currentPos == null) {
            return buildableSpaces;
        }

        int currentRow = currentPos.getRow();
        int currentCol = currentPos.getCol();

        // Check all adjacent spaces (8 directions)
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue; // Skip current position
                }

                int newRow = currentRow + rowOffset;
                int newCol = currentCol + colOffset;

                if (isValidPosition(newRow, newCol)) {
                    Space targetSpace = space[newRow * BOARD_SIZE + newCol];
                    if (targetSpace.canBuild()) {
                        buildableSpaces.add(targetSpace);
                    }
                }
            }
        }

        return buildableSpaces;
    }

    /**
     * Check if the given position is valid on the board
     * @param row The row index
     * @param col The column index
     * @return True if valid, false otherwise
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    /**
     * Get a space at specific coordinates
     * @param row The row index
     * @param col The column index
     * @return The space at the given coordinates, or null if out of bounds
     */
    public Space getSpace(int row, int col) {
        if (isValidPosition(row, col)) {
            return this.space[row * BOARD_SIZE + col];
        }
        return null;
    }

    /**
     * Get all unoccupied spaces on the board
     * @return List of unoccupied spaces
     */
    public List<Space> getUnoccupiedSpaces() {
        List<Space> unoccupied = new ArrayList<>();
        for (Space s : space) {
            if (!s.isOccupied()) {
                unoccupied.add(s);
            }
        }
        return unoccupied;
    }

    public Board updateSpace(Space updatedSpace) {
        Space[] newSpace = Arrays.copyOf(this.space, BOARD_SIZE * BOARD_SIZE);
        int row=updatedSpace.getRow();
        int col=updatedSpace.getCol();
        newSpace[row * BOARD_SIZE + col] = new Space(updatedSpace);
        return new Board(newSpace);
    }

}