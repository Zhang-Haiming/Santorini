package edu.cmu.cs214.hw2;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the current state of the game for serialization
 * follows the tic-tac-toe example
 */
public class GameState {
    private final Cell[] cells;
    private final String message;
    private final String winner;

    private GameState(Cell[] cells, String message, String winner) {
        this.cells = cells;
        this.message = message;
        this.winner = winner;
    }
    
    public static GameState forGame(Game game){
        Cell[] cells=getCells(game);
        String message=getMessage(game);
        String winner=getWinner(game);
        return new GameState(cells, message, winner);
    }
    public Cell[] getCells(){
        return this.cells;
    }
    @Override
    public String toString(){
        return """
        {
            "cells": %s,
            "message": "%s",
            "winner": %s
        }
        """.formatted(
            Arrays.toString(this.cells), 
            this.message, 
            this.winner!=null ? "\""+this.winner+"\"":"null"
            );
                
    }
    private static Cell[] getCells(Game game){
        Cell[] cells = new Cell[25];
        Board board = game.getBoard();
        TurnPhase phase = game.getCurrentPhase();
        for (int row=0; row<5; row++){
            for (int col=0; col<5; col++){
                Space space = board.getSpace(row, col);
                boolean playable = isPlayable(game, row, col, space, phase);
                String text = buildCellText(space);
                Cell cell = new Cell(
                    row,
                    col,
                    text,
                    playable,
                    space.getTowerLevel(),
                    space.hasDome(),
                    space.isOccupied() ? space.getOccupied().getOwner().getPlayer() : null
                );
                cells[row*5 + col] = cell;
            }
        }
        return cells;
    }

    private static boolean isPlayable(Game game, int row, int col, Space space,TurnPhase phase){
        switch(phase){
            case TurnPhase.PLAYER1_INITIAL_WORKER1:
            case TurnPhase.PLAYER1_INITIAL_WORKER2:
            case TurnPhase.PLAYER2_INITIAL_WORKER1:
            case TurnPhase.PLAYER2_INITIAL_WORKER2:
                return !space.isOccupied();
            case TurnPhase.CHOOSE_WORKER:
                if(!space.isOccupied()){
                    return false;
                }
                Worker worker=space.getOccupied();
                List<Space> availableMovesCheck=game.getBoard().getAvailableMoves(worker);
                if(availableMovesCheck==null ||availableMovesCheck.isEmpty()){
                    return false;
                }
                return worker.getOwner().getId()==game.getCurrentPlayerIndex();
            case TurnPhase.MOVE:
                Worker selectedWorker=game.getWorker();
                if (selectedWorker==null){
                    return false;
                }
                List<Space> availableMoves=game.getBoard().getAvailableMoves(selectedWorker);
                if(availableMoves.isEmpty()){
                    return false;
                }
                return availableMoves.contains(space);
            case TurnPhase.BUILD:
                Worker movingWorker=game.getWorker();
                if (movingWorker==null){
                    return false;
                }
                List<Space> availableBuilds=game.getBoard().getBuildableSpaces(movingWorker);
                return availableBuilds.contains(space);
            case TurnPhase.END_GAME:
                return false;
            default:
                return false;
        }
    }

    private static String buildCellText(Space space){
        StringBuilder text = new StringBuilder();
        text.append(space.getTowerLevel());
        
        if (space.isOccupied()){
            Worker worker = space.getOccupied();
            String playerName = worker.getOwner().getPlayer();
            if(playerName.equals("Artemis")){
                text.append("A");
            } else if (playerName.equals("Demeter")){
                text.append("D");
            } 
            
        }
        if(space.hasDome()){
            text.append("⌂");
        }
        return text.toString();
    }

    private static String getMessage(Game game){
        TurnPhase phase=game.getCurrentPhase();
        Player[] players=game.getPlayers();
        switch(phase){
            case TurnPhase.PLAYER1_INITIAL_WORKER1:
                return players[0].getPlayer() + "- place your worker 1.";
            case TurnPhase.PLAYER1_INITIAL_WORKER2:
                return players[0].getPlayer() + "- place your worker 2.";
            case TurnPhase.PLAYER2_INITIAL_WORKER1:
                return players[1].getPlayer() + "- place your worker 1.";
            case TurnPhase.PLAYER2_INITIAL_WORKER2:
                return players[1].getPlayer() + "- place your worker 2.";
            case TurnPhase.CHOOSE_WORKER:
                return players[game.getCurrentPlayerIndex()].getPlayer() + "'s turn - Select a worker";
            case TurnPhase.MOVE:
                return players[game.getCurrentPlayerIndex()].getPlayer() + ", move your selected worker.";
            case TurnPhase.BUILD:
                return players[game.getCurrentPlayerIndex()].getPlayer() + ", build with your selected worker.";
            case TurnPhase.END_GAME:
                return game.getWinner().getPlayer() + " wins!";
            default:
                return "Game in progress.";
        }
    }

    private static String getWinner(Game game){
        if(game.getCurrentPhase()==TurnPhase.END_GAME){
            return game.getWinner().getPlayer();
        }
        return null;
    }
}

class Cell{
    private final int row;
    private final int col;
    private final String text;
    private final boolean playable;
    private final int towerLevel;
    private final boolean hasDome;
    private final String occupiedBy;

    Cell(int row, int col, String text, boolean playable, int towerLevel, boolean hasDome, String occupiedBy){
        this.row = row;
        this.col = col;
        this.text = text;
        this.playable = playable;
        this.towerLevel = towerLevel;
        this.hasDome = hasDome;
        this.occupiedBy = occupiedBy;
    }

    public int getX() { return row; }
    public int getY() { return col; }
    public String getText() { return this.text; }
    public boolean isPlayable() { return this.playable; }
    public int getTowerLevel() { return this.towerLevel; }
    public boolean hasDome() { return this.hasDome; }
    public String getOccupiedBy() { return this.occupiedBy; }

    @Override
    public String toString(){
        return """
        {
            "row": %d,
            "col": %d,
            "text": "%s",
            "playable": %b,
            "towerLevel": %d,
            "hasDome": %b,
            "occupiedBy": %s
        }
        """.formatted(
            this.row,
            this.col,
            this.text,
            this.playable,
            this.towerLevel,
            this.hasDome,
            this.occupiedBy != null ? "\""+this.occupiedBy+"\"" : "null"
        );
    }
}