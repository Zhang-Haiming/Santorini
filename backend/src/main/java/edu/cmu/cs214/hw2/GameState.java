package edu.cmu.cs214.hw2;

import edu.cmu.cs214.hw2.godcard.GodCard;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the current state of the game for serialization
 * follows the tic-tac-toe example
 */
public class GameState {
    private final Cell[] cells=null;
    private final String message=null;
    private final String winner=null;
    private final String[] availableGodCards=null;
    private final String player1God=null;
    private final String player2God=null;
    private final int currentPlayerIndex=0;
    private final String currentPhase=null;

    private GameState(Cell[] cells, String message, String winner, String[] availableGodCards, String player1God, String player2God, int currentPlayerIndex, String currentPhase) {
        // TODO: Implement this method
    }
    
    public static GameState forGame(Game game){
        // TODO: Implement this method
        return null;
    }
    public Cell[] getCells(){
        // TODO: Implement this method
        return null;
    }
    @Override
    public String toString(){
        // TODO: Implement this method
        return null;
    }
    private static Cell[] getCells(Game game){
        // TODO: Implement this method
        return null;
    }

    private static boolean isPlayable(Game game, int row, int col, Space space,TurnPhase phase){
        // TODO: Implement this method
        return false;
    }

    private static String buildCellText(Space space){
        // TODO: Implement this method
        return null;
    }

    private static String getMessage(Game game){
        // TODO: Implement this method
        return null;
    }

    private static String getWinner(Game game){
        // TODO: Implement this method
        return null;
    }

    private static String[] getAvailableGodCards(Game game){
        // TODO: Implement this method
        return null;
    }

    private static String getPlayerGod(Game game, int playerIndex){
        // TODO: Implement this method
        return null;
    }

    private static String formatStringArray(String[] array) {
        // TODO: Implement this method
        return null;
    }
}

class Cell{
    private final int row=0;
    private final int col=0;
    private final String text=null;
    private final boolean playable=false;
    private final int towerLevel=0;
    private final boolean hasDome=false;
    private final String occupiedBy=null;

    Cell(int row, int col, String text, boolean playable, int towerLevel, boolean hasDome, String occupiedBy){
        // TODO: Implement this method
    }

    public int getX() { 
        // TODO: Implement this method
        return 0;
    }
    public int getY() { 
        // TODO: Implement this method
        return 0;
    }
    public String getText() { 
        // TODO: Implement this method
        return null;
    }
    public boolean isPlayable() { 
        // TODO: Implement this method
        return false;
    }
    public int getTowerLevel() { 
        // TODO: Implement this method
        return 0;
    }
    public boolean hasDome() { 
        // TODO: Implement this method
        return false;
    }
    public String getOccupiedBy() { 
        // TODO: Implement this method
        return null;
    }

    @Override
    public String toString(){
        // TODO: Implement this method
        return null;
    }
}

