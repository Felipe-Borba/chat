import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.Instant
import java.time.LocalDateTime

class MainViewModel(
) : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    private val eventChannel = Channel<MainEvent>()
    val events = eventChannel.receiveAsFlow()

    //TODO inject dependancy
    private val queue = RabbitMQ()

    //TODO le a fila de saida do rabit, serializa e mostra na telinha
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
