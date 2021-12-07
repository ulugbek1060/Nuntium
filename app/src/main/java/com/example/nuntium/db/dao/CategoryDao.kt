package com.example.nuntium.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.nuntium.db.entity.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertItem(category: Category)

    @Insert(onConflict = REPLACE)
    suspend fun insertOrUpdate(category: List<Category>)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM category ORDER BY type ASC")
    fun getLiveData(): LiveData<List<Category>>

    @Query("SELECT * FROM category ORDER BY type ASC")
    suspend fun getList(): List<Category>

    @Query("SELECT COUNT(isHave) FROM category WHERE isHave = 1")
    suspend fun getListCount(): Int

    @Query("DELETE FROM category")
    suspend fun deleteAllData()

}