package com.visionquest.restaurantes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurantes")
public class RestaurantesController {

    private final List<String> itensDisponiveis =
            List.of("Hamburguer", "Pizza", "Refrigerante");

    @GetMapping
    public List<Map<String, Object>> listarRestaurantes() {
        return List.of(
                Map.of(
                        "id", 1,
                        "nome", "Restaurante VisionQuest",
                        "itens", itensDisponiveis
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarRestaurante(@PathVariable Long id) {
        if (id != 1) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                Map.of(
                        "id", 1,
                        "nome", "Restaurante VisionQuest",
                        "itens", itensDisponiveis
                )
        );
    }

    @PostMapping("/validar")
    public ResponseEntity<Map<String, Object>> validarPedido(
            @RequestBody ValidacaoPedidoRequest request) {

        if (request.restauranteId() == null || request.restauranteId() != 1) {
            return ResponseEntity.badRequest().body(
                    Map.of("mensagem", "Restaurante não encontrado.")
            );
        }

        if (request.itens() == null || request.itens().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of("mensagem", "O pedido precisa conter itens.")
            );
        }

        List<String> itensIndisponiveis = request.itens().stream()
                .filter(item -> !itensDisponiveis.contains(item))
                .toList();

        if (!itensIndisponiveis.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "mensagem", "Existem itens indisponíveis.",
                            "itensIndisponiveis", itensIndisponiveis
                    )
            );
        }

        return ResponseEntity.ok(
                Map.of(
                        "valido", true,
                        "mensagem", "Restaurante e itens validados."
                )
        );
    }

    public record ValidacaoPedidoRequest(
            Long restauranteId,
            List<String> itens
    ) {
    }
}