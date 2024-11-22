package com.example.myconnect4

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement

@Composable
fun BoardScreen(gameState: GameState, onColumnClick: (Int) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {// Center the columns horizontally
        for (row in gameState.board.indices) {// Iterate through rows
            Row(
                horizontalArrangement = Arrangement.Center// Center the cells horizontally
            ) {
                for (col in gameState.board[row].indices) {
                    Box(// Create a box for each cell
                        modifier = Modifier
                            .clickable {
                                Log.d("Connect4Debug", "Column clicked: $col")
                                onColumnClick(col) }// Respond to clicks on each cell
                            .padding(4.dp) // Add padding between cells
                    ) {
                        Cell( // Display the disc in the cell
                            disc = gameState.board[row][col],
                            modifier = Modifier.size(45.dp) // Set the size of each cell
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Cell(disc: Disc, modifier: Modifier = Modifier) {
    Log.d("Connect4Debug", "Rendering cell with disc: $disc")
    Box(//
        modifier = modifier
            .background(
                color = when (disc) {// Set the background color based on the disc type
                    Disc.NONE -> Color.Gray // Empty cell
                    Disc.RED -> Color.Red// Blue disc
                    Disc.GREEN -> Color.Green // Green disc
                },
                shape = CircleShape // Round cells for a disc shape
            )
    )

}


