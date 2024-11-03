package br.com.itau.casePix.controller;

import br.com.itau.casePix.dto.log.LogDTO;
import br.com.itau.casePix.exception.ContaNotFoundException;
import br.com.itau.casePix.model.Conta;
import br.com.itau.casePix.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
@Slf4j
public class ContaController {

    private final ContaService contaService;

    @Operation(summary = "Cria uma nova conta", description = "Cria uma nova conta no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso", content = @Content(schema = @Schema(implementation = Conta.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação da conta")
    })
    @PostMapping
    public ResponseEntity<Conta> criarConta(@Valid @RequestBody Conta conta) {
        log.info("Iniciando criação de nova conta: {}", conta);
        try {
            Conta novaConta = contaService.criarConta(conta);
            log.info("Conta criada com sucesso: {}", novaConta);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
        } catch (Exception e) {
            LogDTO logErro = LogDTO.logErro("Erro ao criar conta: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "criarConta",
                    "Dados da Conta: " + conta);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Consulta todas as contas", description = "Retorna uma lista de todas as contas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de contas encontrada", content = @Content(schema = @Schema(implementation = Conta.class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma conta encontrada")
    })
    @GetMapping
    public ResponseEntity<List<Conta>> consultarContas() {
        log.info("Consultando todas as contas.");
        List<Conta> contas = contaService.consultarContas();
        if (contas.isEmpty()) {
            log.warn("Nenhuma conta encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Consulta de contas realizada com sucesso. Total de contas: {}", contas.size());
        return ResponseEntity.ok(contas);
    }

    @Operation(summary = "Consulta uma conta pelo ID", description = "Retorna as informações de uma conta com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta encontrada", content = @Content(schema = @Schema(implementation = Conta.class))),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @GetMapping("/{idConta}")
    public ResponseEntity<Conta> consultarContaPorId(@PathVariable UUID idConta) {
        log.info("Consultando conta com ID {}", idConta);
        try {
            Conta conta = contaService.consultarContaPorId(idConta);
            log.info("Conta encontrada: {}", conta);
            return ResponseEntity.ok(conta);
        } catch (ContaNotFoundException e) {
            LogDTO logErro = LogDTO.logErro("Erro ao consultar conta: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "consultarContaPorId",
                    "ID da Conta: " + idConta);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Atualiza uma conta", description = "Atualiza as informações de uma conta existente com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso", content = @Content(schema = @Schema(implementation = Conta.class))),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização da conta")
    })
    @PutMapping("/{idConta}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable UUID idConta, @Valid @RequestBody Conta conta) {
        log.info("Iniciando atualização da conta com ID {}: {}", idConta, conta);
        try {
            Conta contaAtualizada = contaService.atualizarConta(idConta, conta);
            log.info("Conta atualizada com sucesso: {}", contaAtualizada);
            return ResponseEntity.ok(contaAtualizada);
        } catch (ContaNotFoundException e) {
            LogDTO logErro = LogDTO.logErro("Erro ao atualizar conta: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "atualizarConta",
                    "ID da Conta: " + idConta);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            LogDTO logErro = LogDTO.logErro("Erro ao atualizar conta: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "atualizarConta",
                    "ID da Conta: " + idConta + ", Dados da Conta: " + conta);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Deleta uma conta pelo ID", description = "Remove uma conta do sistema com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Conta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @DeleteMapping("/{idConta}")
    public ResponseEntity<Void> deletarConta(@PathVariable UUID idConta) {
        log.info("Iniciando deleção da conta com ID {}", idConta);
        try {
            contaService.deletarConta(idConta);
            log.info("Conta com ID {} deletada com sucesso.", idConta);
            return ResponseEntity.noContent().build();
        } catch (ContaNotFoundException e) {
            LogDTO logErro = LogDTO.logErro("Erro ao deletar conta: " + e.getMessage(),
                    this.getClass().getSimpleName(),
                    "deletarConta",
                    "ID da Conta: " + idConta);
            log.error("{}", logErro);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
