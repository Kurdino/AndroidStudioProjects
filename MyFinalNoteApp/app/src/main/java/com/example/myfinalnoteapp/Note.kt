package com.example.myfinalnoteapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Note(
    val id: Int,
    var title: String,
    var description: String,
    val check: MutableState<Boolean> = mutableStateOf(false)

)



@Composable
fun NoteApp() {
    val myNavController = rememberNavController()
    val noteList = remember { mutableStateListOf<Note>() }

    NavHost(navController = myNavController, startDestination = "noteList")
    {
        composable("noteList") { NoteListScreen(myNavController, noteList) }
        composable("addNote") { AddNoteScreen(myNavController, noteList) }
        composable("editNote/{itemId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("itemId")?.toIntOrNull()
            val note = noteList.find { it.id == noteId }
            note?.let { EditNoteScreen(myNavController, it) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(navController: NavController, noteList: MutableList<Note>) {

    Scaffold(
         topBar =
         {
             TopAppBar(title = {
                 Text(

                     text = "My Notes",
                     fontSize = 24.sp,
                     fontWeight = FontWeight.Thin,
                     modifier = Modifier
                         .fillMaxWidth(),
                     textAlign = TextAlign.Center

                 )}
             )
         },
        floatingActionButton =
        {
            FloatingActionButton(onClick = { navController.navigate("addNote") }) {
                Icon(Icons.Filled.AddCircle, contentDescription = "Add Note")
            }
        }


    ){ padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(noteList) { item ->
                ListItem(
                    leadingContent = {
                        Checkbox(
                            checked = item.check.value,
                            onCheckedChange = {
                                item.check.value = !item.check.value
                            })},
                    headlineContent = { Text(item.title) },
                    supportingContent = { Text(item.description)},
                    trailingContent = {
                        Row {
                            IconButton(
                                onClick = { navController.navigate("editNote/${item.id}") }
                            ) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit Note")
                            }
                            IconButton(
                                onClick = { noteList.remove(item) }
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete Note")
                            }
                        }
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(navController: NavController, noteList: MutableList<Note>) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showTitleError by remember { mutableStateOf(false) }
    var showDescriptionError by remember { mutableStateOf(false) }

    Scaffold(

        topBar =
        {
            TopAppBar(
                title =
                {
                    Text(

                        text = "Create a new note",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        //color = Color.White,
                    )
                },
                navigationIcon =
                {
                    IconButton(
                        onClick =
                        { navController.popBackStack() })
                    {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }

            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
           horizontalAlignment = Alignment.CenterHorizontally
              )
        {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = title,
                onValueChange = {
                    title = it
                    showTitleError = !isTitleValid(title)},
                label = { Text("Title") },
                isError = showTitleError
            )
            if(showTitleError){
                Text(
                    text = "Title must be between 3 and 50 characters",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = description,
                onValueChange = {
                    description = it
                    showDescriptionError = !isDescriptionValid(it)},
                label = { Text("Description") },
                isError = showDescriptionError

            )

            if(showDescriptionError) {
                Text(
                    text = "Description must be between 3 and 120 characters",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (isTitleValid(title) && isDescriptionValid(description)) {
                    noteList.add(Note(id = noteList.size, title = title, description = description))
                    navController.popBackStack()
                } else {
                    showTitleError = !isTitleValid(title)
                    showDescriptionError = !isDescriptionValid(description)
                }
            }, colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text("Add note")
            }
            Spacer(modifier = Modifier.height(30.dp))

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController, noteList: Note) {
    var title by remember { mutableStateOf(noteList.title) }
    var description by remember { mutableStateOf(noteList.description) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Edit note",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Details") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    noteList.title = title
                    noteList.description = description
                    navController.popBackStack()
                }
            }, colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "Save note")
            }
        }
    }
}
private fun isTitleValid(title: String): Boolean {
    return title.isNotBlank() && title.length in 3..50
}

private fun isDescriptionValid(description: String): Boolean {
    return description.isNotBlank() && description.length  <= 120
}