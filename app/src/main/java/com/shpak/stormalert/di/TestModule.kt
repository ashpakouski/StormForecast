package com.shpak.stormalert.di

import android.content.Context
import com.shpak.stormalert.data.util.DailySummaryWorkScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestModule {

    @Provides
    @Singleton
    fun provideBackgroundWorkScheduler(
        @ApplicationContext context: Context
    ): DailySummaryWorkScheduler {
        return DailySummaryWorkScheduler(context)
    }
}