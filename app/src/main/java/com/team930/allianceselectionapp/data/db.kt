package com.team930.allianceselectionapp.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.team930.allianceselectionapp.data.local.EventEntity
import com.team930.allianceselectionapp.data.local.EventDao
import com.team930.allianceselectionapp.data.local.PitDataDao
import com.team930.allianceselectionapp.data.local.PitDataEntity
import com.team930.allianceselectionapp.data.source.local.PickList
import com.team930.allianceselectionapp.data.source.local.PickListDao
import com.team930.allianceselectionapp.data.source.local.PickListTeamCrossRef
import com.team930.allianceselectionapp.data.local.TeamEntity
import com.team930.allianceselectionapp.data.local.TeamDao
import com.team930.allianceselectionapp.data.local.TeamEventCrossRef
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [
    TeamEntity::class,
    EventEntity::class,
    PitDataEntity::class,
    TeamEventCrossRef::class,
    PickList::class,
    PickListTeamCrossRef::class
 ], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun pickListDao(): PickListDao
    abstract fun eventDao(): EventDao
    abstract fun pitDataDao(): PitDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "alliance-selection-db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(application: Application) = AppDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideTeamDao(database: AppDatabase) = database.teamDao()

    @Singleton
    @Provides
    fun providePickListDao(database: AppDatabase) = database.pickListDao()

    @Singleton
    @Provides
    fun provideEventDao(database: AppDatabase) = database.eventDao()

    @Singleton
    @Provides
    fun providePitDataDao(database: AppDatabase) = database.pitDataDao()
}