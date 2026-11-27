package com.visionquest.notificacoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacoesController {

    private final List<NotificacaoResponse> notificacoes =
            new CopyOnWriteArrayList<>();

    private final AtomicLong proximoId = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<?> criarNotificacao(
            @RequestBody CriarNotificacaoRequest request) {

        if (request.usuarioId() == null
                || request.tipo() == null
                || request.tipo().isBlank()
                || request.mensagem() == null
                || request.mensagem().isBlank()) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "mensagem",
                            "Informe o usuário, o tipo e a mensagem."
                    )
            );
        }

        NotificacaoResponse notificacao = new NotificacaoResponse(
                proximoId.getAndIncrement(),
                request.usuarioId(),
                request.tipo(),
                request.mensagem(),
                LocalDateTime.now()
        );

        notificacoes.add(notificacao);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacao);
    }

    @GetMapping
    public List<NotificacaoResponse> listarNotificacoes() {
        return List.copyOf(notificacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarNotificacao(@PathVariable Long id) {
        return notificacoes.stream()
                .filter(notificacao -> notificacao.id().equals(id))
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public record CriarNotificacaoRequest(
            Long usuarioId,
            String tipo,
            String mensagem
    ) {
    }

    public record NotificacaoResponse(
            Long id,
            Long usuarioId,
            String tipo,
            String mensagem,
            LocalDateTime dataCriacao
    ) {
    }
}