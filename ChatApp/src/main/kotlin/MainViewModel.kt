import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant

class MainViewModel() : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    private val eventChannel = Channel<MainEvent>()
    val events = eventChannel.receiveAsFlow()

    private val queue = RabbitMQ()

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnFinishLogin -> {
                state.userName?.let {
                    state = state.copy(
                        showLoginscren = false
                    )

                    viewModelScope.launch {
                        queue.receiveMessages(it).collect { message ->
                            state = state.copy(receivedMessages = state.receivedMessages + message)
                        }
                    }
                }
            }

            is MainAction.SendMessage -> {
                state.userName?.let {
                    queue.sendMessage(
                        queueName = "Entry",
                        message = Message(
                            content = state.message,
                            sender = it,
                            date = Instant.now()
                        )
                    )
                    state = state.copy(
                        message = ""
                    )
                }
            }

            is MainAction.ChangeMessage -> {
                state = state.copy(message = action.message)
            }

            is MainAction.ChangeUserName -> {
                state = state.copy(userName = action.userName)
            }
        }
    }
}
