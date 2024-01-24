package com.hilguener.a2do.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class Task(
    val title: String,
    val category: String,
    val completed: Boolean,
    val isImportant: Boolean,
    val reminder: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
