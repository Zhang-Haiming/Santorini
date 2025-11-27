import React from 'react'
import { Cell } from './game'

/**
 * Props for BoardCell component
 */
interface Props {
  cell: Cell
}

/**
 * BoardCell component - renders a single cell on the board
 * Follows the tic-tac-toe pattern but enhanced for Santorini
 */
class BoardCell extends React.Component<Props> {
  render (): React.ReactNode {
    const { cell } = this.props

    // Build CSS classes based on cell state
    const classes = ['cell']

    // Add 'playable' class if cell is clickable
    // This matches the tic-tac-toe pattern
    if (cell.playable) {
      classes.push('playable')
    }

    // Add player-specific class if occupied\n    if (cell.occupiedBy != null) {\n      classes.push('occupied')\n      classes.push(cell.occupiedBy.toLowerCase()) // 'artemis' or 'demeter'\n    }

    // Add dome class if has dome
    if (cell.hasDome) {
      classes.push('has-dome')
    }

    return (
      <div className={classes.join(' ')}>
        {/* Display the text from backend */}
        {/* This is just like tic-tac-toe displaying "X" or "O" */}
        <div className='cell-text'>
          {cell.text}
        </div>

        {/* Optional: Visual tower indicator */}
        {cell.towerLevel > 0 && (
          <div className='tower-blocks'>
            {/* Show blocks for each tower level */}
            {Array.from({ length: cell.towerLevel }).map((_, i) => (
              <div key={i} className={`tower-block level-${i + 1}`} />
            ))}
          </div>
        )}
      </div>
    )
  }
}

export default BoardCell
