package edu.cmu.cs214.hw2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpaceTest {
    
    private Space space;
    private Player player;
    private Worker worker;
    
    @Before
    public void setUp() {
        space = new Space(2, 3, 0, false, null); // Create space at position (2,3)
        player = new Player("TestPlayer", 0);
        worker = player.getWorkers()[0];
    }
    
    @Test
    public void testSpaceCreation() {
        assertEquals("Row should be 2", 2, space.getRow());
        assertEquals("Col should be 3", 3, space.getCol());
        assertEquals("Initial level should be 0", 0, space.getTowerLevel());
        assertFalse("Should not have dome initially", space.hasDome());
        assertNull("Should not be occupied initially", space.getOccupied());
        assertFalse("Should not be occupied initially", space.isOccupied());
    }
    
    @Test
    public void testCopyConstructor() {
        Space originalSpace = new Space(1, 2, 2, false, worker);
        Space copiedSpace = new Space(originalSpace);
        
        assertEquals("Row should be copied", originalSpace.getRow(), copiedSpace.getRow());
        assertEquals("Col should be copied", originalSpace.getCol(), copiedSpace.getCol());
        assertEquals("Tower level should be copied", originalSpace.getTowerLevel(), copiedSpace.getTowerLevel());
        assertEquals("Dome status should be copied", originalSpace.hasDome(), copiedSpace.hasDome());
        assertEquals("Occupancy should be copied", originalSpace.getOccupied(), copiedSpace.getOccupied());
    }
    
    @Test
    public void testBuildTowerLevels() {
        assertEquals("Initial level should be 0", 0, space.getTowerLevel());
        assertFalse("Should not have dome initially", space.hasDome());
        
        space.build();
        assertEquals("Should be level 1 after first build", 1, space.getTowerLevel());
        assertFalse("Should not have dome at level 1", space.hasDome());
        
        space.build();
        assertEquals("Should be level 2 after second build", 2, space.getTowerLevel());
        assertFalse("Should not have dome at level 2", space.hasDome());
        
        space.build();
        assertEquals("Should be level 3 after third build", 3, space.getTowerLevel());
        assertFalse("Should not have dome at level 3", space.hasDome());
        
        // Building at level 3 should add dome
        space.build();
        assertEquals("Should remain level 3 when dome is added", 3, space.getTowerLevel());
        assertTrue("Should have dome after building at level 3", space.hasDome());
    }
    
    @Test
    public void testBuildWithDome() {
        // Build to level 3 and add dome
        space.build(); // level 1
        space.build(); // level 2
        space.build(); // level 3
        space.build(); // dome
        
        assertTrue("Should have dome", space.hasDome());
        
        // Try to build again - should not change anything
        space.build();
        assertEquals("Level should remain 3", 3, space.getTowerLevel());
        assertTrue("Should still have dome", space.hasDome());
    }
    
    @Test
    public void testCanBuildEmptySpace() {
        assertTrue("Empty space should be buildable", space.canBuild());
    }
    
    @Test
    public void testCanBuildOccupiedSpace() {
        space.setOccupied(worker);
        assertFalse("Occupied space should not be buildable", space.canBuild());
    }
    
    @Test
    public void testCanBuildWithDome() {
        // Build to dome
        space.build(); // level 1
        space.build(); // level 2
        space.build(); // level 3
        space.build(); // dome
        
        assertFalse("Space with dome should not be buildable", space.canBuild());
    }
    
    @Test
    public void testCanMoveToValidMove() {
        // Create a worker on a level 0 space
        Space currentSpace = new Space(0, 0, 0, false, null);
        worker.setPos(currentSpace);
        
        // Test moving to level 1 (valid - can move up 1 level)
        space.build(); // Make space level 1
        
        assertTrue("Should be able to move from level 0 to level 1", space.canMoveTo(worker));
    }
    
    @Test
    public void testCanMoveToOccupiedSpace() {
        Space currentSpace = new Space(0, 0, 0, false, null);
        worker.setPos(currentSpace);
        
        space.setOccupied(worker);
        
        assertFalse("Should not be able to move to occupied space", space.canMoveTo(worker));
    }
    
    @Test
    public void testCanMoveToSpaceWithDome() {
        Space currentSpace = new Space(0, 0, 0, false, null);
        worker.setPos(currentSpace);
        
        // Add dome to space
        space.build(); // level 1
        space.build(); // level 2
        space.build(); // level 3
        space.build(); // dome
        
        assertFalse("Should not be able to move to space with dome", space.canMoveTo(worker));
    }
    
    @Test
    public void testCanMoveToTooHighLevel() {
        // Worker on level 0, trying to move to level 2 (can only go up 1 level)
        Space currentSpace = new Space(0, 0, 0, false, null);
        worker.setPos(currentSpace);
        
        space.build(); // level 1
        space.build(); // level 2
        
        assertFalse("Should not be able to move up more than 1 level", space.canMoveTo(worker));
    }
    
    @Test
    public void testCanMoveToSameLevel() {
        Space currentSpace = new Space(0, 0, 2, false, null);
        worker.setPos(currentSpace);
        
        space.build(); // level 1
        space.build(); // level 2
        
        assertTrue("Should be able to move to same level", space.canMoveTo(worker));
    }
    
    @Test
    public void testCanMoveToLowerLevel() {
        Space currentSpace = new Space(0, 0, 3, false, null);
        worker.setPos(currentSpace);
        
        space.build(); // level 1
        assertTrue("Should be able to move to lower level", space.canMoveTo(worker));
        
        space.build(); // level 2
        assertTrue("Should be able to move to lower level", space.canMoveTo(worker));
        
        space.build(); // level 3
        assertTrue("Should be able to move to same level", space.canMoveTo(worker));
    }
    
    @Test
    public void testSetOccupiedAndRemoveWorker() {
        assertFalse("Initially should not be occupied", space.isOccupied());
        assertNull("Initially should have no occupant", space.getOccupied());
        
        space.setOccupied(worker);
        
        assertTrue("Should be occupied after setting worker", space.isOccupied());
        assertEquals("Should return the correct worker", worker, space.getOccupied());
        
        space.removeWorker();
        
        assertFalse("Should not be occupied after removing worker", space.isOccupied());
        assertNull("Should have no occupant after removal", space.getOccupied());
    }
    
    @Test
    public void testSpaceLifecycleIntegration() {
        // Start with empty space
        assertEquals("Row should be 2", 2, space.getRow());
        assertEquals("Col should be 3", 3, space.getCol());
        assertEquals("Initial level should be 0", 0, space.getTowerLevel());
        assertFalse("Should not have dome initially", space.hasDome());
        assertFalse("Should not be occupied initially", space.isOccupied());
        assertTrue("Should be buildable initially", space.canBuild());
        
        // Build tower progressively
        space.build(); // level 1
        assertEquals("Should be level 1", 1, space.getTowerLevel());
        assertTrue("Should still be buildable", space.canBuild());
        
        space.build(); // level 2
        space.build(); // level 3
        space.build(); // dome
        
        assertEquals("Should be level 3 with dome", 3, space.getTowerLevel());
        assertTrue("Should have dome", space.hasDome());
        assertFalse("Should not be buildable with dome", space.canBuild());
        
        // Test occupation
        space.setOccupied(worker);
        assertTrue("Should be occupied", space.isOccupied());
        assertEquals("Should have correct occupant", worker, space.getOccupied());
    }
}