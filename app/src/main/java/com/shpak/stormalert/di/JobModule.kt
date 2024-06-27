package com.shpak.stormalert.di

import com.shpak.stormalert.data.util.JobScheduler
import com.shpak.stormalert.data.util.NetworkRequestJobScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class JobModule {

    @Binds
    @Singleton
    abstract fun bindJobScheduler(
        jobScheduler: NetworkRequestJobScheduler
    ): JobScheduler
}