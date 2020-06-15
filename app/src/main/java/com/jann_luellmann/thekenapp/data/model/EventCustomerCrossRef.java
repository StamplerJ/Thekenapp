package com.jann_luellmann.thekenapp.data.model;

import androidx.room.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(primaryKeys = {"eventId", "customerId"})
public class EventCustomerCrossRef {

    private long eventId;
    private long customerId;
}
