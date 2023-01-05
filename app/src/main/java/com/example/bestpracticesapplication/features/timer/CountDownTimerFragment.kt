package com.example.bestpracticesapplication.features.timer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.bestpracticesapplication.features.timer.TimerService.Companion.TIMER_MODE
import com.example.bestpracticesapplication.features.timer.TimerService.Companion.TIMER_MODE_SET_TIME
import com.example.bestpracticesapplication.features.timer.TimerService.Companion.TIMER_MODE_SHUT_DOWN
import com.example.bestpracticesapplication.features.timer.TimerService.Companion.TIME_CAP
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CountDownTimerFragment : Fragment() {

    private val viewModel by viewModels<CountDownTimerFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Button(
                            onClick = {
                                startANewTimer()
                            }
                        ) {
                            Text("Start a Count Down Timer")
                        }

                        Button(
                            onClick = {
                                stopTimer()
                            }
                        ) {
                            Text("Stop Count Down Timer")
                        }

                        Text("Current Time: ${viewModel.timeRemaining}")
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindTimerService()
    }

    private fun bindTimerService() {
        Intent(context, TimerService::class.java).also {
            context?.bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun startANewTimer() {
        Intent(context, TimerService::class.java).apply {
            putExtra(TIMER_MODE, TIMER_MODE_SET_TIME)
            putExtra(TIME_CAP, 5000L)
        }.also {
            // start the service, so we can process data stored in intent extra
            context?.startService(it)
        }
    }

    private fun stopTimer() {
        Intent(context, TimerService::class.java).apply {
            putExtra(TIMER_MODE, TIMER_MODE_SHUT_DOWN)
        }.also {
            // sending shutdown command to Service
            context?.startService(it)
        }
    }

    // serviceConnection that used to fetch data from Service
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            (service as TimerService.TimerServiceBinder).service.timeRemaining
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach {
                    viewModel.updateTimeRemaining(it)
                }
                .launchIn(lifecycleScope)
        }

        override fun onServiceDisconnected(name: ComponentName?) {}
    }
}