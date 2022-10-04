package com.dev.james.oauthdemoapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.james.oauthdemoapp.data.local.room.entities.CategoryDiagramEntity
import com.dev.james.oauthdemoapp.data.local.room.entities.PartsEntity

@Database(entities = [CategoryDiagramEntity::class , PartsEntity::class] , version = 2 , exportSchema = false)
abstract class WillManagerDatabase : RoomDatabase() {
    abstract fun getDao() : Dao
}