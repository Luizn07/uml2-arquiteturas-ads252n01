package com.visionquest.pedidos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final RestClient restaurantesClient;
    private final Map<Long, PedidoResponse> pedidos = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public PedidosController(
            @Value("${services.restaurantes.url}") String restaurantesUrl) {

        this.restaurantesClient = RestClient.builder()
                .baseUrl(restaurantesUrl)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(
            @RequestBody CriarPedidoRequest request) {

        if (request.restauranteId() == null
                || request.itens() == null
                || request.itens().isEmpty()) {

            return ResponseEntity.badRequest().body(
                    Map.of("mensagem", "Informe o restaurante e os itens.")
            );
        }

        try {
            restaurantesClient.post()
                    .uri("/api/restaurantes/validar")
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();

        } catch (RestClientException exception) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                    Map.of(
                            "mensagem",
                            "Não foi possível validar o pedido no serviço de restaurantes."
                    )
            );
        }

        long id = proximoId.getAndIncrement();

        PedidoResponse pedido = new PedidoResponse(
                id,
                request.restauranteId(),
                request.itens(),
                "AGUARDANDO_PAGAMENTO"
        );

        pedidos.put(id, pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @GetMapping
    public List<PedidoResponse> listarPedidos() {
        return List.copyOf(pedidos.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPedido(@PathVariable Long id) {
        PedidoResponse pedido = pedidos.get(id);

        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pedido);
    }

    public record CriarPedidoRequest(
            Long restauranteId,
            List<String> itens
    ) {
    }

    public record PedidoResponse(
            Long id,
            Long restauranteId,
            List<String> itens,
            String status
    ) {
    }
}