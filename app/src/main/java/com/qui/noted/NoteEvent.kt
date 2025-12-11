package com.qui.noted

sealed interface NoteEvent {
    object SaveNote: NoteEvent
    data class SetTitle(val title: String): NoteEvent
    data class SetBody(val note: String): NoteEvent
}