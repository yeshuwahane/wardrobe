package com.alien.ward.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.alien.ward.database.MyDatabase
import com.alien.ward.database.entity.TopEntity
import com.alien.ward.repo.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {
    private val dao = MyDatabase.getDatabase(application).myDao()
    private val repository = MyRepository(dao)

    val getTopwear: LiveData<TopEntity> = repository.getTopWear


    fun addTops(topEntity: TopEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTopWear(topEntity)
        }
    }

}