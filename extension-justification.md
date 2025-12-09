# Extension Justification

## TurnPhase

I added an enumeration **TurnPhase** to represent different phases in a player's turn (e.g., ChooseWorker, Move, Build, SecondBuild, etc.). This allows for clearer state transition and facilitates extension of optional phases like SecondBuild for specific God Cards.

## Immutable Game State

I implemented immutable game state by having game operations (```chooseGodCard()```, ```chooseWorker()```, ```move()```, ```firstBuild()```, etc.) return a new Game object rather than modifying the existing instance. This immutability makes the codebase more maintainable and facilitates implementation of new operations without introducing bugs from shared mutable state.

## God Cards

I use strategy pattern to implement God Cards. I create a GodCard interface with default methods like ```getName()```, ```getAvailableMoves()```, ```checkAdditionalCondition()``` and ```executeMove()```. Concrete god card classes (e.g., DemeterCard, MinotaurCard) can selectively override to implement their unique abilities.

This design adheres to the Open/Closed Principle: the Game class delegates execution to the god card strategies through methods like ```getAvailableMoves()``` and ```executeMove()```, allowing new god cards to be added by simply creating new classes that implement the GodCard interface without modifying any existing game logic.

## alternatives considered and analysis of trade-offs

I also considered not using design pattern, but coupling god card logic directly into the Game class. However, this would violate single responsibility principle and lead to a Game class that is hard to maintain. I may write codes like:

```java
if(currentPlayer.getGodCard() == "Demeter") {
    // Demeter specific logic
} else if (currentPlayer.getGodCard() == "Minotaur") {
    // Minotaur specific logic
} else if ...
```

which is typical example of anti-pattern and not scalable as new god cards are added.

By using strategy pattern, each god card encapsulates its own logic, leading to cleaner and more modular code. The trade-off is a slight increase in complexity due to the additional interface and classes, but this is outweighed by the benefits in maintainability and extensibility.
