import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLogin: (name: String) -> Unit
) {
    //TODO mover isso para o viewModel
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
        Button(onClick = {
            onLogin(text)
        }) {
            Text("Login")
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen { }
    }
}
