package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Space;
import edu.cmu.cs214.hw2.Board;
import edu.cmu.cs214.hw2.Worker;
import java.util.List;

public interface GodCard {
    String getName();
    default List<Space> getAvailableMoves(Board board, Worker worker) {
        // TODO: Implement this method
        return null;
    }

    default List<Space> getAvailableBuilds(Board board, Worker worker, Space firstBuild) {
        // TODO: Implement this method
        return null;
    }

    default boolean checkAdditionalCondition(Space from, Space to) {
        // TODO: Implement this method
        return false;
    }

    default boolean allowSecondBuild() {
        // TODO: Implement this method
        return false;
    }

    default Board executeMove(Board board, Worker worker, Space destSpace) {
        // TODO: Implement this method
        return null;
    }
}
