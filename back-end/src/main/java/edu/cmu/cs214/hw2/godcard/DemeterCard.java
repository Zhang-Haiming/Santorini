package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Space;
import edu.cmu.cs214.hw2.Board;
import edu.cmu.cs214.hw2.Worker;
import java.util.List;

public class DemeterCard implements GodCard {

    @Override
    public String getName() {
        return "Demeter";
    }
    
    // Demeter: Your Worker may build one additional time, but not on the same space.
    @Override
    public List<Space> getAvailableBuilds(Board board, Worker worker, Space firstBuild) {
        List<Space> spaces = board.getBuildableSpaces(worker);

        if(firstBuild != null) {
            spaces.removeIf(space -> 
                space.getRow() == firstBuild.getRow() && 
                space.getCol() == firstBuild.getCol());
        }
        return spaces;
    }
}