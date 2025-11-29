package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Space;
import edu.cmu.cs214.hw2.Board;
import edu.cmu.cs214.hw2.Worker;
import java.util.List;

public interface GodCard {
    String getName();
    default List<Space> getAvailableMoves(Board board, Worker worker) {
        return board.getAvailableMoves(worker);
    }

    default List<Space> getAvailableBuilds(Board board, Worker worker, Space firstBuild) {
        return board.getBuildableSpaces(worker);
    }

    default boolean checkAdditionalCondition(Space from, Space to) {
        return false;
    }
}
