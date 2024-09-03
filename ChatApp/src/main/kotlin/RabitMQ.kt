import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.Instant

data class Message(
    val content: String,
    val sender: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val date: Instant
)

class RabbitMQ(
    private val host: String = "localhost",
) {
    private val connectionFactory: ConnectionFactory = ConnectionFactory().apply {
        this.host = host
    }

    private val connection: Connection by lazy {
        connectionFactory.newConnection()
    }

    private val objectMapper = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
        disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    fun sendMessage(queueName: String, message: Message) {
        connection.createChannel().use { channel ->
            channel.queueDeclare(queueName, true, false, false, null)
            val messageBody = objectMapper.writeValueAsBytes(message)
            channel.basicPublish("", queueName, null, messageBody)
        }
    }

    //TODO não retirar a mensagem da fila depois de receber ela para todo mundo poder ver
    suspend fun receiveMessages(queueName: String): Flow<Message> = callbackFlow {
        val channel = connection.createChannel()
        channel.queueDeclare(queueName, true, false, false, null)

        val deliverCallback = DeliverCallback { _, delivery ->
            val message: Message = objectMapper.readValue(delivery.body)
            trySend(message)
        }

        channel.basicConsume(queueName, true, deliverCallback) { _ -> }

        awaitClose { channel.close() }
    }

    fun close() {
        connection.close()
    }
}
