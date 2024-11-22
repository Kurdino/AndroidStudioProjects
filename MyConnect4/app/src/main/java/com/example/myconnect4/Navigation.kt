package com.example.myconnect4

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow




data class Player(
    var name:String = ""
)


data class Game(
    var gameBoard: List<Int> = List(9){0},
    var gameState: String = "invite",
    var player1Id: String = "",
    var player2Id: String = ""
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val model = GameModel()
    model.initGame()
    model.playerMap.asStateFlow().collectAsStateWithLifecycle()
    model.gameMap.asStateFlow().collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = "player") {
        //composable("welcome") { WelcomeScreen(navController) }
        //composable("do") { ConnectFourGame(navController) }
        composable("player") { NewPlayerScreen(navController, model) }
        composable("lobby") { LobbyScreen(navController, model) }
        composable("game{gameId}") {backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            ConnectFourGame(navController, model, gameId)
        }
    }
}
/*@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Connect Four",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = { navController.navigate("do") },
        ) {
            Text("Start Game")
        }
    }
}*/

@Composable
fun NewPlayerScreen(navController: NavController, model: GameModel) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(navController: NavController, model: GameModel) {

}

