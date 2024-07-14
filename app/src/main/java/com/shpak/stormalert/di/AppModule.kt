package com.shpak.stormalert.di

import android.content.Context
import com.shpak.stormalert.data.network.TextDataSource
import com.shpak.stormalert.data.network.TextGeomagneticDataRepository
import com.shpak.stormalert.data.repository.local.AppNotificationSettingsRepository
import com.shpak.stormalert.data.repository.remote.DefaultGeomagneticForecastRepository
import com.shpak.stormalert.data.repository.local.DefaultUiInteractionRepository
import com.shpak.stormalert.domain.repository.GeomagneticForecastRepository
import com.shpak.stormalert.domain.repository.NotificationSettingsRepository
import com.shpak.stormalert.domain.repository.UiInteractionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindUiInteractionRepository(
        uiInteractionRepository: DefaultUiInteractionRepository
    ): UiInteractionRepository

    @Binds
    @Singleton
    abstract fun bindTextGeomagneticDataSource(
        textGeomagneticDataRepository: TextGeomagneticDataRepository
    ): TextDataSource

    @Binds
    @Singleton
    abstract fun bindGeomagneticRepository(
        geomagneticRepository: DefaultGeomagneticForecastRepository
    ): GeomagneticForecastRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        notificationSettingsRepository: AppNotificationSettingsRepository
    ): NotificationSettingsRepository

    @Binds
    @Singleton
    abstract fun bindContext(
        @ApplicationContext context: Context
    ): Context
}