import React from 'react'
import 'animate.css' // Import animate.css library
import './App.css' // Import CSS file
import { GameState, Cell } from './game'
import BoardCell from './Cell'

/**
 * Props interface - empty for now
 */
interface Props { }

/**
 * Main App component for Santorini game
 * Follows the exact pattern from tic-tac-toe example
 */
class App extends React.Component<Props, GameState> {
  private initialized: boolean = false

  /**
   * Constructor - initialize state
   */
  constructor (props: Props) {
    super(props)
    /**
     * State matches the JSON from backend:
     * {
     *   cells: [...],
     *   message: "Artemis - place your worker 1.",
     *   winner: null
     * }
     */
    this.state = {
      cells: [],
      message: '',
      winner: null
    }
  }

  /**
   * Start a new game
   * Same pattern as tic-tac-toe
   */
  newGame = async () => {
    const board = document.getElementById('board')
    if (board != null) {
      // Add fade out animation
      board.classList.add('animate__animated', 'animate__fadeOut')
      setTimeout(async () => {
        // Fetch new game state from backend
        const response = await fetch('/newgame')
        const json = await response.json()

        // Update entire state from JSON response
        this.setState(json)

        // Add fade in animation
        board.classList.remove('animate__fadeOut')
        board.classList.add('animate__fadeIn')
      }, 300)
    } else {
      // If no board element yet, just fetch
      const response = await fetch('/newgame')
      const json = await response.json()
      this.setState(json)
    }
  }

  /**
   * Handle cell click - same pattern as tic-tac-toe play()
   * This function creates a click handler for a specific cell
   *
   * @param row - Row index (0-4)
   * @param col - Column index (0-4)
   * @returns Click handler function
   */
  play (row: number, col: number): React.MouseEventHandler {
    return async (e) => {
      // Prevent default link behavior
      e.preventDefault()

      // Send play request to backend
      // Backend determines what to do based on currentPhase
      const response = await fetch(`/play?x=${row}&y=${col}`)
      const json = await response.json()

      // Update entire state from JSON response
      this.setState(json)
    }
  }

  /**
   * Create a single cell element
   * Same pattern as tic-tac-toe
   *
   * @param cell - Cell data from state
   * @param index - Array index for key
   */
  createCell (cell: Cell, index: number): React.ReactNode {
    if (cell.playable) {
      // Playable cell - wrap in clickable link
      return (
        <div key={index}>
          <a
            href='/'
            onClick={this.play(cell.row, cell.col)}
            title={`Position (${cell.row}, ${cell.col}) - Level ${cell.towerLevel}`}
          >
            <BoardCell cell={cell} />
          </a>
        </div>
      )
    } else {
      // Non-playable cell - just display
      return (
        <div key={index} className='animate__animated animate__fadeIn'>
          <BoardCell cell={cell} />
        </div>
      )
    }
  }

  /**
   * Called after component mounts
   * Initialize the game
   * Same pattern as tic-tac-toe
   */
  componentDidMount (): void {
    if (!this.initialized) {
      this.newGame()
      this.initialized = true
    }
  }

  /**
   * Render the UI
   * Same pattern as tic-tac-toe but with Santorini-specific elements
   */
  render (): React.ReactNode {
    return (
      <div className='App animate__animated animate__fadeIn'>
        {/* Game title */}
        <div className='game-title'>
          <h1>🏛️ Santorini 🏛️</h1>
        </div>

        {/* Message bar - shows current instruction */}
        <div id='message' className='animate__animated animate__slideInDown'>
          {this.state.message || 'Loading...'}
        </div>

        {/* Game board - 5x5 grid */}
        <div id='board' className='animate__animated animate__slideInDown'>
          {this.state.cells.map((cell, i) => this.createCell(cell, i))}
        </div>

        {/* Winner banner - only shows when game is over */}
        {this.state.winner && (
          <div className='winner-banner animate__animated animate__bounceIn'>
            🎉 {this.state.winner} Wins! 🎉
          </div>
        )}

        {/* Control buttons */}
        <div id='bottombar' className='animate__animated animate__slideInUp'>
          <button onClick={this.newGame}>
            🔄 New Game
          </button>
        </div>

        {/* Instructions */}
        <div className='instructions'>
          <h3>How to Play:</h3>
          <ol>
            <li><strong>Setup:</strong> Each player places 2 workers</li>
            <li><strong>Turn:</strong> Select worker → Move → Build</li>
            <li><strong>Win:</strong> Move a worker up to level 3</li>
            <li><strong>Lose:</strong> Cannot make any valid moves</li>
          </ol>
          <div className='player-legend'>
            <div className='player-item artemis'>
              <span className='symbol'>A</span> = Artemis (Red)
            </div>
            <div className='player-item demeter'>
              <span className='symbol'>D</span> = Demeter (Blue)
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default App
