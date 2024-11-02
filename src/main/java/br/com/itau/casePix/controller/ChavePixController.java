package br.com.itau.casePix.controller;

import br.com.itau.casePix.service.ChavePixService;
import br.com.itau.casePix.model.ChavePix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/chaves-pix")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChavePixController {

    private final ChavePixService chavePixService;

    @PostMapping
    public ResponseEntity<ChavePix> criarChave(@Valid @RequestBody ChavePix chavePix) {
        ChavePix novaChave = chavePixService.criarChave(chavePix);
        log.info("Chave Pix criada: {}", novaChave);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaChave);
    }

    @PutMapping("/{idChavePix}")
    public ResponseEntity<ChavePix> atualizarChave(@PathVariable UUID idChavePix, @Valid @RequestBody ChavePix chavePix) {
        ChavePix chaveAtualizada = chavePixService.atualizarChave(idChavePix, chavePix);
        log.info("Chave Pix atualizada: {}", chaveAtualizada);
        return ResponseEntity.ok(chaveAtualizada);
    }

    @GetMapping("/{idChavePix}")
    public ResponseEntity<ChavePix> obterChave(@PathVariable UUID idChavePix) {
        ChavePix chavePix = chavePixService.obterChave(idChavePix);
        log.info("Chave Pix recuperada: {}", chavePix);
        return ResponseEntity.ok(chavePix);
    }

    @DeleteMapping("/{idChavePix}")
    public ResponseEntity<Void> deletarChave(@PathVariable UUID idChavePix) {
        chavePixService.deletarChave(idChavePix);
        log.info("Chave Pix com ID {} deletada.", idChavePix);
        return ResponseEntity.noContent().build();
    }
}
