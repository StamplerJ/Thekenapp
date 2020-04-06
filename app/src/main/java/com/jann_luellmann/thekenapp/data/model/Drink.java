package com.jann_luellmann.thekenapp.data.model;

import java.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
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
@Entity
public class Drink {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private long price;

    private long eventId;

    @NonNull
    @Override
    public String toString() {
        return name + ": " + priceAsText();
    }

    private String priceAsText() {
        return new BigDecimal(price).movePointLeft(2).toString() + " €";
    }
}
