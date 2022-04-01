package com.jann_luellmann.thekenapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["customerId", "drinkId"])
data class Bought(
    var eventId: Long = 0,
    var customerId: Long = 0,
    var drinkId: Long = 0,

    @ColumnInfo(name = "amount")
    var amount: Int = 0
)