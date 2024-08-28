import java.time.LocalDateTime

data class Message(
    val content: String,
    val sender: String,
    val date: LocalDateTime
)
