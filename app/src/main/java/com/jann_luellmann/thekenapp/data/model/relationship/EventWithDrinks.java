package com.jann_luellmann.thekenapp.data.model.relationship;

import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventWithDrinks {
    @Embedded
    private Event event;

    @Relation(
            parentColumn = "id",
            entityColumn = "eventId"
    )
    private List<Drink> drinks;
}