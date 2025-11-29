package edu.cmu.cs214.hw2.godcard;
import edu.cmu.cs214.hw2.Space;

public class PanCard implements GodCard { 
    @Override
    public boolean checkAdditionalCondition(Space from, Space to) {
        // Pan's special win condition: move down two or more levels
        if (from.getTowerLevel() - to.getTowerLevel() >= 2) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "Pan";
    }
}