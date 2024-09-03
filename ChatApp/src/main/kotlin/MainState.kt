data class MainState(
    val userName: String = "",
    val message: String = "",
    val receivedMessages: List<Message> = emptyList(),
    val showLoginscren: Boolean = true,
)
