package edu.cmu.cs214.hw2;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

public class BoardTest {
    
    private Board board;
    
    @Mock
    private Worker mockWorker;
    
    @Mock
    private Worker mockWorker2;
    
    @Mock
    private Space mockCurrentSpace;
    
    @Mock
    private Space mockTargetSpace;
    
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        board = new Board();
    }
    
    @Test
    public void testBoardInitialization() {
        // Test that board is properly initialized with 25 spaces
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Space space = board.getSpace(row, col);
                assertNotNull("Space should not be null at (" + row + ", " + col + ")", space);
                assertEquals("Row should match", row, space.getRow());
                assertEquals("Column should match", col, space.getCol());
                assertEquals("Initial level should be 0", 0, space.getTowerLevel());
                assertFalse("Should not have dome initially", space.hasDome());
                assertFalse("Should not be occupied initially", space.isOccupied());
            }
        }
    }
    
    @Test
    public void testGetSpaceValidPositions() {
        // Test corner positions
        Space corner1 = board.getSpace(0, 0);
        assertNotNull("Top-left corner should exist", corner1);
        assertEquals(0, corner1.getRow());
        assertEquals(0, corner1.getCol());
        
        Space corner2 = board.getSpace(4, 4);
        assertNotNull("Bottom-right corner should exist", corner2);
        assertEquals(4, corner2.getRow());
        assertEquals(4, corner2.getCol());
        
        // Test center position
        Space center = board.getSpace(2, 2);
        assertNotNull("Center space should exist", center);
        assertEquals(2, center.getRow());
        assertEquals(2, center.getCol());
    }
    
    @Test
    public void testGetSpaceInvalidPositions() {
        // Test out of bounds positions
        assertNull("Negative row should return null", board.getSpace(-1, 0));
        assertNull("Negative col should return null", board.getSpace(0, -1));
        assertNull("Row >= 5 should return null", board.getSpace(5, 0));
        assertNull("Col >= 5 should return null", board.getSpace(0, 5));
        assertNull("Both out of bounds should return null", board.getSpace(-1, -1));
        assertNull("Large values should return null", board.getSpace(10, 10));
    }
    
    @Test
    public void testGetAvailableMovesFromCenter() {
        // Mock worker at center position
        Space centerSpace = board.getSpace(2, 2);
        when(mockWorker.getPos()).thenReturn(centerSpace);
        
        // Mock canOccupy to return true for all adjacent spaces
        when(mockWorker.canOccupy(any(Space.class))).thenReturn(true);
        
        List<Space> moves = board.getAvailableMoves(mockWorker);
        
        // Should have 8 adjacent spaces from center
        assertEquals("Should have 8 possible moves from center", 8, moves.size());
        
        // Verify worker.canOccupy was called for each adjacent space
        verify(mockWorker, times(8)).canOccupy(any(Space.class));
    }
    
    @Test
    public void testGetAvailableMovesFromCorner() {
        // Mock worker at corner (0,0)
        Space cornerSpace = board.getSpace(0, 0);
        when(mockWorker.getPos()).thenReturn(cornerSpace);
        when(mockWorker.canOccupy(any(Space.class))).thenReturn(true);
        
        List<Space> moves = board.getAvailableMoves(mockWorker);
        
        // Should have 3 adjacent spaces from corner
        assertEquals("Should have 3 possible moves from corner", 3, moves.size());
        verify(mockWorker, times(3)).canOccupy(any(Space.class));
    }
    
    @Test
    public void testGetAvailableMovesFromEdge() {
        // Mock worker at edge position (0, 2) - top edge
        Space edgeSpace = board.getSpace(0, 2);
        when(mockWorker.getPos()).thenReturn(edgeSpace);
        when(mockWorker.canOccupy(any(Space.class))).thenReturn(true);
        
        List<Space> moves = board.getAvailableMoves(mockWorker);
        
        // Should have 5 adjacent spaces from edge
        assertEquals("Should have 5 possible moves from edge", 5, moves.size());
        verify(mockWorker, times(5)).canOccupy(any(Space.class));
    }
    
    @Test
    public void testGetAvailableMovesWithRestrictions() {
        // Mock worker at center
        Space centerSpace = board.getSpace(2, 2);
        when(mockWorker.getPos()).thenReturn(centerSpace);
        
        // Mock canOccupy to return false for all spaces (no valid moves)
        when(mockWorker.canOccupy(any(Space.class))).thenReturn(false);
        
        List<Space> moves = board.getAvailableMoves(mockWorker);
        
        // Should have no valid moves
        assertEquals("Should have no moves when all spaces are restricted", 0, moves.size());
        verify(mockWorker, times(8)).canOccupy(any(Space.class));
    }
    
    @Test
    public void testGetAvailableMovesWorkerWithNullPosition() {
        // Mock worker with null position
        when(mockWorker.getPos()).thenReturn(null);
        
        List<Space> moves = board.getAvailableMoves(mockWorker);
        
        // Should return empty list for worker with no position
        assertTrue("Should return empty list for worker without position", moves.isEmpty());
        // Should not call canOccupy since worker has no position
        verify(mockWorker, never()).canOccupy(any(Space.class));
    }
    
    @Test
    public void testGetBuildableSpacesFromCenter() {
        // Mock worker at center (2,2)
        Space centerSpace = board.getSpace(2, 2);
        when(mockWorker.getPos()).thenReturn(centerSpace);
        
        List<Space> buildables = board.getBuildableSpaces(mockWorker);
        
        // Should have 8 buildable spaces initially (all empty adjacent spaces)
        assertEquals("Should have 8 buildable spaces from center initially", 8, buildables.size());
        
        // Verify all returned spaces can build
        for (Space space : buildables) {
            assertTrue("Each space should be buildable", space.canBuild());
            assertFalse("Buildable spaces should not be occupied", space.isOccupied());
            assertFalse("Buildable spaces should not have domes", space.hasDome());
        }
    }
    
    @Test
    public void testGetBuildableSpacesFromCorner() {
        // Mock worker at corner (0,0)
        Space cornerSpace = board.getSpace(0, 0);
        when(mockWorker.getPos()).thenReturn(cornerSpace);
        
        List<Space> buildables = board.getBuildableSpaces(mockWorker);
        
        // Should have 3 buildable spaces from corner
        assertEquals("Should have 3 buildable spaces from corner", 3, buildables.size());
        
        for (Space space : buildables) {
            assertTrue("Each space should be buildable", space.canBuild());
        }
    }
    
    @Test
    public void testGetBuildableSpacesWithOccupiedSpaces() {
        // Mock worker at center
        Space centerSpace = board.getSpace(2, 2);
        when(mockWorker.getPos()).thenReturn(centerSpace);
        
        // Occupy some adjacent spaces with mock workers
        Space adjacentSpace1 = board.getSpace(1, 1);
        Space adjacentSpace2 = board.getSpace(1, 2);
        
        adjacentSpace1.setOccupied(mockWorker2);
        adjacentSpace2.setOccupied(mockWorker2);
        
        List<Space> buildables = board.getBuildableSpaces(mockWorker);
        
        // Should have 6 buildable spaces when 2 are occupied
        assertEquals("Should have 6 buildable spaces when 2 are occupied", 6, buildables.size());
        
        // Verify occupied spaces are not in buildable list
        assertFalse("Occupied space should not be buildable", buildables.contains(adjacentSpace1));
        assertFalse("Occupied space should not be buildable", buildables.contains(adjacentSpace2));
    }
    
    @Test
    public void testGetBuildableSpacesWithDomes() {
        // Mock worker at center
        Space centerSpace = board.getSpace(2, 2);
        when(mockWorker.getPos()).thenReturn(centerSpace);
        
        // Add domes to some adjacent spaces
        Space domeSpace1 = board.getSpace(3, 2);
        Space domeSpace2 = board.getSpace(3, 3);
        
        // Build to create domes (level 3 + dome)
        domeSpace1.build(); domeSpace1.build(); domeSpace1.build(); domeSpace1.build();
        domeSpace2.build(); domeSpace2.build(); domeSpace2.build(); domeSpace2.build();
        
        List<Space> buildables = board.getBuildableSpaces(mockWorker);
        
        // Should have 6 buildable spaces (8 - 2 with domes)
        assertEquals("Should have 6 buildable spaces when 2 have domes", 6, buildables.size());
        assertFalse("Space with dome should not be buildable", buildables.contains(domeSpace1));
        assertFalse("Space with dome should not be buildable", buildables.contains(domeSpace2));
    }
    
    @Test
    public void testGetBuildableSpacesWorkerWithNullPosition() {
        // Mock worker with null position
        when(mockWorker.getPos()).thenReturn(null);
        
        List<Space> buildables = board.getBuildableSpaces(mockWorker);
        
        // Should return empty list for worker with no position
        assertTrue("Should return empty list for worker without position", buildables.isEmpty());
    }
    
    @Test
    public void testGetUnoccupiedSpacesInitially() {
        List<Space> unoccupied = board.getUnoccupiedSpaces();
        
        // Initially all 25 spaces should be unoccupied
        assertEquals("All 25 spaces should be unoccupied initially", 25, unoccupied.size());
        
        for (Space space : unoccupied) {
            assertFalse("All spaces should be unoccupied", space.isOccupied());
        }
    }
    
    @Test
    public void testUpdateSpaceImmutability() {
        Space originalSpace = board.getSpace(2, 2);
        assertEquals("Original space should be level 0", 0, originalSpace.getTowerLevel());
        
        // Create a modified space
        Space modifiedSpace = new Space(2, 2, 3, true, mockWorker);
        
        // Update the board (should return new board)
        Board newBoard = board.updateSpace(modifiedSpace);
        
        // Verify original board is unchanged
        assertEquals("Original board space should be unchanged", 0, board.getSpace(2, 2).getTowerLevel());
        assertFalse("Original board space should not have dome", board.getSpace(2, 2).hasDome());
        assertFalse("Original board space should not be occupied", board.getSpace(2, 2).isOccupied());
        
        // Verify new board has the updated space
        assertEquals("New board should have updated space level", 3, newBoard.getSpace(2, 2).getTowerLevel());
        assertTrue("New board space should have dome", newBoard.getSpace(2, 2).hasDome());
        assertTrue("New board space should be occupied", newBoard.getSpace(2, 2).isOccupied());
        
        // Verify other spaces are unchanged in new board
        assertEquals("Other spaces should be unchanged", 0, newBoard.getSpace(1, 1).getTowerLevel());
        assertEquals("Other spaces should be unchanged", 0, newBoard.getSpace(3, 3).getTowerLevel());
        
        // Verify boards are different objects
        assertNotSame("Should return new Board instance", board, newBoard);
    }
}
