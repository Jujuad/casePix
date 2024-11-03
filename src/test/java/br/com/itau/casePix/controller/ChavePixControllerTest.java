package br.com.itau.casePix.controller;

import br.com.itau.casePix.exception.ChavePixNotFoundException;
import br.com.itau.casePix.exception.ChavePixNotFoundExceptionTest;
import br.com.itau.casePix.enumerators.TipoClienteEnum;
import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.model.Conta;
import br.com.itau.casePix.service.ChavePixService;
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
import static org.mockito.Mockito.*;

public class ChavePixControllerTest {

    @InjectMocks
    private ChavePixController chavePixController;

    @Mock
    private ChavePixService chavePixService;

    private ChavePix chavePix;
    private Conta conta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        conta = new Conta();
        conta.setTipoCliente(TipoClienteEnum.PESSOA_FISICA);
        conta.setChavesPix(Collections.emptyList());

        chavePix = new ChavePix();
        chavePix.setConta(conta);
        chavePix.setIdChavePix(UUID.randomUUID());
        chavePix.setDataHoraInclusao(LocalDateTime.now());
    }

    @Test
    public void testCriarChaveSucesso() {
        when(chavePixService.criarChave(any(ChavePix.class))).thenReturn(chavePix);

        ResponseEntity<ChavePix> response = chavePixController.criarChave(chavePix);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(chavePix, response.getBody());
    }

    @Test
    public void testCriarChaveLimiteAtingido() {
        conta.setChavesPix(Collections.nCopies(5, new ChavePix()));

        ResponseEntity<ChavePix> response = chavePixController.criarChave(chavePix);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testConsultarChavesSucesso() {
        when(chavePixService.consultarChaves(null, null, null)).thenReturn(List.of(chavePix));

        ResponseEntity<?> response = chavePixController.consultarChaves(null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(chavePix), response.getBody());
    }

    @Test
    public void testConsultarChavesNenhumaEncontrada() {
        when(chavePixService.consultarChaves(null, null, null)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = chavePixController.consultarChaves(null, null, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Nenhuma chave Pix encontrada.", response.getBody());
    }

    @Test
    public void testAtualizarChaveSucesso() {
        UUID idChavePix = UUID.randomUUID();
        when(chavePixService.atualizarChave(eq(idChavePix), any(ChavePix.class))).thenReturn(chavePix);

        ResponseEntity<ChavePix> response = chavePixController.atualizarChave(idChavePix, chavePix);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(chavePix, response.getBody());
    }

    @Test
    public void testAtualizarChaveNaoEncontrada() {
        UUID idChavePix = UUID.randomUUID();
        when(chavePixService.atualizarChave(eq(idChavePix), any(ChavePix.class)))
                .thenThrow(new ChavePixNotFoundException("Chave não encontrada"));

        ResponseEntity<ChavePix> response = chavePixController.atualizarChave(idChavePix, chavePix);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testObterChaveSucesso() {
        UUID idChavePix = UUID.randomUUID();
        when(chavePixService.obterChave(idChavePix)).thenReturn(chavePix);

        ResponseEntity<ChavePix> response = chavePixController.obterChave(idChavePix);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(chavePix, response.getBody());
    }

    @Test
    public void testObterChaveNaoEncontrada() {
        UUID idChavePix = UUID.randomUUID();
        when(chavePixService.obterChave(idChavePix)).thenThrow(new ChavePixNotFoundException("Chave não encontrada"));

        ResponseEntity<ChavePix> response = chavePixController.obterChave(idChavePix);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeletarChaveSucesso() {
        UUID idChavePix = UUID.randomUUID();
        doNothing().when(chavePixService).deletarChave(idChavePix);

        ResponseEntity<Void> response = chavePixController.deletarChave(idChavePix);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeletarChaveNaoEncontrada() {
        UUID idChavePix = UUID.randomUUID();
        doThrow(new ChavePixNotFoundException("Chave não encontrada")).when(chavePixService).deletarChave(idChavePix);

        ResponseEntity<Void> response = chavePixController.deletarChave(idChavePix);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
