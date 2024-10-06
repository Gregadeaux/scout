package com.team930.allianceselectionapp.core.di

import com.team930.allianceselectionapp.core.util.Local
import com.team930.allianceselectionapp.core.util.Remote
import com.team930.allianceselectionapp.data.repository.LocalTeamRepository
import com.team930.allianceselectionapp.data.repository.RemoteTeamRepository
import com.team930.allianceselectionapp.domain.repository.TeamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    @Remote
    abstract fun bindRemoteTeamRepository(
        remoteTeamRepository: RemoteTeamRepository
    ): TeamRepository

    @Binds
    @Singleton
    @Local
    abstract fun bindLocalTeamRepository(
        localTeamRepository: LocalTeamRepository
    ): TeamRepository
}
