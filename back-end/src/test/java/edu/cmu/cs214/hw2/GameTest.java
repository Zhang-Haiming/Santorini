package edu.cmu.cs214.hw2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
    
    private Game game;
    
    @Before
    public void setUp() {
        game = new Game();
    }
    
    @Test
    public void testInitialGameState() {
        assertEquals("Initial phase should be PLAYER1_INITIAL_WORKER1", 
                     TurnPhase.PLAYER1_INITIAL_WORKER1, game.getCurrentPhase());
        assertEquals("Initial player should be 0", 0, game.getCurrentPlayerIndex());
        assertNotNull("Board should be initialized", game.getBoard());
        assertNotNull("Players should be initialized", game.getPlayers());
        assertEquals("Should have 2 players", 2, game.getPlayers().length);
    }
    
    @Test
    public void testPlaceInitialWorkers() {
        // Place Player 1's first worker
        Game newGame = game.placeInitialWorker(0, 0, 1, 1);
        assertEquals("Should move to PLAYER1_INITIAL_WORKER2", 
                     TurnPhase.PLAYER1_INITIAL_WORKER2, newGame.getCurrentPhase());
        assertEquals("Should still be player 0's turn", 0, newGame.getCurrentPlayerIndex());
        
        // Place Player 1's second worker
        newGame = newGame.placeInitialWorker(0, 1, 1, 2);
        assertEquals("Should move to PLAYER2_INITIAL_WORKER1", 
                     TurnPhase.PLAYER2_INITIAL_WORKER1, newGame.getCurrentPhase());
        assertEquals("Should switch to player 1", 1, newGame.getCurrentPlayerIndex());
        
        // Place Player 2's first worker
        newGame = newGame.placeInitialWorker(1, 0, 3, 1);
        assertEquals("Should move to PLAYER2_INITIAL_WORKER2", 
                     TurnPhase.PLAYER2_INITIAL_WORKER2, newGame.getCurrentPhase());
        assertEquals("Should still be player 1's turn", 1, newGame.getCurrentPlayerIndex());
        
        // Place Player 2's second worker
        newGame = newGame.placeInitialWorker(1, 1, 3, 2);
        assertEquals("Should move to CHOOSE_WORKER", 
                     TurnPhase.CHOOSE_WORKER, newGame.getCurrentPhase());
        assertEquals("Should switch back to player 0", 0, newGame.getCurrentPlayerIndex());
    }
    
    @Test
    public void testInvalidWorkerPlacement() {
        // Try to place worker on an occupied space
        Game newGame = game.placeInitialWorker(0, 0, 1, 1);
        Game sameGame = newGame.placeInitialWorker(0, 1, 1, 1); // Same position
        
        // Should not change phase since placement failed
        assertEquals("Phase should remain the same after invalid placement", 
                     newGame.getCurrentPhase(), sameGame.getCurrentPhase());
    }
    
    @Test
    public void testChooseWorker() {
        // Setup: Place all initial workers
        Game setupGame = game.placeInitialWorker(0, 0, 1, 1)
                             .placeInitialWorker(0, 1, 1, 2)
                             .placeInitialWorker(1, 0, 3, 1)
                             .placeInitialWorker(1, 1, 3, 2);
        
        // Choose a worker
        Game newGame = setupGame.chooseWorker(1, 1);
        assertEquals("Should move to MOVE phase", TurnPhase.MOVE, newGame.getCurrentPhase());
        assertNotNull("Selected worker should not be null", newGame.getWorker());
    }
    
    @Test
    public void testChooseInvalidWorker() {
        // Setup: Place all initial workers
        Game setupGame = game.placeInitialWorker(0, 0, 1, 1)
                             .placeInitialWorker(0, 1, 1, 2)
                             .placeInitialWorker(1, 0, 3, 1)
                             .placeInitialWorker(1, 1, 3, 2);
        
        // Debug: Check what phase setupGame is actually in
        System.out.println("Setup game phase: " + setupGame.getCurrentPhase());
        
        // Try to choose an empty space
        Game sameGame = setupGame.chooseWorker(0, 0);
        System.out.println("After chooseWorker phase: " + sameGame.getCurrentPhase());
        assertEquals("Phase should remain CHOOSE_WORKER", 
                     TurnPhase.CHOOSE_WORKER, sameGame.getCurrentPhase());
    }
    
    @Test
    public void testChooseWorkerNotBelongingToCurrentPlayer() {
        // Setup: Place all initial workers - now it's player 0's turn (CHOOSE_WORKER phase)
        Game setupGame = game.placeInitialWorker(0, 0, 1, 1)  // Player 0's first worker at (1,1)
                             .placeInitialWorker(0, 1, 1, 2)  // Player 0's second worker at (1,2)
                             .placeInitialWorker(1, 0, 3, 1)  // Player 1's first worker at (3,1)
                             .placeInitialWorker(1, 1, 3, 2); // Player 1's second worker at (3,2)
        
        // Verify it's player 0's turn
        assertEquals("Should be player 0's turn", 0, setupGame.getCurrentPlayerIndex());
        assertEquals("Should be in CHOOSE_WORKER phase", TurnPhase.CHOOSE_WORKER, setupGame.getCurrentPhase());
        
        // Try to choose player 1's worker (at position 3,1) while it's player 0's turn
        Game sameGame = setupGame.chooseWorker(3, 1);
        
        // Game state should remain unchanged
        assertEquals("Phase should remain CHOOSE_WORKER", 
                     TurnPhase.CHOOSE_WORKER, sameGame.getCurrentPhase());
        assertEquals("Current player should remain player 0", 
                     0, sameGame.getCurrentPlayerIndex());
        assertNull("No worker should be selected", sameGame.getWorker());
        
        // Verify that choosing own worker still works
        Game validChoice = setupGame.chooseWorker(1, 1);
        assertEquals("Should advance to MOVE phase when choosing own worker", 
                     TurnPhase.MOVE, validChoice.getCurrentPhase());
        assertNotNull("Worker should be selected when choosing own worker", 
                      validChoice.getWorker());
    }
    
    @Test
    public void testMoveWorker() {
        // Setup: Place all initial workers and choose one
        Game setupGame = game.placeInitialWorker(0, 0, 1, 1)
                             .placeInitialWorker(0, 1, 1, 2)
                             .placeInitialWorker(1, 0, 3, 1)
                             .placeInitialWorker(1, 1, 3, 2)
                             .chooseWorker(1, 1);
        
        // Move the worker
        Game newGame = setupGame.move(2, 1);
        assertEquals("Should move to BUILD phase", TurnPhase.BUILD, newGame.getCurrentPhase());
        assertNotNull("Selected worker should still be available", newGame.getWorker());
    }
    
    @Test
    public void testBuildTower() {
        // Setup: Full setup through move
        Game setupGame = game.placeInitialWorker(0, 0, 1, 1)
                             .placeInitialWorker(0, 1, 1, 2)
                             .placeInitialWorker(1, 0, 3, 1)
                             .placeInitialWorker(1, 1, 3, 2)
                             .chooseWorker(1, 1)
                             .move(2, 1);
        
        // Build a tower
        Game newGame = setupGame.buildTower(1, 1);
        assertEquals("Should return to CHOOSE_WORKER phase", 
                     TurnPhase.CHOOSE_WORKER, newGame.getCurrentPhase());
        assertEquals("Should switch to next player", 1, newGame.getCurrentPlayerIndex());
        assertNull("Selected worker should be cleared", newGame.getWorker());
    }
    
    @Test
    public void testImmutableGameState() {
        // Test that game operations return new instances
        Game newGame = game.placeInitialWorker(0, 0, 1, 1);
        
        // Original game should be unchanged
        assertEquals("Original game phase should be unchanged", 
                     TurnPhase.PLAYER1_INITIAL_WORKER1, game.getCurrentPhase());
        assertEquals("New game should have advanced phase", 
                     TurnPhase.PLAYER1_INITIAL_WORKER2, newGame.getCurrentPhase());
        
        // Should be different objects
        assertNotSame("Should return new Game instance", game, newGame);
    }
    
    @Test
    public void testPlayerNames() {
        Player[] players = game.getPlayers();
        assertEquals("Player 0 should be Artemis", "Artemis", players[0].getPlayer());
        assertEquals("Player 1 should be Demeter", "Demeter", players[1].getPlayer());
    }
}