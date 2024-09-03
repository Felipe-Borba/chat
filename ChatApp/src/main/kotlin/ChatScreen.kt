import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ChatScreen(
    messages: List<Message>,
    onSendMessage: (message: String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f).padding(16.dp)
        ) {
            items(messages) { message ->
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    BasicText(text = "Sender: ${message.sender}")
                    BasicText(text = "Content: ${message.content}")
                    BasicText(
                        text = "Date: ${
                            LocalDateTime.ofInstant(message.date, ZoneId.of("UTC")).format(formatter)
                        }"
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp),
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("message") },
                modifier = Modifier.fillMaxWidth(0.85f)
            )
            Button(onClick = {
                onSendMessage(text)
            }, modifier = Modifier.fillMaxSize()) {
                Text("Send")
            }
        }
    }
}

@Composable
@Preview
private fun ChatScreenPreview() {
    val messages = listOf(
        Message("Hello!", "Alice", Instant.now()),
        Message("Hi, how are you?", "Bob", Instant.now()),
        Message("I'm good, thanks!", "Alice", Instant.now()),
        Message("What about you?", "Alice", Instant.now()),
        Message("I'm fine too.", "Bob", Instant.now())
    )

    MaterialTheme {
        ChatScreen(messages = messages) { }
    }
}
