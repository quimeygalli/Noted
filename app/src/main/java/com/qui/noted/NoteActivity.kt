package com.qui.noted


import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qui.noted.ui.theme.CardBodyBackground
import com.qui.noted.ui.theme.CardTitleBackground
import com.qui.noted.ui.theme.NotedTheme
import com.qui.noted.ui.theme.White
import java.time.temporal.TemporalQueries.offset

val loraFontFamily = FontFamily( // TODO figure out a way to implement fonts
    Font(R.font.lora_font, FontWeight.Normal)
)

class NoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                NotedTheme {
                    Note()
            }
        }
    }
}

@Preview
@Composable
fun Note() {
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
            border = BorderStroke(2.dp, Color.Gray)
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
        fontFamily = loraFontFamily
    )
}

@Composable
fun Body() {
    Text(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState()),
        text = "\n" + stringResource(R.string.note_sample),
        fontSize = 20.sp,
        fontWeight = FontWeight.W500,
        fontFamily = loraFontFamily
    )
}
