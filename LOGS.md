# Logs do fluxo principal

## Fluxo

O cliente envia um pedido ao API Gateway, que encaminha a solicitação ao microsserviço de Pedidos. O serviço de Pedidos consulta o microsserviço de Restaurantes para validar o estabelecimento e os itens solicitados. Após a validação, o pedido é registrado e a resposta é devolvida ao cliente.

## Registros do fluxo

```text
API Gateway - Recebida solicitação de criação de pedido.
Gateway - Encaminhando requisição para Pedidos.
Pedidos - Iniciando validação do pedido.
Pedidos - Solicitando validação ao serviço de Restaurantes.
Restaurantes - Validando restaurante e itens solicitados.
Restaurantes - Validação concluída com sucesso. HTTP 200.
Pedidos - Pedido criado com sucesso. ID: 1.
Pedidos - Status: AGUARDANDO_PAGAMENTO.
Gateway - Resposta retornada ao cliente. HTTP 201.
```
## Microsserviços e suas rotas

### Gateway
- POST `/api/pedidos` — Encaminha a criação de pedidos.

### Restaurantes
- GET `/api/restaurantes` — Lista restaurantes.
- GET `/api/restaurantes/{id}` — Consulta restaurante.
- POST `/api/restaurantes/validar` — Valida pedidos e itens.

### Pedidos
- POST `/api/pedidos` — Cria pedido.
- GET `/api/pedidos` — Lista pedidos.
- GET `/api/pedidos/{id}` — Consulta pedido.

### Pagamentos
- POST `/api/pagamentos` — Processa pagamento.
- GET `/api/pagamentos/{id}` — Consulta pagamento.

### Entregas
- POST `/api/entregas` — Cria entrega.
- GET `/api/entregas` — Lista entregas.
- GET `/api/entregas/{id}` — Consulta entrega.
- PATCH `/api/entregas/{id}/status` — Atualiza status.
- PATCH `/api/entregas/{id}/entregador` — Atribui entregador.

### Notificações
- POST `/api/notificacoes` — Cria notificação.
- GET `/api/notificacoes` — Lista notificações.
- GET `/api/notificacoes/{id}` — Consulta notificação.

  ## Arquitetura e Docker Compose

O projeto utiliza uma arquitetura de microsserviços com API Gateway, permitindo a separação das responsabilidades entre os serviços.

### Serviços e portas

| Microsserviço | Porta |
|---|---:|
| Gateway | 8080 |
| Restaurantes | 3001 |
| Pedidos | 3002 |
| Pagamentos | 3003 |
| Entregas | 3004 |
| Notificações | 3005 |

### Comunicação e infraestrutura

- O Docker Compose é utilizado para organizar os containers.
- Os serviços utilizam a rede Docker `delivery-net`.
- O Gateway encaminha requisições ao serviço de Pedidos.
- Pedidos se comunica com Restaurantes para validar os pedidos.
- Cada microsserviço possui sua própria responsabilidade dentro da arquitetura.

### Comandos Docker

Para construir e iniciar os serviços:

```bash
docker compose up --build
```

Para consultar os logs dos containers:

```bash
docker compose logs
```

Para encerrar os serviços:

```bash
docker compose down
```
