package com.jann_luellmann.thekenapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.data.util.Editable

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true)
    var customerId: Long = 0,

    @ColumnInfo(name = "cname")
    @field:Editable(stringId = R.string.name)
    var name: String = ""
) : Comparable<Customer> {

    override fun toString(): String {
        return name
    }

    override fun compareTo(other: Customer): Int {
        return name.compareTo(other.name)
    }
}