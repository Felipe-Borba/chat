import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant

class MainViewModel(
) : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    private val eventChannel = Channel<MainEvent>()
    val events = eventChannel.receiveAsFlow()

    private val queue = RabbitMQ()

    init {
        viewModelScope.launch {
            queue.receiveMessages("Entry").collect { message ->
                print("Leitura file $message")
                state = state.copy(receivedMessages = state.receivedMessages + message)
            }
        }
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnFinishLogin -> {
                state = state.copy(
                    userName = action.userName,
                    showLoginscren = false
                )
            }

            is MainAction.SendMessage -> {
                queue.sendMessage(
                    queueName = "Entry",
                    message = Message(
                        content = action.message,
                        sender = state.userName,
                        date = Instant.now()
                    )
                )
            }
        }
    }
}
