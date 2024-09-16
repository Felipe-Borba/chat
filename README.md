# ChatApp

rojeto de Chat com Servidor Multithreading

O projeto visa criar um sistema de chat utilizando um servidor multithreading, que gerencia conexões de clientes por meio de sockets, RMI (Remote Method Invocation) ou sistemas de mensageria. O servidor deve ser capaz de criar e gerenciar salas de chat, permitindo que os clientes escolham uma sala para se conectar. Uma vez dentro de uma sala, os clientes poderão enviar mensagens tanto para usuários específicos quanto para todos os membros da sala.

Ao receber uma mensagem, o servidor é responsável por replicá-la para todos os usuários conectados na mesma sala. A persistência de dados, como mensagens, salas e usuários, pode ser gerida através de um banco de dados, proporcionando armazenamento durável e robusto. Alternativamente, é possível utilizar objetos em memória para controle mais ágil e temporário dos dados, desde que esses objetos sejam adequadamente compartilhados e sincronizados entre as threads.

Essa versão fornece uma explicação clara e concisa do objetivo do projeto, detalhando a funcionalidade do servidor e a abordagem para persistência de dados.

## Diagramas 📄

### Diagrama de Classe
![Diagrama de Classe](/Chat-Digrama.drawio%20(1).png)

### Diagrama de Componentes
![Diagrama de Componentes](/Componentes_chat.png)

### Diagrama de Atividades
![Diagrama de Atividades](/Diagrama%20de%20atividade_Chat.png)

## Tecnologias 🌐

- Java
- Kotlin
- RabbitMQ
- Kotlin Multiplatform

## Autores 👤

- [Andre Luis](https://www.github.com/DreLuis)
- [Felipe Borba](https://www.github.com/Felipe-Borba)
- [Leonardo Vieira](https://www.github.com/vieira-leonardo)
- [Jonatas Medeiros](https://www.github.com/jonatasmedeiros155)

## Link
https://github.com/Felipe-Borba/chat
