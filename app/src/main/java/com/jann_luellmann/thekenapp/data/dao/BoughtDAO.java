package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.Bought;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BoughtDAO {

    @Query("SELECT * FROM bought")
    LiveData<List<Bought>> getAll();

    @Query("SELECT * FROM bought WHERE id IN (:boughtIds)")
    LiveData<List<Bought>> loadAllByIds(long[] boughtIds);

    @Insert
    void insertAll(Bought... boughts);

    @Delete
    void delete(Bought bought);
}
