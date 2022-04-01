package com.jann_luellmann.thekenapp.data.model.relationship

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.EventDrinkCrossRef

class EventWithDrinks {
    @Embedded
    var event: Event? = null

    @Relation(
        parentColumn = "eventId", entityColumn = "drinkId", associateBy = Junction(
            EventDrinkCrossRef::class
        )
    )
    var drinks: MutableList<Drink> = mutableListOf()
}