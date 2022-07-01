package com.dev.james.oauthdemoapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryDiagramEntity::class] , version = 1 , exportSchema = false)
abstract class WillManagerDatabase : RoomDatabase() {
    abstract fun getDao() : Dao
}