package edu.cmu.cs214.hw2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WorkerTest {
    
    private Worker worker;
    private Player player;
    
    @Before
    public void setUp() {
        player = new Player("TestPlayer", 0);
        worker = new Worker(player);
    }
    
    @Test
    public void testWorkerCreation() {
        assertEquals("Worker should belong to player", player, worker.getOwner());
        assertNull("Worker should not have position initially", worker.getPos());
    }
    
    @Test
    public void testPlaceOn() {
        Space space = new Space(1, 1, 0, false, null);
        
        worker.placeOn(space);
        
        assertEquals("Worker should be at the placed space", space, worker.getPos());
        assertEquals("Space should be occupied by worker", worker, space.getOccupied());
        assertTrue("Space should be marked as occupied", space.isOccupied());
    }
    
    @Test
    public void testMoveToValidMove() {
        Space currentSpace = new Space(1, 1, 0, false, null);
        Space targetSpace = new Space(1, 2, 0, false, null); // Adjacent space
        
        worker.placeOn(currentSpace);
        worker.moveTo(targetSpace);
        
        assertEquals("Worker should be at target space", targetSpace, worker.getPos());
        assertEquals("Target space should be occupied by worker", worker, targetSpace.getOccupied());
        assertNull("Current space should no longer be occupied", currentSpace.getOccupied());
        assertFalse("Current space should not be marked as occupied", currentSpace.isOccupied());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMoveToSameSpace() {
        Space space = new Space(2, 2, 0, false, null);
        worker.placeOn(space);
        
        // Trying to move to same space should throw IllegalArgumentException
        worker.moveTo(space);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMoveToInvalidSpace() {
        Space currentSpace = new Space(1, 1, 0, false, null);
        Space targetSpace = new Space(3, 3, 0, false, null); // Not adjacent
        
        worker.placeOn(currentSpace);
        worker.moveTo(targetSpace); // Should throw exception
    }
    
    @Test
    public void testCanOccupyValidSpaces() {
        Space currentSpace = new Space(2, 2, 0, false, null);
        worker.setPos(currentSpace);
        
        // Test all 8 adjacent positions
        int[][] adjacentPositions = {
            {1, 1}, {1, 2}, {1, 3},
            {2, 1},         {2, 3},
            {3, 1}, {3, 2}, {3, 3}
        };
        
        for (int[] pos : adjacentPositions) {
            Space adjacentSpace = new Space(pos[0], pos[1], 0, false, null);
            assertTrue("Position (" + pos[0] + ", " + pos[1] + ") should be reachable", 
                      worker.canOccupy(adjacentSpace));
        }
    }
    
    @Test
    public void testCanOccupyOccupiedSpace() {
        Space currentSpace = new Space(1, 1, 0, false, null);
        Space targetSpace = new Space(2, 1, 0, false, null);
        Player otherPlayer = new Player("OtherPlayer", 1);
        Worker otherWorker = new Worker(otherPlayer);
        
        worker.setPos(currentSpace);
        targetSpace.setOccupied(otherWorker);
        
        assertFalse("Worker cannot occupy space that is already occupied", 
                   worker.canOccupy(targetSpace));
    }
    
    @Test
    public void testCanOccupySpaceWithDome() {
        Space currentSpace = new Space(1, 1, 0, false, null);
        Space targetSpace = new Space(2, 1, 3, true, null); // Has dome
        
        worker.setPos(currentSpace);
        
        assertFalse("Worker cannot occupy space with dome", worker.canOccupy(targetSpace));
    }
    
    @Test
    public void testCanOccupyNotAdjacent() {
        Space currentSpace = new Space(1, 1, 0, false, null);
        Space targetSpace = new Space(3, 3, 0, false, null); // 2 rows and 2 columns away
        
        worker.setPos(currentSpace);
        
        assertFalse("Worker cannot move to non-adjacent space", worker.canOccupy(targetSpace));
    }
    
    @Test
    public void testCanOccupyHeightRestrictions() {
        Space currentSpace = new Space(2, 2, 0, false, null);
        worker.setPos(currentSpace);
        
        // Can move to same level
        Space sameLevelSpace = new Space(2, 3, 0, false, null);
        assertTrue("Should be able to move to same level", worker.canOccupy(sameLevelSpace));
        
        // Can move up one level
        Space oneLevelUpSpace = new Space(2, 3, 1, false, null);
        assertTrue("Should be able to move up one level", worker.canOccupy(oneLevelUpSpace));
        
        // Cannot move up two levels
        Space twoLevelsUpSpace = new Space(2, 3, 2, false, null);
        assertFalse("Should not be able to move up two levels", worker.canOccupy(twoLevelsUpSpace));
        
        // Can move down any number of levels
        Space downSpace = new Space(2, 3, 0, false, null);
        Space workerOnLevel3 = new Space(2, 2, 3, false, null);
        worker.setPos(workerOnLevel3);
        assertTrue("Should be able to move down multiple levels", worker.canOccupy(downSpace));
    }
    
    @Test
    public void testCanOccupySamePosition() {
        Space currentSpace = new Space(1, 1, 0, false, null);
        worker.setPos(currentSpace);
        
        assertFalse("Worker cannot stay in same position during turn", 
                   worker.canOccupy(currentSpace));
    }
    
    @Test
    public void testCanOccupyNullInputs() {
        assertFalse("Should return false for null space", worker.canOccupy(null));
        
        Space space = new Space(1, 1, 0, false, null);
        assertFalse("Should return false when worker has no position", worker.canOccupy(space));
    }
}