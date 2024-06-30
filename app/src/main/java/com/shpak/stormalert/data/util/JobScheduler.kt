package com.shpak.stormalert.data.util

import androidx.work.ListenableWorker

interface JobScheduler {

    fun schedule(
        job: Class<out ListenableWorker>,
        jobId: String,
        initialDelayMillis: Long = 0L
    )

    fun cancel(jobId: String)
}