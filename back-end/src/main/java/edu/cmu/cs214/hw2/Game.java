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
        this.board = new Board();
        this.players = new Player[2];
        this.currentPlayerIndex = 0;
        this.winner=-1;
        this.currentPhase=TurnPhase.PLAYER1_CHOOSE_GODCARD;
        this.selectedWorker=null;
        this.available=null;
        this.godCards=new GodCard[]{null,null};
        initialPlayer();
    }

    public Game(Board board, Player[] players, int nextPlayerIndex, int winner, TurnPhase nextPhase, Worker selectedWorker, List<Space> available, GodCard[] godCards) {
        this.board = board;
        this.players = players;
        this.currentPlayerIndex = nextPlayerIndex;
        this.winner = winner;
        this.currentPhase = nextPhase;
        this.selectedWorker = selectedWorker;
        this.available = available;
        this.godCards = godCards;
    }

    /**
     * Initialize players
     */
    public void initialPlayer() {
        this.players[0] = new Player("Player A", 0);
        this.players[1] = new Player("Player B", 1);
    }

    public Game chooseGodCard(String godName){
        GodCard godcard;
        switch (godName.toLowerCase()){
            case "demeter":
                godcard=new DemeterCard();
                break;
            case "hephaestus":
                godcard=new HephaestusCard();
                break;
            case "pan":
                godcard=new PanCard();
                break;
            case "minotaur":
                godcard=new MinotaurCard();
                break;
            default:
                godcard=new NoGodCard();
                break;
        }
        GodCard[] newGodCards = this.godCards.clone();
        newGodCards[this.currentPlayerIndex]=godcard;
        if(this.currentPhase==TurnPhase.PLAYER1_CHOOSE_GODCARD){
            return new Game(this.board, this.players, 1, this.winner, TurnPhase.PLAYER2_CHOOSE_GODCARD, this.selectedWorker, this.available, newGodCards);
        } else {
            return new Game(this.board, this.players, 0, this.winner, TurnPhase.PLAYER1_INITIAL_WORKER1, this.selectedWorker, this.available, newGodCards);
        }
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
        return new Game(this.board.updateSpace(target), this.players, nextPlayerIndex, this.winner, nextPhase, this.selectedWorker,null, this.godCards);
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
            if (!this.checkHasMoveableWorker(this.players[this.currentPlayerIndex])){
                System.out.println("No moveable workers available!");
                return new Game(this.board, this.players, this.currentPlayerIndex, 1 - this.currentPlayerIndex, TurnPhase.END_GAME, this.selectedWorker,null, this.godCards);
            }
            Worker worker=target.getOccupied();
            
            if(worker.getOwner().getId()!=this.currentPlayerIndex){
                return this;
            }
            List<Space> availableMoves = this.getAvailableMoves(this.board, worker);
            if (availableMoves.isEmpty()){
                return this;
            }
            return new Game(
                this.board, this.players, this.currentPlayerIndex, 
                this.winner, TurnPhase.MOVE, worker, availableMoves, this.godCards
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
        this.board = this.board.updateSpace(srcSpace).updateSpace(destSpace);
        if(checkWinCondition(srcSpace, destSpace)){
            return new Game(this.board, this.players, this.currentPlayerIndex, this.currentPlayerIndex, TurnPhase.END_GAME, this.selectedWorker, null, this.godCards);
        }
        List<Space> buildableSpaces = this.getAvailableBuilds(this.board, this.selectedWorker, null);
        if(buildableSpaces.isEmpty()){
            System.out.println("No buildable spaces available!");
            return new Game(this.board, this.players, this.currentPlayerIndex, 1 - this.currentPlayerIndex, TurnPhase.END_GAME, this.selectedWorker,null, this.godCards);
        }
        return new Game(this.board, this.players, this.currentPlayerIndex, this.winner, TurnPhase.BUILD, this.selectedWorker, buildableSpaces, this.godCards);
    }

    /**
     * Build a tower after moving
     */
    public Game firstBuildTower(int x, int y) {
        try{
            Space buildSpace = new Space(this.board.getSpace(x, y));
            buildSpace.build();
            this.board=this.board.updateSpace(buildSpace);
            
            // TODO: add SECOND_BUILD phase
            List<Space> secondBuildSpaces = this.getAvailableBuilds(this.board, this.selectedWorker, buildSpace);
            if(!secondBuildSpaces.isEmpty()) {
                return new Game(this.board, this.players, this.currentPlayerIndex, this.winner, TurnPhase.SECOND_BUILD, this.selectedWorker, secondBuildSpaces, this.godCards);
            } 
            return new Game(this.board, this.players, 1 - this.currentPlayerIndex, this.winner, TurnPhase.CHOOSE_WORKER, null, null, this.godCards);
        } catch (IllegalArgumentException e) {
            return this;
        }
    }
    public Game secondBuildTower(int x, int y) {
        try{
            Space buildSpace = new Space(this.board.getSpace(x, y));
            buildSpace.build();
            this.board=this.board.updateSpace(buildSpace);
            return new Game(this.board, this.players, 1-this.currentPlayerIndex, this.winner, TurnPhase.CHOOSE_WORKER, null, null, this.godCards);
        } catch (IllegalArgumentException e) {
            return this;
        }
    }

    public boolean checkHasMoveableWorker(Player player){
        for(Worker worker:player.getWorkers()){
            List<Space> availableMoves=this.getAvailableMoves(this.board, worker);
            if(availableMoves!=null && !availableMoves.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public boolean checkWinCondition(Space from, Space to){
        if(to.getTowerLevel()==WINNING_TOWER_LEVEL){
            return true;
        }
        GodCard godCard=getGodCard();
        return godCard.checkAdditionalCondition(from, to);
    }

    public List<Space> getAvailableMoves(Board board, Worker worker){
        GodCard godCard=getGodCard();
        return godCard.getAvailableMoves(board, worker);
    }

    public List<Space> getAvailableBuilds(Board board, Worker worker, Space firstBuild){
        GodCard godCard=getGodCard();
        return godCard.getAvailableBuilds(board, worker, firstBuild);
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
    public List<Space> getAvailable() { return this.available; }
    public GodCard getGodCard() {
        return this.godCards[this.currentPlayerIndex];
    }
}