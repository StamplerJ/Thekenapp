package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.Sold;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SoldDAO {

    @Query("SELECT * FROM sold")
    LiveData<List<Sold>> getAll();

    @Query("SELECT * FROM sold WHERE id IN (:soldIds)")
    LiveData<List<Sold>> loadAllByIds(long[] soldIds);

    @Insert
    void insertAll(Sold... solds);

    @Delete
    void delete(Sold sold);
}
