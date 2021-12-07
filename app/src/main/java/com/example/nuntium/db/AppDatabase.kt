package com.example.nuntium.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nuntium.response.Article
import com.example.nuntium.db.converter.Converters
import com.example.nuntium.db.dao.AppDao
import com.example.nuntium.db.dao.CategoryDao
import com.example.nuntium.db.entity.Category

@TypeConverters(Converters::class)
@Database(entities = [Article::class, Category::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    abstract fun getCategoryDao(): CategoryDao

}