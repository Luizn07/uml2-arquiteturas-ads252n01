# Requisitos

## Requisitos Funcionais

| ID | Requisito |
| :--- | :--- |
| RF01 | Permitir o cadastro e login de clientes, restaurantes e entregadores no sistema. |
| RF02 | Permitir que restaurantes cadastrem e atualizem itens do cardápio e sua disponibilidade. |
| RF03 | Permitir que o cliente monte o carrinho e realize um pedido pelo API Gateway. |
| RF04 | Permitir que o restaurante aceite ou recuse o pedido e atualize o status de preparo. |
| RF05 | Permitir atribuir entregador, atualizar o status da entrega e registrar logs em LOGS.md. |

## Requisitos Não Funcionais

| ID | Requisito |
| :--- | :--- |
| RNF01 | Estruturar o sistema no Modelo 1 (Delivery de comida) com arquitetura de microssserviços e API Gateway. |
| RNF02 | Responder às consultas e pedidos de forma rápida e disponível durante horários de pico. |
| RNF03 | Executar e orquestrar os serviços em contêineres isolados via docker-compose.yml, mantendo rastreabilidade em logs. |

## Regras de Negócio

| ID | Regra |
| :--- | :--- |
| RN01 | Bloquear novos pedidos quando o restaurante estiver fechado ou com item esgotado. |
| RN02 | Seguir a ordem de status do pedido: CRIADO, ACEITO, EM_PREPARO, SAIU_PARA_ENTREGA e ENTREGUE. |
| RN03 | Cancelar ou reter o pedido caso o pagamento seja recusado antes do preparo. |
