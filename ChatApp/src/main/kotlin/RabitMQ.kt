import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rabbitmq.client.*
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
    private val EXCHANGE_NAME = "MessageDistribution";

    //TODO change queue to senai server
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
//            channel.queueDeclare(queueName, true, false, false, null)
            val messageBody = objectMapper.writeValueAsBytes(message)
            channel.basicPublish("", queueName, null, messageBody)
        }
    }

    suspend fun receiveMessages(queueName: String): Flow<Message> = callbackFlow {
        val channel = connection.createChannel()
        channel.queueDelete(queueName)
        channel.queueDeclare(queueName, true, false, false, mapOf("x-expires" to 3600000))
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        val consumer: Consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray?
            ) {
                body?.let {
                    val message: Message = objectMapper.readValue(it)
                    trySend(message)
                }

                // Acknowledge the message to remove it from the queue
                 channel.basicAck(envelope?.deliveryTag ?: 0, false)
            }
        }

        channel.basicConsume(queueName, false, consumer)

        awaitClose { channel.close() }
    }

    fun close() {
        connection.close()
    }
}
