package com.shpak.stormalert.data.util

import androidx.work.ListenableWorker

interface JobScheduler {

    fun schedule(
        job: Class<out ListenableWorker>,
        jobId: String,
        repeatIntervalMillis: Long,
        initialDelayMillis: Long = 0L
    )

    fun cancel(jobId: String)

    // fun schedule(
    //     job: Class<out ListenableWorker>,
    //     jobId: String,
    //     executionDelayMillis: Long = 0L
    // )
}