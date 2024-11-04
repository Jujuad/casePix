package br.com.itau.casePix.controller;

import br.com.itau.casePix.exception.ContaNotFoundException;
import br.com.itau.casePix.model.Conta;
import br.com.itau.casePix.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ContaControllerTest {

    @InjectMocks
    private ContaController contaController;

    @Mock
    private ContaService contaService;

    private Conta conta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        conta = new Conta();
        conta.setIdConta(UUID.randomUUID());
        conta.setTipoConta("Corrente");
        conta.setNumeroAgencia(1234);
        conta.setNumeroConta(567890);
        conta.setNomeCorrentista("João Silva");
        conta.setDataHoraInclusao(LocalDateTime.now());
    }

    @Test
    public void testCriarContaSucesso() {
        when(contaService.criarConta(any(Conta.class))).thenReturn(conta);

        ResponseEntity<Conta> response = contaController.criarConta(conta);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(conta, response.getBody());
    }

    @Test
    public void testConsultarContasSucesso() {
        when(contaService.consultarContas()).thenReturn(List.of(conta));

        ResponseEntity<List<Conta>> response = contaController.consultarContas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(conta), response.getBody());
    }

    @Test
    public void testConsultarContasNenhumaEncontrada() {
        when(contaService.consultarContas()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Conta>> response = contaController.consultarContas();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }



    @Test
    public void testAtualizarContaSucesso() {
        UUID idConta = conta.getIdConta();
        when(contaService.atualizarConta(eq(idConta), any(Conta.class))).thenReturn(conta);

        ResponseEntity<Conta> response = contaController.atualizarConta(idConta, conta);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(conta, response.getBody());
    }

    @Test
    public void testAtualizarContaNaoEncontrada() {
        UUID idConta = conta.getIdConta();
        when(contaService.atualizarConta(eq(idConta), any(Conta.class)))
                .thenThrow(new ContaNotFoundException("Conta não encontrada"));

        ResponseEntity<Conta> response = contaController.atualizarConta(idConta, conta);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testConsultarContaPorIdSucesso() {
        UUID idConta = conta.getIdConta();
        when(contaService.consultarContaPorId(idConta)).thenReturn(conta);

        ResponseEntity<Conta> response = contaController.consultarContaPorId(idConta);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(conta, response.getBody());
    }

    @Test
    public void testConsultarContaPorIdNaoEncontrada() {
        UUID idConta = conta.getIdConta();
        when(contaService.consultarContaPorId(idConta)).thenThrow(new ContaNotFoundException("Conta não encontrada"));

        ResponseEntity<Conta> response = contaController.consultarContaPorId(idConta);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeletarContaSucesso() {
        UUID idConta = conta.getIdConta();
        doNothing().when(contaService).deletarConta(idConta);

        ResponseEntity<Void> response = contaController.deletarConta(idConta);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeletarContaNaoEncontrada() {
        UUID idConta = conta.getIdConta();
        doThrow(new ContaNotFoundException("Conta não encontrada")).when(contaService).deletarConta(idConta);

        ResponseEntity<Void> response = contaController.deletarConta(idConta);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
