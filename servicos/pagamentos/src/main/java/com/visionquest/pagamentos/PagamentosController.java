package com.visionquest.pagamentos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentosController {

    private final Map<Long, PagamentoResponse> pagamentos =
            new ConcurrentHashMap<>();

    private final AtomicLong proximoId = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<?> processarPagamento(
            @RequestBody PagamentoRequest request) {

        if (request.pedidoId() == null
                || request.pedidoId() <= 0
                || request.valor() == null
                || request.valor().compareTo(BigDecimal.ZERO) <= 0) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "mensagem",
                            "Informe um pedido válido e um valor maior que zero."
                    )
            );
        }

        long id = proximoId.getAndIncrement();

        PagamentoResponse pagamento = new PagamentoResponse(
                id,
                request.pedidoId(),
                request.valor(),
                "APROVADO"
        );

        pagamentos.put(id, pagamento);

        return ResponseEntity.status(201).body(pagamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPagamento(@PathVariable Long id) {
        PagamentoResponse pagamento = pagamentos.get(id);

        if (pagamento == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pagamento);
    }

    public record PagamentoRequest(
            Long pedidoId,
            BigDecimal valor
    ) {
    }

    public record PagamentoResponse(
            Long pagamentoId,
            Long pedidoId,
            BigDecimal valor,
            String status
    ) {
    }
}