package com.jann_luellmann.thekenapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jann_luellmann.thekenapp.data.model.Drink

@Dao
interface DrinkDAO {
    @get:Query("SELECT * FROM drink")
    val all: LiveData<List<Drink>>

    @Query("SELECT * FROM drink WHERE drinkId IN (:drinkIds)")
    fun loadAllByIds(drinkIds: LongArray): LiveData<List<Drink>>

    @Query("SELECT * FROM drink WHERE dname LIKE :name LIMIT 1")
    fun findByName(name: String): LiveData<Drink>

    @Insert
    fun insert(drink: Drink): Long

    @Insert
    fun insertAll(vararg drinks: Drink)

    @Insert
    fun insertAllBlocking(vararg drinks: Drink)

    @Update
    fun update(drink: Drink)

    @Delete
    fun delete(drink: Drink)
}