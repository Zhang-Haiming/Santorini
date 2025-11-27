package edu.cmu.cs214.hw2;

/**
 * Represents a worker piece in the Santorini game
 */
public class Worker {
    private Player owner;
    private Space position;

    /**
     * Constructor to create a worker
     * @param owner The player who owns this worker
     */
    public Worker(Player owner) {
        this.owner = owner;
        this.position = null;
    }

    /**
     * Get the owner of this worker
     * @return The player who owns this worker
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Get the current position of this worker
     * @return The space this worker is currently on
     */
    public Space getPos() {
        return position;
    }

    /**
     * Set the position of this worker (for testing purposes)
     * @param space The space to set as position
     */
    public void setPos(Space space) {
        this.position = space;
    }

    /**
     * Move this worker to a new space
     * @param newSpace The space to move to
     * @throws IllegalArgumentException if trying to move to the same position
     */
    public void moveTo(Space newSpace) {
        // Check if trying to move to the same position (not allowed during gameplay)
        if (this.position != null &&  newSpace != null && 
            this.position.getRow() == newSpace.getRow() && 
            this.position.getCol() == newSpace.getCol()) {
            throw new IllegalArgumentException("Worker cannot stay in the same position during a turn");
        }
        if(!canOccupy(newSpace)){
            throw new IllegalArgumentException("Worker cannot move to the specified space");
        }
        // Remove from current position if occupied
        if (this.position != null) {
            this.position.removeWorker();
        }
        
        // Set new position
        this.position = newSpace;
        
        // Occupy the new space
        if (newSpace != null) {
            newSpace.setOccupied(this);
        }
    }

    /**
     * Place this worker on a space during initial setup (allows placing on same position)
     * @param newSpace The space to place the worker on
     */
    public void placeOn(Space newSpace) {
        // Remove from current position if occupied
        if (position != null) {
            position.removeWorker();
        }
        
        // Set new position
        this.position = newSpace;
        
        // Occupy the new space
        if (newSpace != null) {
            newSpace.setOccupied(this);
        }
    }

    /**
     * Check if this worker can move to the given space
     * @param space The target space
     * @return True if the worker can move to the space, false otherwise
     */
    public boolean canOccupy(Space space) {
        if (space == null || position == null) {
            return false;
        }
        
        // Cannot stay in the same position during a turn
        if (space.getRow() == position.getRow() && space.getCol() == position.getCol()) {
            return false;
        }
        
        // Can't move to occupied space
        if (space.isOccupied()) {
            return false;
        }
        
        // Can't move to space with dome
        if (space.hasDome()) {
            return false;
        }
        
        // Can only move up one level
        if (space.getTowerLevel() > position.getTowerLevel() + 1) {
            return false;
        }
        
        // Must be adjacent (within 1 row/col distance)
        int rowDiff = Math.abs(space.getRow() - position.getRow());
        int colDiff = Math.abs(space.getCol() - position.getCol());
        
        return rowDiff <= 1 && colDiff <= 1;
    }
}
