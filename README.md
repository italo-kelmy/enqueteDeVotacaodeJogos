Enquete de Votação de Jogos
Este é um projeto Spring Boot para uma API de enquete de votação de jogos, onde os usuários podem cadastrar-se, fazer login e votar em seus jogos favoritos. A API também fornece endpoints para listar jogos, buscar por nome ou categoria, e visualizar um ranking dos jogos mais votados.

📋 Funcionalidades
Autenticação de Usuários:

Cadastro de novos usuários

Login com geração de token JWT

Gerenciamento de Jogos:

Listagem de todos os jogos

Busca de jogos por nome ou categoria

Votação em jogos

Ranking dos jogos mais votados

Segurança:

Autenticação via JWT

Proteção de endpoints sensíveis

Configuração HTTPS

🛠️ Tecnologias Utilizadas
Backend:

Java 24

Spring Boot 3.4.7

Spring Security

JWT (JSON Web Tokens)

JPA/Hibernate

MySQL

Testes:

JUnit 5

Mockito

Testes de integração

Documentação da API
Endpoints Públicos
POST /cadastro - Cadastra um novo usuário

POST /login - Realiza login e retorna um token JWT

GET /jogos - Lista todos os jogos disponíveis

Endpoints Protegidos
GET /jogos/{nome} - Busca um jogo pelo nome

GET /jogos/categoria?categoria={categoria} - Busca jogos por categoria

POST /jogos/votacao - Registra um voto para um jogo

GET /jogos/ranking - Retorna o ranking dos jogos mais votados (requer role ADMIN)



Desenvolvido por [Italo Kelmy] - 2025
