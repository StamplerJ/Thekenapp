package com.jann_luellmann.thekenapp.data.model;

import com.jann_luellmann.thekenapp.R;
import com.jann_luellmann.thekenapp.data.util.Editable;

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
public class Customer {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    @Editable(stringId = R.string.name)
    private String name;

    private long eventId;

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
