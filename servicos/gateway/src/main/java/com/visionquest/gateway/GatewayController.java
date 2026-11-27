package com.visionquest.gateway;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@RestController
@RequestMapping("/api/pedidos")
public class GatewayController {

    private final RestClient restClient;

    public GatewayController(
            @Value("${services.pedidos.url:http://localhost:3002}") String pedidosUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(pedidosUrl)
                .build();
    }

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody Map<String, Object> pedido) {
        try {
            Map resposta = restClient.post()
                    .uri("/api/pedidos")
                    .body(pedido)
                    .retrieve()
                    .body(Map.class);

            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("erro", "Serviço de pedidos indisponível"));
        }
    }
}
