package edu.cmu.cs214.hw2;

import java.util.List;
import edu.cmu.cs214.hw2.godcard.GodCard;
import edu.cmu.cs214.hw2.godcard.DemeterCard;
import edu.cmu.cs214.hw2.godcard.HephaestusCard;
import edu.cmu.cs214.hw2.godcard.PanCard;
import edu.cmu.cs214.hw2.godcard.MinotaurCard;
import edu.cmu.cs214.hw2.godcard.NoGodCard;

/**
 * Main game controller for Santorini
 */
public class Game {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private int winner;
    private TurnPhase currentPhase;
    private Worker selectedWorker;
    private List<Space> available; // for move and build
    private GodCard[] godCards;
    private static final int WINNING_TOWER_LEVEL = 3;
    /**
     * Constructor to create a new game
     */
    public Game() {
        // TODO: Implement this method
    }

    public Game(Board board, Player[] players, int nextPlayerIndex, int winner, TurnPhase nextPhase, Worker selectedWorker, List<Space> available, GodCard[] godCards) {
        // TODO: Implement this method
    }

    /**
     * Initialize players
     */
    public void initialPlayer() {
        // TODO: Implement this method
    }

    public Game chooseGodCard(String godName){
        // TODO: Implement this method
        return null;
    }

    /**
     * Place initial workers on the board
     */
    public Game placeInitialWorker(int playerId, int workerId, int row, int col) {
        // TODO: Implement this method
        return null;
    }

    /**
     * Choose a worker to move
     */
    public Game chooseWorker(int x, int y) {
        // TODO: Implement this method
        return null;
    }

    /**
     * Move the selected worker
     */
    public Game move(int x,int y) {
        // TODO: Implement this method
        return null;
    }

    /**
     * Build a tower after moving
     */
    public Game firstBuildTower(int x, int y) {
        // TODO: Implement this method
        return null;
    }
    public Game secondBuildTower(int x, int y) {
        // TODO: Implement this method
        return null;
    }

    // pass second build
    public Game pass(){
        // TODO: Implement this method
        return null;
    }

    public boolean checkHasMoveableWorker(){
        // TODO: Implement this method
        return false;
    }

    public boolean allowSecondBuild() {
        // TODO: Implement this method
        return false;
    }
    
    public boolean checkWinCondition(int playerId,Space from, Space to){
        // TODO: Implement this method
        return false;
    }

    public List<Space> getAvailableMoves(Board board, Worker worker){
        // TODO: Implement this method
        return null;
    }

    public List<Space> getAvailableBuilds(Board board, Worker worker, Space firstBuild){
        // TODO: Implement this method
        return null;
    }

    // Getters for testing
    public Board getBoard() { 
        // TODO: Implement this method
        return null;
    }
    public Player[] getPlayers() { 
        // TODO: Implement this method
        return null;
    }
    public Worker getWorker() { 
        // TODO: Implement this method
        return null;
    }
    public int getCurrentPlayerIndex() { 
        // TODO: Implement this method
        return 0;
    }
    public TurnPhase getCurrentPhase() { 
        // TODO: Implement this method
        return null;
    }
    public Player getWinner() { 
        // TODO: Implement this method
        return null;
    }
    public List<Space> getAvailable() { 
        // TODO: Implement this method
        return null;
    }
    public GodCard getGodCard(int playerId) {
        // TODO: Implement this method
        return null;
    }
    public GodCard[] getGodCards() {
        // TODO: Implement this method
        return null;
    }
    
}