package com.jann_luellmann.thekenapp.data.model.relationship;

import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.EventDrinkCrossRef;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventWithDrinks {
    @Embedded
    private Event event;

    @Relation(
            parentColumn = "eventId",
            entityColumn = "drinkId",
            associateBy = @Junction(EventDrinkCrossRef.class)
    )
    private List<Drink> drinks;
}
