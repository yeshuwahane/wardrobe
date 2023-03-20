package com.alien.ward.repo

import androidx.lifecycle.LiveData
import com.alien.ward.database.dao.MyDao
import com.alien.ward.database.entity.TopEntity

class MyRepository(private val myDao: MyDao) {
    val getTopWear: LiveData<TopEntity> = myDao.getTopWear()

    suspend fun addTopWear(topEntity: TopEntity){
        myDao.addTopWear(topEntity)
    }

}