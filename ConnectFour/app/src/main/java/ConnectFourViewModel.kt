package com.example.connectfour

// ConnectFourViewModel.kt
class ConnectFourViewModel : ViewModel() {

    private val gameBoard = GameBoard()

    fun onColumnClicked(column: Int) {
        val currentPlayer = getCurrentPlayer() // Logic to determine current player
        val success = gameBoard.dropDisc(column, currentPlayer)
        if (success) {
            // Update UI, check for win/draw, switch player, etc.
        } else {
            // Handle column full scenario (e.g., show a message)
        }
    }

    // ... other ViewModel logic ...
}