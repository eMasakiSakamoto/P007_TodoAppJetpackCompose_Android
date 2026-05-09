package com.free.masaki.sakamoto.todoapp.di

import android.content.Context
import androidx.room.Room
import com.free.masaki.sakamoto.todoapp.data.local.AppDatabase
import com.free.masaki.sakamoto.todoapp.data.local.TodoDao
import com.free.masaki.sakamoto.todoapp.data.repository.TodoRepositoryImpl
import com.free.masaki.sakamoto.todoapp.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todo_database",
        ).build()
    }

    @Provides
    fun provideTodoDao(database: AppDatabase): TodoDao = database.todoDao()

    @Provides
    @Singleton
    fun provideTodoRepository(impl: TodoRepositoryImpl): TodoRepository = impl
}
