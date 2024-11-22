package com.example.myconnect4

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

const val ROWS = 6
const val COLUMNS = 7

enum class Disc {
    NONE, RED, GREEN
}
class GameState {
    //val board: Array<Array<Disc>> = Array(ROWS) { Array(COLUMNS) { Disc.NONE } } // Initialize the board
    val board: List<SnapshotStateList<Disc>> = List(ROWS){
        mutableStateListOf(*Array(COLUMNS) { Disc.NONE })
    }
    var currentPlayer = Disc.RED // Start with RED player
    var winner: Disc? = null // No winner initially

    fun reset() { // Reset the game state
        for (row in board) {
            // for (col in row.indices) { // Reset each cell to NONE
            //    row[col] = Disc.NONE
            // }
            row.replaceAll { Disc.NONE }
        }
        currentPlayer = Disc.RED // Reset to RED player
        winner = null // No winner

    }
    fun placeDisc(column: Int, context: Context): Boolean { // Place a disc in the specified column
        if (column < 0 || column >= COLUMNS || winner != null) //return false // Invalid column or game is over
            return false
        for (row in ROWS - 1 downTo 0) { // Start from the bottom row
            if (board[row][column] == Disc.NONE) { // If the cell is empty
                board[row][column] = currentPlayer
                Log.d("Connect4Debug", "Placed at row $row, column $column")
                if (checkForWin(row, column)) {
                    winner = currentPlayer
                    Log.d("Connect4Debug", "Winner detected: $winner")
                }
                currentPlayer = if (currentPlayer == Disc.RED) Disc.GREEN else Disc.RED
                return true
            }
        }
        Toast.makeText(context, "Please select another column", Toast.LENGTH_LONG).show()
        Log.d("Connect4Debug", "Column full: $column")
        return false
    }

    // Checks if there's a winning sequence starting from (row, column)
    private fun checkForWin(row: Int, col: Int): Boolean {
        val directions = listOf(
            Pair(1, 0),  // Vertical
            Pair(0, 1),  // Horizontal
            Pair(1, 1),  // Diagonal /
            Pair(1, -1)  // Diagonal \
        )

        for ((dx, dy) in directions) {
            var count = 1
            for (d in listOf(-1, 1)) {
                var r = row + d * dx
                var c = col + d * dy
                while (r in 0 until ROWS && c in 0 until COLUMNS && board[r][c] == currentPlayer) {
                    count++
                    r += d * dx
                    c += d * dy
                }
            }
            if (count >= 4) {
                return true
            }
        }
        return false

    }


}

