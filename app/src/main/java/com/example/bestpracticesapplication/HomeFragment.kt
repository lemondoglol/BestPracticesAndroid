package com.example.bestpracticesapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        item {
                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToSignInFragment()
                                    )
                                }
                            ) {
                                Text("To Sign-in fragment")
                            }

                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToOneTapSignInFragment()
                                    )
                                }
                            ) {
                                Text("To OneTap Sign-in fragment")
                            }

                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToWorkManagerFragment()
                                    )
                                }
                            ) {
                                Text("To WorkManager fragment")
                            }

                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToFileTransferFragment()
                                    )
                                }
                            ) {
                                Text("Download and Save File")
                            }

                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToNotificationFragment()
                                    )
                                }
                            ) {
                                Text("To Notification")
                            }

                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToCustomFlashLightFragment()
                                    )
                                }
                            ) {
                                Text("To Flash Light")
                            }

                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToAvoidGlobalScopeFragment()
                                    )
                                }
                            ) {
                                Text("To Avoid Using GlobalScope Example")
                            }

                            Button(
                                onClick = {
                                    findNavController().navigate(
                                        HomeFragmentDirections.actionHomeFragmentToCountDownTimerFragment()
                                    )
                                }
                            ) {
                                Text("To CountDownTimer Example")
                            }
                        }
                    }
                }
            }
        }
    }
}