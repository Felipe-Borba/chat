@file:OptIn(ExperimentalFoundationApi::class)

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

data class MainState(
//    val userName: TextFieldState = TextFieldState(),
//    val message: TextFieldState = TextFieldState(),
    val userName: String = "",
    val message: String = "",
    val receivedMessages: List<Message> = emptyList(),
    val showLoginscren: Boolean = true,
)
