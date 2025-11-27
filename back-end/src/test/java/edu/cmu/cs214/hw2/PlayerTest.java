package edu.cmu.cs214.hw2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {
    
    private Player player;
    
    @Before
    public void setUp() {
        player = new Player("TestPlayer", 0);
    }
    
    @Test
    public void testPlayerCreation() {
        assertEquals("Player name should be set correctly", "TestPlayer", player.getPlayer());
        assertNotNull("Workers array should be initialized", player.getWorkers());
        assertEquals("Should have exactly 2 workers", 2, player.getWorkers().length);
        
        // Verify workers are initialized
        assertNotNull("First worker should be initialized", player.getWorkers()[0]);
        assertNotNull("Second worker should be initialized", player.getWorkers()[1]);
    }
    
    @Test
    public void testSelectWorkerValidIndex() {
        Worker[] workers = player.getWorkers();
        
        // Test selecting first worker (index 0)
        Worker selectedWorker = player.selectWorker(0);
        assertNotNull("Selected worker should not be null", selectedWorker);
        assertEquals("Should return first worker", workers[0], selectedWorker);
        
        // Test selecting second worker (index 1)
        selectedWorker = player.selectWorker(1);
        assertNotNull("Selected worker should not be null", selectedWorker);
        assertEquals("Should return second worker", workers[1], selectedWorker);
    }
    
    @Test
    public void testSelectWorkerInvalidIndices() {
        // Test negative index
        Worker selectedWorker = player.selectWorker(-1);
        assertNull("Should return null for negative index", selectedWorker);
        
        // Test index beyond array bounds
        selectedWorker = player.selectWorker(2);
        assertNull("Should return null for index >= size", selectedWorker);
        
        // Test very large index
        selectedWorker = player.selectWorker(100);
        assertNull("Should return null for very large index", selectedWorker);
    }
    
    @Test
    public void testWorkerOwnership() {
        Worker worker1 = player.getWorkers()[0];
        Worker worker2 = player.getWorkers()[1];
        
        assertEquals("First worker should belong to player", player, worker1.getOwner());
        assertEquals("Second worker should belong to player", player, worker2.getOwner());
    }
    
    @Test
    public void testMultiplePlayersIndependence() {
        Player player1 = new Player("Player1",0);
        Player player2 = new Player("Player2",1);
        
        // Verify players are independent
        assertNotEquals("Players should have different names", player1.getPlayer(), player2.getPlayer());
        assertNotSame("Players should have different worker arrays", 
                     player1.getWorkers(), player2.getWorkers());
        
        // Verify workers are different instances
        assertNotSame("Workers should be different instances", 
                     player1.getWorkers()[0], player2.getWorkers()[0]);
        assertNotSame("Workers should be different instances", 
                     player1.getWorkers()[1], player2.getWorkers()[1]);
    }
    
    @Test
    public void testPlayerWorkflowIntegration() {
        String playerName = "Artemis";
        Player testPlayer = new Player(playerName, 0);
        
        // Verify initial state
        assertEquals("Name should be set correctly", playerName, testPlayer.getPlayer());
        assertEquals("Should have 2 workers", 2, testPlayer.getWorkers().length);
        
        // Select and verify workers
        Worker worker1 = testPlayer.selectWorker(0);
        Worker worker2 = testPlayer.selectWorker(1);
        
        assertEquals("First worker should belong to player", testPlayer, worker1.getOwner());
        assertEquals("Second worker should belong to player", testPlayer, worker2.getOwner());
        
        // Verify invalid selections
        assertNull("Invalid selection should return null", testPlayer.selectWorker(-1));
        assertNull("Invalid selection should return null", testPlayer.selectWorker(2));
    }
}