package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Space;
import edu.cmu.cs214.hw2.Board;
import edu.cmu.cs214.hw2.Worker;

import java.util.ArrayList;
import java.util.List;

public class HephaestusCard implements GodCard {

    @Override
    public String getName() {
        return "Hephaestus";
    }
    
    // Hephaestus: Your Worker may build one additional time on top of your first build (i.e., increase a level by 2), 
    // but not to a dome.
    @Override
    public List<Space> getAvailableBuilds(Board board, Worker worker, Space firstBuild) {
        if(firstBuild != null) {
            List<Space> spaces =new ArrayList<>();
            if(!firstBuild.hasDome()){
                spaces.add(board.getSpace(firstBuild.getRow(), firstBuild.getCol()));
            }
            return spaces;
        }
        return board.getBuildableSpaces(worker);
    }
}
