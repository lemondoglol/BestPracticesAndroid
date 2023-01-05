package com.example.bestpracticesapplication.features.timer

import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import kotlinx.coroutines.flow.MutableStateFlow

class TimerService : LifecycleService() {

    internal var timeRemaining = MutableStateFlow(0L)
        private set

    private var countDownTimer: CountDownTimer? = null

    inner class TimerServiceBinder : Binder() {
        val service = this@TimerService
    }

    // called when startService(...) is being called
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val res = super.onStartCommand(intent, flags, startId)

        val timerMode = intent?.getStringExtra(TIMER_MODE)
        val timeCap = intent?.getLongExtra(TIME_CAP, 0L) ?: 0L
        timeRemaining.value = timeCap

        when (timerMode) {
            TIMER_MODE_SHUT_DOWN -> {
                shutDownTimer()
            }
            else -> {
                // if it is a new timer, then cancel the old one and create a new one
                shutDownTimer()
                createAndStartCountDownTimer()
            }
        }

        return res
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return TimerServiceBinder()
    }

    // used to keep tracking current timer
    private fun createAndStartCountDownTimer() {
        countDownTimer = object : CountDownTimer(timeRemaining.value, SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining.value -= SECOND
            }

            override fun onFinish() {}
        }.start()
    }

    private fun shutDownTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    companion object {
        private const val SECOND = 1000L
        internal const val TIMER_MODE = "TIMER_MODE"
        internal const val TIMER_MODE_SHUT_DOWN = "TIMER_MODE_SHUT_DOWN"
        internal const val TIMER_MODE_SET_TIME = "TIMER_MODE_SET_TIME"
        internal const val TIME_CAP = "TIME_CAP"
    }
}
