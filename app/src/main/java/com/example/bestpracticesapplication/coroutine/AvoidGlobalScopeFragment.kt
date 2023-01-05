package com.example.bestpracticesapplication.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvoidGlobalScopeFragment : Fragment() {

    private val viewModel by viewModels<AvoidGlobalScopeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                viewModel.exampleUsingLaunch()
                            }
                        ) {
                            Text("Directly use .launch()")
                        }

                        Button(
                            onClick = {
                                viewModel.exampleUsingGlobalScope()
                            }
                        ) {
                            Text("Using Global Scope")
                        }

                        Button(
                            onClick = {
                                viewModel.exampleUsingGlobalScope1()
                            }
                        ) {
                            Text("Using Global Scope with JoinAll")
                        }

                        Button(
                            onClick = {
                                viewModel.exampleUsingCustomScope()
                            }
                        ) {
                            Text("Correct Usage")
                        }

                        Text("Total Execution Time: ${viewModel.totalExecuteTime}")
                        Text("API Response: \n${viewModel.fakeAPIResponse}")
                    }
                }
            }
        }
    }
}