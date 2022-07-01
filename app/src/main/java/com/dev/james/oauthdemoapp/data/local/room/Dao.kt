package com.dev.james.oauthdemoapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories( categoriesList : List<CategoryDiagramEntity>)

    @Query("SELECT * FROM category_table")
    suspend fun getCategoriesList() : List<CategoryDiagramEntity>

    @Query("DELETE FROM category_table")
    suspend fun deleteAllCategories()
}