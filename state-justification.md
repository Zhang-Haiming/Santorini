# Justification for handling state

Below, describe where you stored each of the following states and justify your answers with design principles/goals/heuristics/patterns. Discuss the alternatives and trade-offs you considered during your design process.

## Players

I create a **Player** struct to represent each player in the game. Players are stored in an array in the **Game** class. This allows for easy access to player information and simplifies turn management.

## Current player

The current **Player** is tracked by an integer index (**currentPlayerIndex**) in the **Game** class. This index points to the current player in the players array. This approach is simple and straightforward, allowing for easy turn rotation.

## Worker locations

Each **Worker** has a position attribute referencing a **Space**.
Justification: I want to get detailed position info(whether have dome, which level,...) each time I refer a *position*. So I store position in Worker as a **Space**. The Space will tracks its occupant, level and dome. This way, I can get all the info I need from Space directly. The row and column of position can be accessed through Space.

## Towers

Tower states (levels, domes) are stored in each Space via buildLevel and hasDome attributes.
Justification: This localizes tower information to the board’s spaces, supporting high cohesion and easy querying of board state. It also enables efficient updates when building or placing domes.

## Winner

The index winner is stored in the **Game** class when a player wins. This allows for easy access to the winning player information and simplifies end-game handling.

## TurnPhase

TurnPhase is stored as an enum in the **Game** class. This allows for clear representation of the game state and simplifies turn management.

## Available moves/builds

I store the available moves/builds as a list of **Space** in the **Game** class. This allows for easy access to valid moves/builds during a player's turn and can highlight the playable spaces in the front-end.

## God cards

God cards are stored in an array in the **Game** class, with each index corresponding to a player. This allows for easy access to each player's god card and decouple god card logic from player logic. Only **Game** needs to know which god card belongs to which player and manage usage during turns.

## Design goals/principles/heuristics considered

1. **Single Responsibility Principle**: Each class has a clear responsibility (e.g., Game manages overall state, Player manages player operations, Worker managers specific move, Board manages tower build).

2. **High Cohesion**: Related data and behaviors are grouped together (e.g., Space manages tower state, Player manages workers).

3. **Low Coupling**: Classes interact with as less knowledge of each other's internals as possible, minimizing dependencies. For example, Game interacts directly with GodCard, not going through Player.

4. **Encapsulation**: Most class internal states are hidden and accessed through getter methods.

## Alternatives considered and analysis of trade-offs

1. **Storing current player as Player reference**: This would allow simple and direct access to the current player but complicates player turn. Since there are only two players in the game, using an index is simpler and more efficient.

2. **Storing worker positions as (row:int, col:int)**: This would simplify position tracking and more straightforward, but would require additional logic to manage worker state and interactions with the board.

3. **Storing tower states in a separate Tower class**: This would encapsulate tower logic but would add complexity. Since in Santorini, towers are not belonged to specific player, there is no need to create class for them. Storing tower state in Space is more efficient.
