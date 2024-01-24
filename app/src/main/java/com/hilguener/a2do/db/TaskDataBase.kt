package com.hilguener.a2do.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hilguener.a2do.dao.TaskDAO
import com.hilguener.a2do.di.ApplicationScope
import com.hilguener.a2do.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO

    class Callback @Inject constructor(
        private val database: Provider<TaskDataBase>,
       @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()
            applicationScope.launch {
                dao.insert(Task("Jogar bola", "Pessoal", false, false, false))
                dao.insert(Task("Tomar banho", "Pessoal", false, false, false))
                dao.insert(Task("Fazer relatório", "Trabalho", false, false, false))
                dao.insert(Task("Jogar video game", "Lazer", false, false, false))
                dao.insert(Task("Levar cachorro para passear", "Lazer", false, false, false))

            }
        }
    }
}