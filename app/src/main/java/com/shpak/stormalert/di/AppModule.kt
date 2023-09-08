package com.shpak.stormalert.di

import com.shpak.stormalert.data.network.TextDataSource
import com.shpak.stormalert.data.network.TextGeomagneticDataRepository
import com.shpak.stormalert.data.repository.DefaultGeomagneticRepository
import com.shpak.stormalert.domain.repository.GeomagneticRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
}