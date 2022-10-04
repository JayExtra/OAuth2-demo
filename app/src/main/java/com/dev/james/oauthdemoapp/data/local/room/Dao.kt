package com.dev.james.oauthdemoapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.james.oauthdemoapp.data.local.room.entities.CategoryDiagramEntity
import com.dev.james.oauthdemoapp.data.local.room.entities.PartsEntity
import com.dev.james.oauthdemoapp.domain.models.Part

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories( categoriesList : List<CategoryDiagramEntity>)

    @Query("SELECT * FROM category_table")
    suspend fun getCategoriesList() : List<CategoryDiagramEntity>

    @Query("DELETE FROM category_table")
    suspend fun deleteAllCategories()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParts(partsList : List<PartsEntity>)

    @Query("SELECT * FROM parts_table WHERE part_parent_category_id = :catId")
    suspend fun getParts(catId : String) : List<PartsEntity>

    @Query("DELETE FROM parts_table")
    suspend fun deleteAllParts()

    @Query("UPDATE parts_table SET part_favourite_status=:value WHERE part_id=:partId")
    suspend fun partIsFavourite(value : Boolean , partId : String)

    @Query("SELECT * FROM parts_table WHERE part_favourite_status=:status")
    suspend fun getFavouriteParts(
        status : Boolean = true
    ) : List<PartsEntity>


    @Query("SELECT * FROM parts_table WHERE name LIKE '%'  || :searchQuery || '%'  ")
    suspend fun searchForPart(searchQuery : String) : List<PartsEntity>




}