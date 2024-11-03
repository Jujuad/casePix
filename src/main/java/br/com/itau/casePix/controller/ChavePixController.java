package br.com.itau.casePix.controller;

import br.com.itau.casePix.dto.log.LogDTO;
import br.com.itau.casePix.exception.ChavePixInvalidException;
import br.com.itau.casePix.exception.ChavePixNotFoundException;
import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.model.Conta;
import br.com.itau.casePix.enumerators.TipoClienteEnum;
import br.com.itau.casePix.service.ChavePixService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Cria uma nova chave Pix", description = "Cria uma nova chave Pix para uma conta.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Chave Pix criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ChavePix.class))),
            @ApiResponse(responseCode = "400", description = "Limite de chaves atingido ou chave inválida")
    })
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
            LogDTO logErro = LogDTO.logErro("Erro ao criar chave Pix: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "criarChave",
                    "Chave: " + chavePix);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Consulta chaves Pix", description = "Consulta chaves Pix com base nos parâmetros fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de chaves Pix encontradas"),
            @ApiResponse(responseCode = "404", description = "Nenhuma chave Pix encontrada"),
            @ApiResponse(responseCode = "422", description = "Erro ao processar a consulta")
    })
    @GetMapping("/consultas")
    public ResponseEntity<?> consultarChaves(
            @Parameter(description = "ID da chave Pix") @RequestParam(required = false) UUID idChavePix,
            @Parameter(description = "Data de inclusão da chave Pix") @RequestParam(required = false) LocalDateTime dataInclusao,
            @Parameter(description = "Data de inativação da chave Pix") @RequestParam(required = false) LocalDateTime dataInativacao) {

        try {
            List<ChavePix> chaves = chavePixService.consultarChaves(idChavePix, dataInclusao, dataInativacao);
            if (chaves.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma chave Pix encontrada.");
            }
            return ResponseEntity.ok(chaves);
        } catch (ChavePixInvalidException e) {
            LogDTO logErro = LogDTO.logErro("Erro ao consultar chaves Pix: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "consultarChaves",
                    "ID: " + idChavePix);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @Operation(summary = "Atualiza uma chave Pix", description = "Atualiza as informações de uma chave Pix existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chave Pix atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ChavePix.class))),
            @ApiResponse(responseCode = "404", description = "Chave Pix não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização da chave Pix")
    })
    @PutMapping("/{idChavePix}")
    public ResponseEntity<ChavePix> atualizarChave(@PathVariable UUID idChavePix, @Valid @RequestBody ChavePix chavePix) {
        log.info("Iniciando atualização da chave Pix com ID {}: {}", idChavePix, chavePix);
        try {
            ChavePix chaveAtualizada = chavePixService.atualizarChave(idChavePix, chavePix);
            log.info("Chave Pix atualizada com sucesso: {}", chaveAtualizada);
            return ResponseEntity.ok(chaveAtualizada);
        } catch (ChavePixNotFoundException e) {
            LogDTO logErro = LogDTO.logErro("Erro ao atualizar chave Pix: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "atualizarChave",
                    "ID: " + idChavePix);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ChavePixInvalidException e) {
            LogDTO logErro = LogDTO.logErro("Erro ao atualizar chave Pix: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "atualizarChave",
                    "ID: " + idChavePix + ", Chave: " + chavePix);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Obtém uma chave Pix pelo ID", description = "Recupera as informações de uma chave Pix pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chave Pix encontrada",
                    content = @Content(schema = @Schema(implementation = ChavePix.class))),
            @ApiResponse(responseCode = "404", description = "Chave Pix não encontrada")
    })
    @GetMapping("/{idChavePix}")
    public ResponseEntity<ChavePix> obterChave(@PathVariable UUID idChavePix) {
        log.info("Recuperando chave Pix com ID {}", idChavePix);
        try {
            ChavePix chavePix = chavePixService.obterChave(idChavePix);
            log.info("Chave Pix recuperada: {}", chavePix);
            return ResponseEntity.ok(chavePix);
        } catch (ChavePixNotFoundException e) {
            LogDTO logErro = LogDTO.logErro("Erro ao recuperar chave Pix: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "obterChave",
                    "ID: " + idChavePix);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Deleta uma chave Pix pelo ID", description = "Remove uma chave Pix com base no seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Chave Pix deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Chave Pix não encontrada")
    })
    @DeleteMapping("/{idChavePix}")
    public ResponseEntity<Void> deletarChave(@PathVariable UUID idChavePix) {
        log.info("Iniciando a deleção da chave Pix com ID {}", idChavePix);
        try {
            chavePixService.deletarChave(idChavePix);
            log.info("Chave Pix com ID {} deletada com sucesso.", idChavePix);
            return ResponseEntity.noContent().build();
        } catch (ChavePixNotFoundException e) {
            LogDTO logErro = LogDTO.logErro("Erro ao deletar chave Pix: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "deletarChave",
                    "ID: " + idChavePix);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
