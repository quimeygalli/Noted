package com.qui.noted
import androidx.room.PrimaryKey

// The Note will be a data class because it won't contain logic and Room uses data classes instead
// of just classes. It also allows us to use functions like 'equals()', 'copy()', 'toString()', etc.

data class Note(
//    @PrimaryKey(autoGenerate = true)
    val id: Int, //? = null,
    var title: String,
    var body: String,
)