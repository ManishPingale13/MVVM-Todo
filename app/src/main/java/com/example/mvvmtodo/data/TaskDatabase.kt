package com.example.mvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //do DB operations
            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Wash Dishes", completed = true))
                dao.insert(Task("Call Elon Musk", important = true))
                dao.insert(Task("Learn Dagger"))
                dao.insert(Task("Work on Flight Computer"))
                dao.insert(Task("Go to Gym"))
                dao.insert(Task("Read book", completed = true))


            }
        }
    }
}