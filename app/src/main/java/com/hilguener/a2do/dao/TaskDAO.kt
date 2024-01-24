package com.hilguener.a2do.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hilguener.a2do.model.Task
import com.hilguener.a2do.ui.Sort
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    fun getTasks(query: String, sortOrder: Sort, hideCompleted: Boolean):Flow<List<Task>> =
        when(sortOrder){
            Sort.BY_NAME -> getTasksSortedByName(query, hideCompleted)
            Sort.BY_CATEGORY -> getTasksSortedByCategory(query, hideCompleted)
        }
    @Query("SELECT * FROM task_table WHERE(completed != :hideCompleted OR completed = 0) AND title LIKE '%' || :searchQuery || '%' ORDER BY isImportant DESC, title")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean ): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE(completed != :hideCompleted OR completed = 0) AND title LIKE '%' || :searchQuery || '%' ORDER BY isImportant DESC, category")
    fun getTasksSortedByCategory(searchQuery: String, hideCompleted: Boolean ): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)
}