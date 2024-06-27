package com.shpak.stormalert.data.util

import android.content.Context
import androidx.work.WorkManager
import javax.inject.Inject

class RegularUpdateJob @Inject constructor(
    context: Context
) {
    private val workManager = WorkManager.getInstance(context)


}