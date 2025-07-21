🗳️ Enquete de Votação de Jogos
Projeto desenvolvido com Spring Boot, oferecendo uma API REST para votação em jogos. Usuários podem se cadastrar, fazer login, votar em seus jogos favoritos e consultar o ranking dos mais votados — com segurança e controle de acesso via JWT.

✅ Funcionalidades
Cadastro e login de usuários (JWT gerado no login)

Listagem de todos os jogos

Busca de jogos por nome ou categoria

Votação em jogos

Visualização do ranking com os jogos mais votados

Apenas usuários autenticados podem votar ou acessar o ranking

HTTPS obrigatório (redirecionamento automático)

Integração com banco de dados MySQL

Testes automatizados com JUnit e Mockito

🔐 Segurança
Autenticação via JWT

Token necessário para acessar endpoints protegidos (via cabeçalho Authorization)

Proteção dos endpoints sensíveis com Spring Security

Obrigatoriedade de uso de HTTPS em todas as requisições

🧪 Testes
Foram implementados testes com:

JUnit 5

Mockito

Cobertura de testes para:

Usuário

Jogo

Votação

Ranking

🚀 Tecnologias Utilizadas
Java 17

Spring Boot 3.4.7

Spring Security

JWT (JSON Web Token)

JPA / Hibernate

MySQL

JUnit 5

Mockito

📚 Endpoints da API
🔓 Públicos
POST /cadastro – Cadastro de novo usuário

POST /login – Autenticação e geração de token JWT

GET /jogos – Listagem geral de jogos

🔐 Protegidos (JWT obrigatório)
GET /jogos/{nome} – Busca por nome

GET /jogos/categoria?categoria=nome – Busca por categoria

POST /jogos/votacao – Registra um voto

GET /jogos/ranking – Ranking de jogos mais votados (somente para ROLE_ADMIN)

👨‍💻 Desenvolvedor
Desenvolvido por Italo Kelmy – 2025 🚀
