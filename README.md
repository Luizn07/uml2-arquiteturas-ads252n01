# UML 2 - Arquiteturas

Repositório da disciplina de UML 2 (turma ADS252N01).
Contém os materiais e atividades sobre arquiteturas de software.

## Integrantes

- Adilia Colares
- Bruno Gomes
- Bruno Santos
- Erbeson Brito
- Gabriel Magalhães
- Luiz Vinicius
- Thomas Areas

## Como rodar

**Requisitos:** Docker e Docker Compose.

**Subir todos os serviços (um comando):**

    docker compose up --build

**Serviços e portas:**

| Serviço | Porta |
| ------- | ----- |
| Gateway | 8080 |
| Restaurantes | 3001 |
| Pedidos | 3002 |
| Pagamentos | 3003 |
| Entregas | 3004 |
| Notificações | 3005 |

**Fluxo principal (criar um pedido pelo Gateway):**

    curl -X POST http://localhost:8080/pedidos \
      -H "Content-Type: application/json" \
      -d '{"restauranteId":1,"itens":[{"produto":"Pizza","quantidade":1}]}'

O Gateway recebe o pedido e repassa para Pedidos (`http://pedidos:3002`), que consulta Restaurantes (`http://restaurantes:3001`). Os logs da execução estão em `LOGS.md`.

**Parar:**

    docker compose down