package com.example.nuntium.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
class Category(
    @NotNull
    @PrimaryKey
    val type: String,
    var isHave: Boolean
)