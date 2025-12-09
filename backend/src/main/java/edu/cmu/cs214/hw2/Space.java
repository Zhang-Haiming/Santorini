package edu.cmu.cs214.hw2;

/**
 * Represents a single space on the Santorini game board
 */
public class Space {
    private int row;
    private int col;
    private int towerLevel;
    private boolean hasDome;
    private Worker occupied;
    private static final int WINNING_TOWER_LEVEL = 3;

    /**
     * Constructor to initialize a space
     * @param row The row index of the space
     * @param col The column index of the space
     */
    public Space(int row, int col, int towerLevel, boolean hasDome, Worker occupied) {
        // TODO: Implement this method
    }

    public Space(Space other) {
        // TODO: Implement this method
    }

    /**
     * Build a tower level or dome on this space
     */
    public void build() {
        // TODO: Implement this method
    }

    /**
     * Check if this space can be built on
     * @return True if can build, false otherwise
     */
    public boolean canBuild() {
        // TODO: Implement this method
        return false;
    }

    /**
     * Check if this space can be moved to
     * @param worker The worker attempting to move here
     * @return True if can move, false otherwise
     */
    public boolean canMoveTo(Worker worker) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Set the worker occupying this space
     * @param worker The worker to occupy this space
     */
    public void setOccupied(Worker worker) {
        // TODO: Implement this method
    }

    /**
     * Remove the worker from this space
     */
    public void removeWorker() {
        // TODO: Implement this method
    }

    // Getters
    public int getRow() { 
        // TODO: Implement this method
        return 0;
    }
    public int getCol() { 
        // TODO: Implement this method
        return 0;
    }
    public int getTowerLevel() { 
        // TODO: Implement this method
        return 0;
    }
    public boolean hasDome() { 
        // TODO: Implement this method
        return false;
    }
    public Worker getOccupied() { 
        // TODO: Implement this method
        return null;
    }
    public boolean isOccupied() { 
        // TODO: Implement this method
        return false;
    }
}
