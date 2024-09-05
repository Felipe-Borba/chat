data class MainState(
    val userName: String? = null,
    val message: String = "",
    val receivedMessages: List<Message> = emptyList(),
    val showLoginscren: Boolean = true,
)
