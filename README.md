# ChatApp

Projeto de um Chat, utilizando-se um servidor multithreading, o qual recebe conexões dos clientes do Chat, através de socket, RMI ou mesmo Mensageria. O servidor precisa criar uma sala e os clientes devem escolher a sala de comunicação e então enviar mensagens para um usuário da sala, ou para todos. O servidor ao receber as mensagens, deve replicar para todos os usuários conectados na sala de discussão. Poderão usar persistência em banco de dados para as mensagens, salas e usuários, ou controlar via objetos em memória no servidor. Esses objetos necessariamente deverão ser compartilhados de alguma forma.

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
