/**
 * TypeScript interfaces for Santorini game state
 * These match the JSON structure returned by your backend
 */

interface GameState {
  cells: Cell[] // Array of 25 cells (5x5 grid)
  message: string // Current instruction message
  winner: string | null // Winner name or null
  availableGodCards: string[] | null // List of available gods for selection
  player1God: string | null // Player 1's selected god
  player2God: string | null // Player 2's selected god
  currentPlayerIndex: number // Current player
  currentPhase: string // Current phase of the game
}

interface Cell {
  row: number // Row index (0-4)
  col: number // Column index (0-4)
  text: string // Display text (e.g., "2A" = level 2 with Artemis)
  playable: boolean // Whether this cell can be clicked
  towerLevel: number // Tower height (0-3)
  hasDome: boolean // Whether cell has a dome
  occupiedBy: string | null // "Artemis", "Demeter", or null
}

export type { GameState, Cell }
