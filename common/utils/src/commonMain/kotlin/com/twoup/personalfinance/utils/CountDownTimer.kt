package com.twoup.personalfinance.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class CountDownTimer(
    private val durationMillis: Long,
    private val tickIntervalMillis: Long
) {
    abstract fun onFinish()
    abstract fun onTick(millisUntilFinished: Long)

    private var scope: Job? = null
    fun start() {
        var remainingMillis = durationMillis

        scope = GlobalScope.launch {
            while (remainingMillis > 0) {
                delay(tickIntervalMillis)
                remainingMillis -= tickIntervalMillis
                onTick(remainingMillis)
            }

            onFinish()
            scope?.cancel()
        }
    }

    fun cancel() {
        scope?.cancel()
    }

}