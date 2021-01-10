package com.easycodingg.easynotesyt.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Entity(
    tableName = "notes_table"
)
@Parcelize
data class Note(
    var title: String,
    var description: String,
    var created: Long = System.currentTimeMillis(),

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
): Parcelable {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(created)
}