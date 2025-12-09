package com.qui.noted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qui.noted.ui.theme.CardBodyBackgroundColor
import com.qui.noted.ui.theme.CardBorderColor
import com.qui.noted.ui.theme.CardTitleBackgroundColor
import com.qui.noted.ui.theme.FABColor
import com.qui.noted.ui.theme.LightTextColor
import com.qui.noted.ui.theme.NotedTheme
import com.qui.noted.ui.theme.White

/* Font Family */

val onestFontFamily = FontFamily(
    Font(R.font.onest_font, FontWeight.Normal)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotedTheme {
                NotedApp()
            }
        }
    }
}

@Preview
@Composable
fun NotedApp() {
    val nav = rememberNavController()

    val notes = remember {
        mutableStateListOf<Note>()
    }

    NavHost(
        navController = nav,
        startDestination = "menu"
    ) {
        composable("menu") {
            NoteMenu(
                nav = nav,
                notes = notes
            )
        }

        // 'note/{id}' defines the route to get to the notes. Refer to 'GridItem->Card->modifier->.clickable' to see it in action.
        composable("note/{id}") { backStackEntry ->
            Note(
                nav = nav,
                notes = notes,
                id = backStackEntry.arguments?.getString("id")!!
                    .toInt() // Arguments are always strings
            )
        }
    }
}

/**


Note creation

 **/

fun createNewNote(nav: NavController, notes: MutableList<Note>) {

    // For a new note select the max id and add 1. If its the first note make it 0 and add 1
    val newId = (notes.maxOfOrNull { it.id } ?: 0) + 1

    val newNote: Note = Note(
        id = newId,
        title = "",
        body = ""
    )

    notes.add(newNote)

    nav.navigate("note/$newId")
}

/**

Note selection menu

 **/

//region

@Composable
fun NoteMenu(nav: NavController, notes: SnapshotStateList<Note>) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .size(60.dp),
                containerColor = FABColor,
                onClick = {
                    createNewNote(nav, notes)
                },
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(
                    painter = painterResource(
                        R.drawable.ic_add_note
                    ),
                    contentDescription = "Add note",
                    modifier = Modifier
                )
            }
        }
    ) { it: PaddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues = PaddingValues(0.dp))
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
            Row(
                modifier = Modifier
                    .weight(75f)
            ) {
                NoteGrid(
                    nav = nav,
                    notes = notes
                )
            }
        }
    }
}

@Composable
fun GreetingBar() {
    Text(
        text = "What's it gonna be this time, Quimey?",
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        fontFamily = onestFontFamily,
        lineHeight = 35.sp
    )
}

@Composable
fun NoteGrid(nav: NavController, notes: List<Note>) {

    LazyVerticalGrid(
        modifier = Modifier
            .background(color = Color.White),
        columns = GridCells.Adaptive(minSize = 116.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(notes) { note ->
            GridItem(note = note, nav = nav)
        }
    }
}

@Composable
fun GridItem(nav: NavController, note: Note) {

    val colors = listOf(
        Color.Transparent,
        Color.LightGray
    )

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .clickable { nav.navigate("note/${note.id}") },
        shape = RoundedCornerShape(18.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${note.title}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "${note.body.take(200)}",
                    color = LightTextColor
                )
            }

            // Gradient

            // The gradient box is on the bottom because compose is just kotlin, so it's declarative
            // This box will be drawn last, so it will be on top
            Box(
                modifier = Modifier
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

Individual note blueprint

 **/

//region
@Composable
fun Note(nav: NavController, notes: SnapshotStateList<Note>, id: Int) {
    val note = notes.first { it.id == id } // note will be the first element to have a coinciding id

    var title by remember { mutableStateOf(note.title) }
    var body by remember { mutableStateOf(note.body) }

    Column(
        modifier = Modifier
            .background(color = White)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            colors = CardDefaults.cardColors(
                containerColor = CardTitleBackgroundColor
            )
        ) {
            TextField(
                modifier = Modifier.padding(10.dp),
                value = title,
                onValueChange = { title = it },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 40.sp,
                    fontFamily = onestFontFamily
                ),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            colors = CardDefaults.cardColors(containerColor = CardBodyBackgroundColor),
            border = BorderStroke(2.dp, color = CardBorderColor)
        ) {
            LazyColumn {
                item {
                    TextField(
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                        value = body,
                        onValueChange = { body = it },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            fontFamily = onestFontFamily
                        ),
                    )
                }
            }
        }
    }
}

//endregion