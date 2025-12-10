package com.qui.noted.ui.theme.PreviewParameterSamples

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.qui.noted.Note

data class  NoteData (
    val id: Int = -1,
    var title: String,
    var body: String
)

class SampleNoteDataProvider : PreviewParameterProvider<NoteData> {
    override val values: Sequence<NoteData> = sequenceOf(
        NoteData(
            -1,
            "This is a sample title",
            "Lorem ipsum dolor amet blah blah blah this is the note body and it is long because its a note so it wont be as short as the titke"
        )
    )
}