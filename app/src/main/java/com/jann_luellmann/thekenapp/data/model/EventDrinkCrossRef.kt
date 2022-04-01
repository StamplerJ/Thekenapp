package com.jann_luellmann.thekenapp.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["eventId", "drinkId"])
data class EventDrinkCrossRef(
    var eventId: Long = 0,
    var drinkId: Long = 0
)