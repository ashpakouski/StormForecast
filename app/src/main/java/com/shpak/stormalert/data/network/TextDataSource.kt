package com.shpak.stormalert.data.network

interface TextDataSource {
    suspend fun loadRawData(): String
}