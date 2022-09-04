package com.yyw.jetchatpractice

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yyw.jetchatpractice.conversation.ConversationUiState
import com.yyw.jetchatpractice.data.initialMessages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _drawerShouldBeOpened = MutableStateFlow(false)
    val drawerShouldBeOpened: StateFlow<Boolean> = _drawerShouldBeOpened

    fun openDrawer() {
        _drawerShouldBeOpened.value = true
    }

    fun resetOpenDrawerAction() {
        _drawerShouldBeOpened.value = false
    }

    //    private val _mUiState = MutableStateFlow(exampleUiState)
    private val _mUiState = MutableStateFlow(
        ConversationUiState(
            initialMessages = initialMessages,
            channelName = "#composers",
            channelMembers = 42
        )
    )
    val mUiState: StateFlow<ConversationUiState> = _mUiState

    fun resetUiState(uiState: ConversationUiState) {
        Log.d(TAG, "resetUiState uiState.channelName:${uiState.channelName}")
        _mUiState.update { uiState }
    }
}