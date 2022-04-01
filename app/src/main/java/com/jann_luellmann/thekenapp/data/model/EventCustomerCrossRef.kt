package com.jann_luellmann.thekenapp.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["eventId", "customerId"])
class EventCustomerCrossRef(
    var eventId: Long = 0,
    var customerId: Long = 0
)