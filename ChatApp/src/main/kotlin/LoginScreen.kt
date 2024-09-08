import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    state: MainState,
    onAction: (MainAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.userName ?: "",
            onValueChange = {
                if (it.isNotEmpty() && it.last() == '\n') {
                    onAction(MainAction.OnFinishLogin)
                } else {
                    onAction(MainAction.ChangeUserName(it))
                }
            },
            label = { Text("Username") },
        )
        Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
        Button(onClick = {
            onAction(MainAction.OnFinishLogin)
        }) {
            Text("Login")
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(state = MainState(), onAction = {})
    }
}
