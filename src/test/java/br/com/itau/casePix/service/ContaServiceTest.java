package br.com.itau.casePix.service;

import br.com.itau.casePix.exception.ContaNotFoundException;
import br.com.itau.casePix.model.Conta;
import br.com.itau.casePix.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    private Conta conta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conta = new Conta();
        conta.setIdConta(UUID.randomUUID());
        conta.setTipoConta("Corrente");
    }

    @Test
    void testCriarConta() {
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        Conta novaConta = contaService.criarConta(conta);

        assertNotNull(novaConta);
        assertEquals(conta.getIdConta(), novaConta.getIdConta());
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    void testConsultarContas() {
        when(contaRepository.findAll()).thenReturn(Collections.singletonList(conta));

        List<Conta> resultado = contaService.consultarContas();

        assertEquals(1, resultado.size());
        assertEquals(conta.getIdConta(), resultado.get(0).getIdConta());
        verify(contaRepository, times(1)).findAll();
    }

    @Test
    void testConsultarContaPorId() {
        when(contaRepository.findById(conta.getIdConta())).thenReturn(Optional.of(conta));

        Conta resultado = contaService.consultarContaPorId(conta.getIdConta());

        assertNotNull(resultado);
        assertEquals(conta.getIdConta(), resultado.getIdConta());
        verify(contaRepository, times(1)).findById(conta.getIdConta());
    }

    @Test
    void testConsultarContaPorId_NotFound() {
        UUID idInexistente = UUID.randomUUID();
        when(contaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ContaNotFoundException.class, () -> {
            contaService.consultarContaPorId(idInexistente);
        });

        assertEquals("Conta não encontrada", exception.getMessage());
        verify(contaRepository, times(1)).findById(idInexistente);
    }

    @Test
    void testAtualizarConta() {
        when(contaRepository.findById(conta.getIdConta())).thenReturn(Optional.of(conta));

        Conta contaAtualizada = new Conta();
        contaAtualizada.setTipoConta("Poupança");

        when(contaRepository.save(any(Conta.class))).thenAnswer(invocation -> {
            Conta c = invocation.getArgument(0);
            c.setIdConta(conta.getIdConta());
            return c;
        });

        Conta resultado = contaService.atualizarConta(conta.getIdConta(), contaAtualizada);

        assertNotNull(resultado);
        assertEquals("Poupança", resultado.getTipoConta());

        verify(contaRepository, times(1)).save(any(Conta.class));
    }



    @Test
    void testAtualizarConta_NotFound() {
        UUID idInexistente = UUID.randomUUID();
        when(contaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ContaNotFoundException.class, () -> {
            contaService.atualizarConta(idInexistente, conta);
        });

        assertEquals("Conta não encontrada", exception.getMessage());
        verify(contaRepository, times(1)).findById(idInexistente);
    }

    @Test
    void testObterConta() {
        when(contaRepository.findById(conta.getIdConta())).thenReturn(Optional.of(conta));

        Conta resultado = contaService.obterConta(conta.getIdConta());

        assertNotNull(resultado);
        assertEquals(conta.getIdConta(), resultado.getIdConta());
        verify(contaRepository, times(1)).findById(conta.getIdConta());
    }

    @Test
    void testObterConta_NotFound() {
        UUID idInexistente = UUID.randomUUID();
        when(contaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ContaNotFoundException.class, () -> {
            contaService.obterConta(idInexistente);
        });

        assertEquals("Conta não encontrada", exception.getMessage());
        verify(contaRepository, times(1)).findById(idInexistente);
    }

    @Test
    void testDeletarConta() {
        when(contaRepository.findById(conta.getIdConta())).thenReturn(Optional.of(conta));

        contaService.deletarConta(conta.getIdConta());

        verify(contaRepository, times(1)).delete(conta);
    }

    @Test
    void testDeletarConta_NotFound() {
        UUID idInexistente = UUID.randomUUID();
        when(contaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ContaNotFoundException.class, () -> {
            contaService.deletarConta(idInexistente);
        });

        assertEquals("Conta não encontrada", exception.getMessage());
        verify(contaRepository, times(1)).findById(idInexistente);
    }
}
