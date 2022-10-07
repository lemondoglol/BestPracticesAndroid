package com.example.bestpracticesapplication.features.sign_in

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneTapSignInFragment : Fragment() {

    private lateinit var signInClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest


    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                val credential = signInClient.getSignInCredentialFromIntent(it.data)
                Toast.makeText(
                    activity,
                    "success with token: ${credential.googleIdToken} name: ${credential.displayName}",
                    Toast.LENGTH_LONG
                ).show()
                // once you get the token, send it to the BE, and BE needs to verify the token
            }
            else -> Toast.makeText(activity, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.let {
            signInClient = Identity.getSignInClient(it)
            signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(
                    BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()
                )
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId("lemonbp-1665154427043")
                        .setFilterByAuthorizedAccounts(true)
                        .build()
                )
                // if set to true, then it will auto sign in when only one credential is retrieved
                .setAutoSelectEnabled(false)
                .build()

            signInClient.beginSignIn(signInRequest)
                .addOnSuccessListener { signInRequest ->
                    val intentSenderRequest = IntentSenderRequest.Builder(
                        signInRequest.pendingIntent.intentSender
                    ).build()
                    googleSignInLauncher.launch(intentSenderRequest)
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Sign in failed", Toast.LENGTH_SHORT).show()
                }
        }


        return ComposeView(requireContext()).apply {
            setContent {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Button(
                            onClick = {
                                // todo
                            }
                        ) {
                            Text("Sign in")
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        signInClient.signOut()
    }
}