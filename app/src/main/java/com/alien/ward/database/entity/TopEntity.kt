package com.alien.ward.database.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_wear")
data class TopEntity(
    val imageName: String,
    val topImage: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}