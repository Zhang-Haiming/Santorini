package edu.cmu.cs214.hw2;

import java.util.List;

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
    private static final int WINNING_TOWER_LEVEL = 3;
    /**
     * Constructor to create a new game
     */
    public Game() {
        this.board = new Board();
        this.players = new Player[2];
        this.currentPlayerIndex = 0;
        this.winner=-1;
        this.currentPhase=TurnPhase.PLAYER1_INITIAL_WORKER1;
        this.selectedWorker=null;
        this.available=null;
        initialPlayer();
    }

    public Game(Board board, Player[] players, int nextPlayerIndex, int winner, TurnPhase nextPhase, Worker selectedWorker, List<Space> available) {
        this.board = board;
        this.players = players;
        this.currentPlayerIndex = nextPlayerIndex;
        this.winner = winner;
        this.currentPhase = nextPhase;
        this.selectedWorker = selectedWorker;
        this.available = available;
    }

    /**
     * Initialize players
     */
    public void initialPlayer() {
        this.players[0] = new Player("Artemis", 0);
        this.players[1] = new Player("Demeter", 1);
    }

    /**
     * Place initial workers on the board
     */
    public Game placeInitialWorker(int playerId, int workerId, int row, int col) {
        Worker worker=this.players[playerId].getWorkers()[workerId];
        Space target = new Space(this.board.getSpace(row, col));
        
        // Check if target space is valid and unoccupied
        if (target == null || target.isOccupied()) {
            return this;
        }
        
        worker.placeOn(target);
        TurnPhase nextPhase;
        int nextPlayerIndex;
        switch (this.currentPhase) {
            case TurnPhase.PLAYER1_INITIAL_WORKER1:
                nextPhase = TurnPhase.PLAYER1_INITIAL_WORKER2;
                nextPlayerIndex = this.currentPlayerIndex;
                break;
            case TurnPhase.PLAYER1_INITIAL_WORKER2:
                nextPhase = TurnPhase.PLAYER2_INITIAL_WORKER1;
                nextPlayerIndex = 1 - this.currentPlayerIndex;
                break;
            case TurnPhase.PLAYER2_INITIAL_WORKER1:
                nextPhase = TurnPhase.PLAYER2_INITIAL_WORKER2;
                nextPlayerIndex = this.currentPlayerIndex;
                break;
            case TurnPhase.PLAYER2_INITIAL_WORKER2:
                nextPhase = TurnPhase.CHOOSE_WORKER;
                nextPlayerIndex = 1 - this.currentPlayerIndex;
                break;
            default:
                nextPhase = this.currentPhase;
                nextPlayerIndex = this.currentPlayerIndex;
                break;
        }
        return new Game(this.board.updateSpace(target), this.players, nextPlayerIndex, this.winner, nextPhase, this.selectedWorker,null);
    }

    /**
     * Choose a worker to move
     */
    public Game chooseWorker(int x, int y) {
        Space target = this.board.getSpace(x, y);
        if(target.getOccupied()==null){
            return this;
        }
        else{
            if (!this.checkLoseMoveableWorker(this.players[this.currentPlayerIndex])){
                System.out.println("No moveable workers available!");
                return new Game(this.board, this.players, this.currentPlayerIndex, 1 - this.currentPlayerIndex, TurnPhase.END_GAME, this.selectedWorker,null);
            }
            Worker worker=target.getOccupied();
            
            if(worker.getOwner().getId()!=this.currentPlayerIndex){
                return this;
            }
            List<Space> availableMoves = this.board.getAvailableMoves(worker);
            if (availableMoves.isEmpty()){
                return this;
            }
            return new Game(
                this.board, this.players, this.currentPlayerIndex, 
                this.winner, TurnPhase.MOVE, worker, availableMoves
                );
        }
    }

    /**
     * Move the selected worker
     */
    public Game move(int x,int y) {
        Space srcSpace = new Space(this.selectedWorker.getPos());
        Space destSpace = new Space(this.board.getSpace(x, y));
        try{
            this.selectedWorker.moveTo(destSpace);
        } catch (IllegalArgumentException e) {
            return this;
        }
        srcSpace.removeWorker();
        if(destSpace.getTowerLevel()==WINNING_TOWER_LEVEL){
            return new Game(this.board.updateSpace(srcSpace).updateSpace(destSpace), this.players, this.currentPlayerIndex, this.currentPlayerIndex, TurnPhase.END_GAME, this.selectedWorker, null);
        }
        List<Space> buildableSpaces = this.board.getBuildableSpaces(this.selectedWorker);
        if(buildableSpaces.isEmpty()){
            System.out.println("No buildable spaces available!");
            return new Game(this.board, this.players, this.currentPlayerIndex, 1 - this.currentPlayerIndex, TurnPhase.END_GAME, this.selectedWorker,null);
        }
        return new Game(this.board.updateSpace(srcSpace).updateSpace(destSpace), this.players, this.currentPlayerIndex, this.winner, TurnPhase.BUILD, this.selectedWorker, buildableSpaces);
    }

    /**
     * Build a tower after moving
     */
    public Game buildTower(int x, int y) {
        try{
            Space buildSpace = new Space(this.board.getSpace(x, y));
            buildSpace.build();
            this.board=this.board.updateSpace(buildSpace);
            // check if the game should end
            if(!this.checkLoseMoveableWorker(this.players[1-this.currentPlayerIndex])){
                return new Game(this.board, this.players, 1 - this.currentPlayerIndex, this.currentPlayerIndex, TurnPhase.END_GAME, null, null);
            }
            return new Game(this.board, this.players, 1 - this.currentPlayerIndex, this.winner, TurnPhase.CHOOSE_WORKER, null, null);
        } catch (IllegalArgumentException e) {
            return this;
        }
    }

    private boolean checkLoseMoveableWorker(Player player){
        for(Worker worker:player.getWorkers()){
            List<Space> availableMoves=this.board.getAvailableMoves(worker);
            if(availableMoves!=null && !availableMoves.isEmpty()){
                return true;
            }
        }
        return false;
    }
    // Getters for testing
    public Board getBoard() { return this.board; }
    public Player[] getPlayers() { return this.players; }
    public Worker getWorker() { return this.selectedWorker; }
    public int getCurrentPlayerIndex() { return this.currentPlayerIndex; }
    public TurnPhase getCurrentPhase() { return this.currentPhase; }
    public Player getWinner() { 
        if (this.winner!=-1) {
            return this.players[this.winner]; 
        }
        return null;
    }
}