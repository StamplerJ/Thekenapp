package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.EventDrinkCrossRef;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface EventDrinkCrossDAO {

    @Transaction
    @Query("SELECT * FROM eventDrinkCrossRef")
    LiveData<List<EventDrinkCrossRef>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(EventDrinkCrossRef... eventDrinkCrossRefs);
}
