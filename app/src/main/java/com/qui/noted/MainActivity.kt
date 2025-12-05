package com.qui.noted

import android.os.Bundle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qui.noted.ui.theme.CardBodyBackground
import com.qui.noted.ui.theme.CardBorder
import com.qui.noted.ui.theme.CardTitleBackground
import com.qui.noted.ui.theme.LightText
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

@Composable
fun NotedApp() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = "menu"
    ) {
        composable("menu") {NoteMenu(nav)}
        composable("note") {Note(nav)}
    }
}

/**

            Note selection menu

 **/

@Composable
fun NoteMenu(nav: NavController) {
    Column(
        modifier = Modifier
            .background(Color.White)
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
            NoteGrid(nav)
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
fun NoteGrid(nav: NavController) {
    val itemsList = (0..100).toList()

    LazyVerticalGrid(
        modifier = Modifier
            .background(color = Color.White),
        columns = GridCells.Adaptive(minSize = 116.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(itemsList) { item ->
            GridItem(item = item, nav = nav)
        }
    }
}

@Composable
fun GridItem(item: Int, nav: NavController) {

    val colors = listOf(
        Color.Transparent,
        Color.LightGray
    )

    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .clickable { nav.navigate("note") }
        ,
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
                    text = "Note $item",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = stringResource(R.string.note_sample),
                    color = LightText
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

/**

            Individual note blueprint

 **/

@Composable
fun Note(nav: NavController) {
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
                containerColor = CardTitleBackground
            )
        ) {
            Title()
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            colors = CardDefaults.cardColors(containerColor = CardBodyBackground),
            border = BorderStroke(2.dp, color = CardBorder)
        ) {
            Body()
        }

    }
}

@Composable
fun Title() {
    Text(
        modifier = Modifier
            .padding(10.dp),
        text = "Title Example",
        fontSize = 40.sp,
        fontWeight = FontWeight.Light,
        fontFamily = onestFontFamily
    )
}

@Composable
fun Body() {
    LazyColumn {
        item { Spacer(modifier = Modifier.height(5.dp)) }
        item {
            Text(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp),
                text = stringResource(R.string.note_sample),
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                fontFamily = onestFontFamily
            )
        }
    }
}