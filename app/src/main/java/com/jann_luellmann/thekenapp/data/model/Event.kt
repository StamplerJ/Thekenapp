package com.jann_luellmann.thekenapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.data.util.Editable
import java.text.DateFormat
import java.util.*

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true)
    var eventId: Long = 0,

    @ColumnInfo(name = "ename")
    @field:Editable(stringId = R.string.name)
    var name: String = "",

    @ColumnInfo(name = "date")
    @field:Editable(stringId = R.string.date)
    var date: Date? = null,

    @ColumnInfo(name = "total")
    var total: Long = 0,
) {
    override fun toString(): String {
        return """
            $name
            ${DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.GERMANY).format(date)}
            """.trimIndent()
    }
}