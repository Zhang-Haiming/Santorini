package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Space;
import edu.cmu.cs214.hw2.Board;
import edu.cmu.cs214.hw2.Worker;

import java.util.ArrayList;
import java.util.List;

public class HephaestusCard implements GodCard {

    @Override
    public String getName() {
        // TODO: Implement this method
        return null;
    }
    
    // Hephaestus: Your Worker may build one additional time on top of your first build (i.e., increase a level by 2), 
    // but not to a dome.
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