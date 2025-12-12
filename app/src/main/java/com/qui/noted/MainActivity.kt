package com.qui.noted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qui.noted.Room.NoteDatabase
import com.qui.noted.Room.NoteEntity
import com.qui.noted.Room.NoteRepository
import com.qui.noted.Room.NoteVMFactory
import com.qui.noted.ui.theme.ui.theme.CardBodyBackgroundColor
import com.qui.noted.ui.theme.ui.theme.CardTitleBackgroundColor
import com.qui.noted.ui.theme.ui.theme.FABColor
import com.qui.noted.ui.theme.ui.theme.LightTextColor
import com.qui.noted.ui.theme.ui.theme.NotedTheme
import com.qui.noted.ui.theme.PreviewParameterSamples.NoteData
import com.qui.noted.ui.theme.PreviewParameterSamples.SampleNoteDataProvider
import com.qui.noted.ui.theme.ui.theme.White

/* Font Family */

val onestFontFamily = FontFamily(
    Font(R.font.onest_font, FontWeight.Normal)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        val db = NoteDatabase.getDatabase(applicationContext)
        val repo = NoteRepository(db.dao())
        val factory = NoteVMFactory(repo)

        setContent {
            NotedTheme {
                val vm: NoteVM = viewModel(factory = factory)
                NotedApp(vm)
            }
        }
    }
}


@Composable
fun NotedApp(vm: NoteVM) {
    val nav = rememberNavController()
    val notes by vm.notes.collectAsState() // List<NoteEntity>

    NavHost(
        navController = nav,
        startDestination = "menu"
    ) {
        composable("menu") {
            NoteMenu(nav = nav, vm = vm)
        }

        composable("note/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")!!.toInt()
            IndividualNote(nav = nav, vm = vm, id = id)
        }
    }
}

/**


            Note selection menu


 **/

//region

@Composable
fun NoteMenu(nav: NavController, vm: NoteVM) {
    val notes by vm.notes.collectAsState()

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(60.dp),
                containerColor = FABColor,
                onClick = { nav.navigate("note/0") }, // navigate to "new note" screen
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_note),
                    contentDescription = "Add note"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .weight(25f)
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GreetingBar()
            }
            Row(modifier = Modifier.weight(75f)) {
                NoteGrid(nav = nav, notes = notes)
            }
        }
    }
}

@Composable
fun GreetingBar() {
    Text(
        text = "Welcome back",
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        fontFamily = onestFontFamily,
        lineHeight = 35.sp
    )
}

@Composable
fun NoteGrid(nav: NavController, notes: List<NoteEntity>) {
    LazyVerticalGrid(
        modifier = Modifier.background(color = Color.White),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(notes) { note ->
            GridItem(nav = nav, note = note)
        }
    }
}

@Composable
fun GridItem(nav: NavController, note: NoteEntity) {
    val colors = listOf(Color.Transparent, Color.LightGray)

    Card(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable {
                // safe navigate only if id exists
                val id = note.id ?: return@clickable
                nav.navigate("note/$id")
            }
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(18.dp))) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = (note.title ?: "").take(40),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = (note.body ?: "").take(200),
                    color = LightTextColor
                )
            }

            // Gradient overlay
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = colors,
                            startY = 100f,
                            endY = 500f
                        )
                    )
            )
        }
    }
}

//endregion

/**


            Individual note screen


 **/




//region
@Composable
fun IndividualNote(nav: NavController, vm: NoteVM, id: Int) {
    val notes by vm.notes.collectAsState()
    if (id == 0) {
        var title by remember { mutableStateOf("") }
        var body by remember { mutableStateOf("") }

        Scaffold(
            modifier = Modifier,
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.size(60.dp),
                    containerColor = FABColor,
                    onClick = {
                        val toSave = NoteEntity(
                            id = null,
                            title = title,
                            body = body
                        )
                        vm.saveNote(toSave)
                        nav.popBackStack()
                    },
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "Save note"
                    )
                }
            }
        ){
            Column(
                modifier = Modifier
                    .background(color = White)
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Title
                Card(
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardTitleBackgroundColor)
                ) {
                    TextField(
                        modifier = Modifier.padding(10.dp),
                        value = title,
                        onValueChange = { title = it },
                        placeholder = {
                            Text(
                                text = "Title",
                                fontSize = 32.sp,
                                fontFamily = onestFontFamily
                            )
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 32.sp,
                            fontFamily = onestFontFamily
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Body
                Card(
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBodyBackgroundColor)
                ) {
                    LazyColumn {
                        item {
                            TextField(
                                modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                                value = body,
                                onValueChange = { body = it },
                                placeholder = {
                                    Text(
                                        text = "What were your plans today?",
                                        fontSize = 20.sp,
                                        fontFamily = onestFontFamily
                                    )
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 20.sp,
                                    fontFamily = onestFontFamily
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        }

        return
    }

    // If note exists, load it by its id
    val note = notes.firstOrNull { it.id == id }
    if (note == null) {
        // show not found
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("Note not found")
        }
        return
    }

    var title by remember { mutableStateOf(note.title ?: "") }
    var body by remember { mutableStateOf(note.body ?: "") }

    Column(
        modifier = Modifier
            .background(color = White)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardTitleBackgroundColor)
        ) {
            TextField(
                modifier = Modifier.padding(10.dp),
                value = title,
                onValueChange = { title = it },
                placeholder = { Text(text = "Title", fontSize = 32.sp, fontFamily = onestFontFamily) },
                textStyle = LocalTextStyle.current.copy(fontSize = 32.sp, fontFamily = onestFontFamily),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBodyBackgroundColor)
        ) {
            LazyColumn {
                item {
                    TextField(
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                        value = body,
                        onValueChange = { body = it },
                        placeholder = { Text(text = "What were your plans today?", fontSize = 20.sp, fontFamily = onestFontFamily) },
                        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, fontFamily = onestFontFamily),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val updated = note.copy(title = title, body = body)
            vm.saveNote(updated)
            nav.popBackStack()
        }) {
            Text("Save")
        }
    }

    // keep UI updated
    LaunchedEffect(title, body) {
    }
}
//endregion