package com.yyw.jetchatpractice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import com.yyw.jetchatpractice.conversation.BackPressHandler
import com.yyw.jetchatpractice.conversation.ConversationUiState
import com.yyw.jetchatpractice.conversation.JetchatDrawer
import com.yyw.jetchatpractice.conversation.LocalBackPressedDispatcher
import com.yyw.jetchatpractice.data.initialMessages
import com.yyw.jetchatpractice.databinding.ContentMainBinding
import com.yyw.jetchatpractice.ui.theme.JetchatPracticeTheme
import kotlinx.coroutines.launch

const val TAG = "wyy"

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(ComposeView(this).apply {
            consumeWindowInsets = false
            setContent {
                CompositionLocalProvider(LocalBackPressedDispatcher provides this@MainActivity.onBackPressedDispatcher) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val drawerOpen by viewModel.drawerShouldBeOpened.collectAsState()
                    val uiState by viewModel.mUiState.collectAsState()
                    if (drawerOpen) {
                        // Open drawer and reset state in VM.
                        LaunchedEffect(Unit) {
                            // wrap in try-finally to handle interruption whiles opening drawer
                            try {
                                drawerState.open()
                            } finally {
                                viewModel.resetOpenDrawerAction()
                            }
                        }
                    }
                    val scope = rememberCoroutineScope()
                    if (drawerState.isOpen) {
                        BackPressHandler {
                            scope.launch { drawerState.close() }
                        }
                    }
                    JetchatDrawer(
                        drawerState = drawerState,
                        onProfileClicked = {
                            Log.d(TAG, "onProfileClicked")
                            findNavController(R.id.nav_host_fragment).navigate(R.id.profileFragment)
                            scope.launch {
                                drawerState.close()
                            }
                        }, onChatClicked = {
                            Log.d(TAG, "onChatClicked it:$it")
                            viewModel.resetUiState(
                                ConversationUiState(
                                    initialMessages = initialMessages,
                                    channelName = it,
                                    channelMembers = 42
                                )
                            )
                            scope.launch {
                                drawerState.close()
                            }
                        }) {
                        AndroidViewBinding(ContentMainBinding::inflate)
                    }
                }
            }
        })
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetchatPracticeTheme {
        Greeting("Android")
    }
}