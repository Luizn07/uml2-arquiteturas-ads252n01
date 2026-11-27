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

