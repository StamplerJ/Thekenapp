package com.jann_luellmann.thekenapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(primaryKeys = {"customerId", "drinkId"})
public class Bought {

    private long eventId;
    private long customerId;
    private long drinkId;

    @ColumnInfo(name = "amount")
    private int amount;
}
