package com.example.myconnect4

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ConnectFourGame(navController: NavHostController, model: GameModel, gameId: String?) {
    val gameState = remember { mutableStateOf(GameState()) }
    val (winner, setWinner) = remember { mutableStateOf<Disc?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Connect Four",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        BoardScreen(gameState = gameState.value) { column ->
            if (gameState.value.placeDisc(column, context)) {
                setWinner(gameState.value.winner)
            }
        }

        if (winner != null) {
            Toast.makeText(context, "${winner.name} wins!", Toast.LENGTH_LONG).show()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {
                gameState.value.reset()
                setWinner(null)
            }) {
                Text("Reset Game")
            }

            //Button(onClick = { navController.navigate("welcome") }) {
            //    Text("Back to Welcome")
            // }
        }
    }
}

