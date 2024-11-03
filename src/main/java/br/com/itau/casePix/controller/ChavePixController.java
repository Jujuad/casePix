package br.com.itau.casePix.controller;

import br.com.itau.casePix.exception.ChavePixInvalidException;
import br.com.itau.casePix.exception.ChavePixNotFoundException;
import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.model.Conta;
import br.com.itau.casePix.enumerators.TipoClienteEnum;
import br.com.itau.casePix.service.ChavePixService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
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
        log.info("Iniciando criação de chave Pix: {}", chavePix);
        try {
            Conta conta = chavePix.getConta();
            long quantidadeChaves = conta.getChavesPix().size();

            if (conta.getTipoCliente() == TipoClienteEnum.PESSOA_FISICA && quantidadeChaves >= 5) {
                throw new ChavePixInvalidException("Limite de 5 chaves para Pessoa Física atingido.");
            }
            if (conta.getTipoCliente() == TipoClienteEnum.PESSOA_JURIDICA && quantidadeChaves >= 20) {
                throw new ChavePixInvalidException("Limite de 20 chaves para Pessoa Jurídica atingido.");
            }

            ChavePix novaChave = chavePixService.criarChave(chavePix);
            log.info("Chave Pix criada com sucesso: {}", novaChave);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaChave);
        } catch (ChavePixInvalidException e) {
            log.error("Erro ao criar chave Pix: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/consultas")
    public ResponseEntity<?> consultarChaves(
            @RequestParam(required = false) UUID idChavePix,
            @RequestParam(required = false) LocalDateTime dataInclusao,
            @RequestParam(required = false) LocalDateTime dataInativacao) {

        List<ChavePix> chaves;
        try {
            chaves = chavePixService.consultarChaves(idChavePix, dataInclusao, dataInativacao);
            if (chaves.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma chave Pix encontrada.");
            }
            return ResponseEntity.ok(chaves);
        } catch (ChavePixInvalidException e) {
            log.error("Erro ao consultar chaves Pix: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @PutMapping("/{idChavePix}")
    public ResponseEntity<ChavePix> atualizarChave(@PathVariable UUID idChavePix, @Valid @RequestBody ChavePix chavePix) {
        log.info("Iniciando atualização da chave Pix com ID {}: {}", idChavePix, chavePix);
        try {
            ChavePix chaveAtualizada = chavePixService.atualizarChave(idChavePix, chavePix);
            log.info("Chave Pix atualizada com sucesso: {}", chaveAtualizada);
            return ResponseEntity.ok(chaveAtualizada);
        } catch (ChavePixNotFoundException e) {
            log.error("Erro ao atualizar chave Pix: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ChavePixInvalidException e) {
            log.error("Erro ao atualizar chave Pix: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{idChavePix}")
    public ResponseEntity<ChavePix> obterChave(@PathVariable UUID idChavePix) {
        log.info("Recuperando chave Pix com ID {}", idChavePix);
        try {
            ChavePix chavePix = chavePixService.obterChave(idChavePix);
            log.info("Chave Pix recuperada: {}", chavePix);
            return ResponseEntity.ok(chavePix);
        } catch (ChavePixNotFoundException e) {
            log.error("Erro ao recuperar chave Pix: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{idChavePix}")
    public ResponseEntity<Void> deletarChave(@PathVariable UUID idChavePix) {
        log.info("Iniciando a deleção da chave Pix com ID {}", idChavePix);
        try {
            chavePixService.deletarChave(idChavePix);
            log.info("Chave Pix com ID {} deletada com sucesso.", idChavePix);
            return ResponseEntity.noContent().build();
        } catch (ChavePixNotFoundException e) {
            log.error("Erro ao deletar chave Pix: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
