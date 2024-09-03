import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App() {
//    val screenStack by remember { mutableStateOf(mutableListOf<String>("LoginScreen")) }
//    var currentScreen by remember { mutableStateOf("LoginScreen") }
//    var user by remember { mutableStateOf("") }
//    val messages by remember { mutableStateOf(mutableListOf<Message>()) }
    val viewModel = MainViewModel()

    MaterialTheme {
        Scaffold(topBar = {
            Column(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(if (viewModel.state.userName.isNotEmpty()) "Welcome ${viewModel.state.userName}" else "Welcome to the chat app")
            }
        }) {
            if (viewModel.state.showLoginscren) {
                LoginScreen(
                    onLogin = { userName ->
                        viewModel.onAction(MainAction.OnFinishLogin(userName))
                    }
                )
            } else {
                ChatScreen(
                    messages = viewModel.state.receivedMessages,
                    onSendMessage = { message ->
                        viewModel.onAction(MainAction.SendMessage(message))
                    }
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
