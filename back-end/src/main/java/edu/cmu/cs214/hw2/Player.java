package edu.cmu.cs214.hw2;

/**
 * Represents a player in the Santorini game
 */
public class Player {
    private Worker[] workers;
    private String name;
    private int id;

    /**
     * Constructor to create a player
     * @param name The name of the player
     * @param id The ID of the player
     */
    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.workers = new Worker[2];
        // Each player starts with 2 workers
        workers[0] = new Worker(this);
        workers[1] = new Worker(this);
    }

    /**
     * Get the player's name
     * @return The name of the player
     */
    public String getPlayer() {
        return name;
    }

    /**
     * Get the list of workers belonging to this player
     * @return List of workers
     */
    public Worker[] getWorkers() {
        return workers;
    }

    /**
     * Select a specific worker by index
     * @param index The index of the worker to select
     * @return The selected worker or null if index is invalid
     */
    public Worker selectWorker(int index) {
        if (index >= 0 && index < workers.length) {
            return workers[index];
        }
        return null;
    }
    /**
     * Get the player's ID
     * @return The ID of the player
     */
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
