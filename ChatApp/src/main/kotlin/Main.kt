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
import java.time.LocalDateTime

@Composable
fun App() {
    val screenStack by remember { mutableStateOf(mutableListOf<String>("LoginScreen")) }
    var currentScreen by remember { mutableStateOf("LoginScreen") }
    var user by remember { mutableStateOf("") }


    MaterialTheme {
        Scaffold(topBar = {
            Column(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(if (user.isNotEmpty()) "Welcome $user" else "Welcome to the chat app")
            }
        }) {
            when (currentScreen) {
                "ChatScreen" -> {
                    ChatScreen(messages = emptyList()) { message ->
                        println(message)
                    }
                }

                "LoginScreen" -> {
                    LoginScreen { username ->
                        user = username
                        screenStack.add("ChatScreen")
                        currentScreen = "ChatScreen"
                    }
                }

                else -> {}
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
