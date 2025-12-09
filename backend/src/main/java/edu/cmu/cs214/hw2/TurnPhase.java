package edu.cmu.cs214.hw2;

/**
 * Enum representing the different phases of a player's turn
 */
public enum TurnPhase {
    PLAYER1_CHOOSE_GODCARD,
    PLAYER2_CHOOSE_GODCARD,
    PLAYER1_INITIAL_WORKER1,
    PLAYER1_INITIAL_WORKER2,
    PLAYER2_INITIAL_WORKER1,
    PLAYER2_INITIAL_WORKER2,
    CHOOSE_WORKER,
    MOVE,
    BUILD,
    SECOND_BUILD,
    END_GAME,
}
