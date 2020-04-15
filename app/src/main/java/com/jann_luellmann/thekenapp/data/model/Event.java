package com.jann_luellmann.thekenapp.data.model;

import com.jann_luellmann.thekenapp.data.util.Editable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
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
@Entity
public class Event {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "name")
    @Editable
    private String name;

    @ColumnInfo(name = "date")
    @Editable
    private Date date;

    @ColumnInfo(name = "total")
    private long total;

    @NonNull
    @Override
    public String toString() {
        return name + "\n" + DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.GERMANY).format(date);
    }
}
