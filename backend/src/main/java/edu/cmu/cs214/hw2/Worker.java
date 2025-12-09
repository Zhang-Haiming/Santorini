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
        // TODO: Implement this method
    }

    /**
     * Get the owner of this worker
     * @return The player who owns this worker
     */
    public Player getOwner() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Get the current position of this worker
     * @return The space this worker is currently on
     */
    public Space getPos() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Set the position of this worker (for testing purposes)
     * @param space The space to set as position
     */
    public void setPos(Space space) {
        // TODO: Implement this method
    }

    /**
     * Move this worker to a new space
     * @param newSpace The space to move to
     * @throws IllegalArgumentException if trying to move to the same position
     */
    public void moveTo(Space newSpace) {
        // TODO: Implement this method
    }

    /**
     * Place this worker on a space during initial setup (allows placing on same position)
     * @param newSpace The space to place the worker on
     */
    public void placeOn(Space newSpace) {
        // TODO: Implement this method
    }

    /**
     * Check if this worker can move to the given space
     * @param space The target space
     * @return True if the worker can move to the space, false otherwise
     */
    public boolean canOccupy(Space space) {
        // TODO: Implement this method
        return false;
    }
}
