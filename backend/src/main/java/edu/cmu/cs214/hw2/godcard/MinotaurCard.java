package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Board;
import edu.cmu.cs214.hw2.Worker;
import edu.cmu.cs214.hw2.Space;
import java.util.List;
import java.util.ArrayList;

public class MinotaurCard implements GodCard {
    @Override
    public String getName() {
        // TODO: Implement this method
        return null;
    }
    
    private boolean canPushWorker(Board board, Worker worker, Space from, Space to, int dx, int dy) {
        // TODO: Implement this method
        return false;
    }
    
    @Override
    public List<Space> getAvailableMoves(Board board, Worker worker) {
        // TODO: Implement this method
        return null;
    }

    @Override
    public Board executeMove(Board board, Worker worker, Space destSpace) {
        // TODO: Implement this method
        return null;
    }
}
