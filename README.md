🗳️ Enquete de Votação de Jogos
Projeto desenvolvido com Spring Boot que disponibiliza uma API REST para votação em jogos. Os usuários podem se cadastrar, fazer login, votar em seus jogos favoritos, visualizar listas de jogos, buscar por nome ou categoria, e consultar um ranking com os jogos mais votados.

🔧 Funcionalidades
🧑‍💻 Autenticação de Usuários
Cadastro de novos usuários

Login com geração de token JWT

🎮 Gerenciamento de Jogos
Listagem de todos os jogos

Busca de jogos por nome ou categoria

Votação em jogos

Visualização do ranking dos jogos mais votados

🔐 Segurança
Autenticação via JWT

Proteção de endpoints sensíveis com Spring Security

Configuração HTTPS

🛠️ Tecnologias Utilizadas
Backend
Java 24

Spring Boot 3.4.7

Spring Security

JWT (JSON Web Tokens)

JPA / Hibernate

MySQL

Testes
JUnit 5

Mockito

Testes de Integração

📚 Documentação da API
🔓 Endpoints Públicos
POST /cadastro - Cadastro de novo usuário

POST /login - Autenticação e geração de token JWT

GET /jogos - Listagem de todos os jogos disponíveis

🔐 Endpoints Protegidos (Requer autenticação via JWT)
GET /jogos/{nome} - Busca um jogo pelo nome

GET /jogos/categoria?categoria={categoria} - Busca jogos por categoria

POST /jogos/votacao - Registra um voto para um jogo

GET /jogos/ranking - Retorna o ranking dos jogos mais votados (requer role ADMIN)

👨‍💻 Desenvolvedor
Desenvolvido por Italo Kelmy – 2025
