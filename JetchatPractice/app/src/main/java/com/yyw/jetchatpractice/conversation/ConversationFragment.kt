package com.yyw.jetchatpractice.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yyw.jetchatpractice.MainViewModel
import com.yyw.jetchatpractice.data.exampleUiState
import com.yyw.jetchatpractice.ui.theme.JetchatPracticeTheme

class ConversationFragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            JetchatPracticeTheme {
                val uiState by activityViewModel.mUiState.collectAsState()
                ConversationContent(uiState = uiState, onNavIconPressed = { activityViewModel.openDrawer() })
            }
        }
    }
}