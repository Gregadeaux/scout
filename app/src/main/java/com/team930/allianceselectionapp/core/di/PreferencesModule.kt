package com.team930.allianceselectionapp.core.di

import android.content.Context
import com.team930.allianceselectionapp.data.local.EventDao
import com.team930.allianceselectionapp.data.preferences.GlobalStatePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideGlobalStatePreferences(
        @ApplicationContext context: Context,
        eventDao: EventDao
    ): GlobalStatePreferences {
        return GlobalStatePreferences(context, eventDao)
    }
}