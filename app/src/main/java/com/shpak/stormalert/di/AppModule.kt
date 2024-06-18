package com.shpak.stormalert.di

import android.content.Context
import com.shpak.stormalert.data.network.TextDataSource
import com.shpak.stormalert.data.network.TextGeomagneticDataRepository
import com.shpak.stormalert.data.repository.AppNotificationSettingsRepository
import com.shpak.stormalert.data.repository.DefaultGeomagneticRepository
import com.shpak.stormalert.data.repository.DefaultUiInteractionRepository
import com.shpak.stormalert.domain.repository.GeomagneticRepository
import com.shpak.stormalert.domain.repository.NotificationSettingsRepository
import com.shpak.stormalert.domain.repository.UiInteractionRepository
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
    fun provideUiInteractionRepository(@ApplicationContext context: Context): UiInteractionRepository {
        return DefaultUiInteractionRepository(context)
    }

    @Provides
    @Singleton
    fun provideTextGeomagneticDataSource(): TextDataSource {
        return TextGeomagneticDataRepository()
    }

    @Provides
    @Singleton
    fun provideGeomagneticRepository(textDataSource: TextDataSource): GeomagneticRepository {
        return DefaultGeomagneticRepository(textDataSource)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): NotificationSettingsRepository {
        return AppNotificationSettingsRepository(context)
    }
}