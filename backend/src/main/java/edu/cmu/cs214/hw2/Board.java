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
        // TODO: Implement this method
    }

    public Board(Space[] space) {
        // TODO: Implement this method
    }
    /**
     * Initialize the board with empty spaces
     */
    public void initializeBoard() {
        // TODO: Implement this method
    }

    /**
     * Get available moves for a worker
     * @param worker The worker to check moves for
     * @return List of spaces the worker can move to
     */
    public List<Space> getAvailableMoves(Worker worker) {
        // TODO: Implement this method
        return null;
    }

    /**
     * Get buildable spaces around a worker
     * @param worker The worker to check buildable spaces for
     * @return List of spaces where the worker can build
     */
    public List<Space> getBuildableSpaces(Worker worker) {
        // TODO: Implement this method
        return null;
    }

    /**
     * Check if the given position is valid on the board
     * @param row The row index
     * @param col The column index
     * @return True if valid, false otherwise
     */
    private boolean isValidPosition(int row, int col) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Get a space at specific coordinates
     * @param row The row index
     * @param col The column index
     * @return The space at the given coordinates, or null if out of bounds
     */
    public Space getSpace(int row, int col) {
        // TODO: Implement this method
        return null;
    }


    public Board updateSpace(Space updatedSpace) {
        // TODO: Implement this method
        return null;
    }

}