package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Game;
import edu.cmu.cs214.hw2.Space;
import edu.cmu.cs214.hw2.Worker;

public class MinotaurCard implements GodCard {
    @Override
    public String getName() {
        return "Minotaur";
    }
    
    private boolean canPushWorker(Game game, Space from, Space to) {
        if (!to.isOccupied()) {
            return false;
        }
        Worker opponentWorker = to.getOccupied();
        if(opponentWorker.getOwner().getId() == game.getCurrentPlayerIndex()) {
            return false;
        }

        int dx = to.getRow() - from.getRow();
        int dy = to.getCol() - from.getCol();
        int pushX = to.getRow() + dx;
        int pushY = to.getCol() + dy;
        // Check if the push position is within bounds and unoccupied
        if (pushX < 0 || pushX >= 5 || pushY < 0 || pushY >= 5) {
            return false;
        }
        Space pushSpace = game.getBoard().getSpace(pushX, pushY);
        if(pushSpace.isOccupied()){
            return false;
        }
        if(pushSpace.hasDome()){
            return false;
        }
        return true;
    }    
}
