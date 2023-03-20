package com.alien.ward.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alien.ward.database.entity.TopEntity

@Dao
interface MyDao {
    @Query("SELECT * FROM top_wear ORDER BY id ASC")
    fun getTopWear(): LiveData<TopEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopWear(topEntity: TopEntity)
}