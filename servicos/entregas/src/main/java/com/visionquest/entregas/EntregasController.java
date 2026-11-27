package com.visionquest.entregas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/entregas")
public class EntregasController {

    private final Map<Long, EntregaResponse> entregas =
            new ConcurrentHashMap<>();

    private final AtomicLong proximoId = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<?> criarEntrega(
            @RequestBody CriarEntregaRequest request) {

        if (request.pedidoId() == null
                || request.pedidoId() <= 0
                || request.endereco() == null
                || request.endereco().isBlank()) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "mensagem",
                            "Informe um pedido válido e o endereço de entrega."
                    )
            );
        }

        long id = proximoId.getAndIncrement();

        EntregaResponse entrega = new EntregaResponse(
                id,
                request.pedidoId(),
                request.endereco(),
                null,
                "AGUARDANDO_ENTREGADOR"
        );

        entregas.put(id, entrega);

        return ResponseEntity.status(HttpStatus.CREATED).body(entrega);
    }

    @GetMapping
    public List<EntregaResponse> listarEntregas() {
        return List.copyOf(entregas.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEntrega(@PathVariable Long id) {
        EntregaResponse entrega = entregas.get(id);

        if (entrega == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(entrega);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusRequest request) {

        EntregaResponse entrega = entregas.get(id);

        if (entrega == null) {
            return ResponseEntity.notFound().build();
        }

        if (request.status() == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("mensagem", "Informe o novo status.")
            );
        }

        String novoStatus = request.status();

        boolean transicaoValida =
                (entrega.status().equals("AGUARDANDO_ENTREGADOR")
                        && novoStatus.equals("EM_ROTA"))
                ||
                (entrega.status().equals("EM_ROTA")
                        && novoStatus.equals("ENTREGUE"));

        if (!transicaoValida) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "mensagem",
                            "Transição de status inválida."
                    )
            );
        }

        EntregaResponse atualizada = new EntregaResponse(
                entrega.id(),
                entrega.pedidoId(),
                entrega.endereco(),
                entrega.entregador(),
                novoStatus
        );

        entregas.put(id, atualizada);

        return ResponseEntity.ok(atualizada);
    }

    @PatchMapping("/{id}/entregador")
    public ResponseEntity<?> atribuirEntregador(
            @PathVariable Long id,
            @RequestBody AtribuirEntregadorRequest request) {

        EntregaResponse entrega = entregas.get(id);

        if (entrega == null) {
            return ResponseEntity.notFound().build();
        }

        if (request.entregador() == null
                || request.entregador().isBlank()) {

            return ResponseEntity.badRequest().body(
                    Map.of("mensagem", "Informe o nome do entregador.")
            );
        }

        EntregaResponse atualizada = new EntregaResponse(
                entrega.id(),
                entrega.pedidoId(),
                entrega.endereco(),
                request.entregador(),
                entrega.status()
        );

        entregas.put(id, atualizada);

        return ResponseEntity.ok(atualizada);
    }

    public record CriarEntregaRequest(
            Long pedidoId,
            String endereco
    ) {
    }

    public record AtualizarStatusRequest(
            String status
    ) {
    }

    public record AtribuirEntregadorRequest(
            String entregador
    ) {
    }

    public record EntregaResponse(
            Long id,
            Long pedidoId,
            String endereco,
            String entregador,
            String status
    ) {
    }
}