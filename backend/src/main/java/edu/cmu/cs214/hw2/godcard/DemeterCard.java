package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Space;
import edu.cmu.cs214.hw2.Board;
import edu.cmu.cs214.hw2.Worker;
import java.util.List;

public class DemeterCard implements GodCard {

    @Override
    public String getName() {
        // TODO: Implement this method
        return null;
    }
    
    // Demeter: Your Worker may build one additional time, but not on the same space.
    @Override
    public List<Space> getAvailableBuilds(Board board, Worker worker, Space firstBuild) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public boolean allowSecondBuild() {
        // TODO: Implement this method
        return false;
    }
}