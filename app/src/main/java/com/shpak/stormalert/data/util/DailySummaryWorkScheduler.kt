package com.shpak.stormalert.data.util

import java.util.Calendar

object DailySummaryWorkScheduler {

    private val job = DailySummaryJob::class.java

    private const val JOB_ID = "job_id_daily_summary"
    private const val REPEAT_INTERVAL_MILLIS = 6 * 60 * 60 * 1000L // 6 hours

    fun scheduleJob(
        jobScheduler: JobScheduler
    ) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 20)
            set(Calendar.SECOND, 0)
        }

        val now = Calendar.getInstance()

        if (now.after(calendar)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val initialDelayMillis = calendar.timeInMillis - now.timeInMillis

        jobScheduler.schedule(job, JOB_ID, REPEAT_INTERVAL_MILLIS, initialDelayMillis)
    }
}