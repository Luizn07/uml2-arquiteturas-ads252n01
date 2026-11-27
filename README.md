# Delivery de Comida: Arquitetura de Microsserviços

**Disciplina:** Análise e Projeto de Sistemas (ADS252N01)
**Equipe:** VisionQuest
**Modelo escolhido:** 1 - Delivery de Comida
**Arquitetura principal:** Microsserviços com API Gateway

## Integrantes

| Nome | Usuário do GitHub |
| ---- | ----------------- |
| Adilia Colares |h20lia |
| Bruno Gomes |ibrunosz |
| Bruno Santos | brusantosdev |
| Erbeson Brito | Erbeson-Brito |
| Gabriel Magalhães |gabrielmagalhaesg700-hub |
| Luiz Vinicius | Luizn07 |
| Thomas Areas | areasthom |

## Sobre o projeto

Sistema de delivery de comida construído com microsserviços em Java 17 e Maven. Um API Gateway recebe as requisições dos clientes e as encaminha para os serviços responsáveis, cada um com uma função própria e executando em seu próprio container Docker.

## Arquitetura

| Serviço | Porta | Responsabilidade |
| ------- | ----- | ---------------- |
| Gateway | 8080 | Ponto de entrada único; roteia as requisições |
| Restaurantes | 3001 | Cadastro e consulta de restaurantes |
| Pedidos | 3002 | Criação e acompanhamento de pedidos |
| Pagamentos | 3003 | Processamento de pagamentos |
| Entregas | 3004 | Gerenciamento das entregas |
| Notificações | 3005 | Envio de avisos ao cliente |

O fluxo principal é: o cliente cria um pedido pelo Gateway, que repassa para Pedidos, que consulta Restaurantes.

Os diagramas estão em `docs/arquitetura.png` e `docs/sequencia.png`, e os requisitos em `docs/requisitos.md`.

## Integrantes

| Nome | Usuário do GitHub |
| ---- | ----------------- |
| Adília Colares | |
| Bruno Gomes | |
| Bruno Santos | brusantosdev |
| Erbeson Brito | Erbeson-Brito |
| Gabriel Magalhães | |
| Luiz Vinicius | Luizn07 |

## Como rodar

**Requisitos:** Docker e Docker Compose.

**Subir todos os serviços:**

    docker compose up --build

**Fluxo principal (criar um pedido pelo Gateway):**

    curl -X POST http://localhost:8080/pedidos \
      -H "Content-Type: application/json" \
      -d '{"restauranteId":1,"itens":[{"produto":"Pizza","quantidade":1}]}'

O Gateway repassa para Pedidos (`http://pedidos:3002`), que consulta Restaurantes (`http://restaurantes:3001`). Os logs da execução estão em `LOGS.md`.

**Parar:**

    docker compose down

## Estrutura do repositório

- `servicos/`: um microsserviço por pasta
- `docs/`: requisitos e diagramas
- `docker-compose.yml`: orquestração dos containers
- `LOGS.md`: logs do fluxo principal