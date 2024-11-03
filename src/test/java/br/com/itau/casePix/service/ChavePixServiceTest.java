package br.com.itau.casePix.service;

import br.com.itau.casePix.enumerators.TipoChaveEnum;
import br.com.itau.casePix.exception.ChavePixInvalidException;
import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.repository.ChavePixRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChavePixServiceTest {

    @InjectMocks
    private ChavePixService chavePixService;

    @Mock
    private ChavePixRepository chavePixRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarChave() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.valueOf("EMAIL"));
        chavePix.setValorChave("teste@example.com");
        chavePix.setDataHoraInclusao(LocalDateTime.now());

        when(chavePixRepository.save(any(ChavePix.class))).thenReturn(chavePix);

        ChavePix novaChave = chavePixService.criarChave(chavePix);

        assertNotNull(novaChave);
        assertEquals(chavePix.getValorChave(), novaChave.getValorChave());
        verify(chavePixRepository, times(1)).save(chavePix);
    }


    @Test
    public void testConsultarChavesComId() {
        UUID idChavePix = UUID.randomUUID();
        ChavePix chavePix = new ChavePix();
        when(chavePixRepository.findById(idChavePix)).thenReturn(Optional.of(chavePix));

        List<ChavePix> resultado = chavePixService.consultarChaves(idChavePix, null, null);

        assertEquals(1, resultado.size());
        assertEquals(chavePix, resultado.get(0));
        verify(chavePixRepository, times(1)).findById(idChavePix);
    }

    @Test
    public void testConsultarChavesComDataInclusao() {
        LocalDateTime dataInclusao = LocalDateTime.now();
        ChavePix chavePix = new ChavePix();
        when(chavePixRepository.findByDataHoraInclusao(dataInclusao)).thenReturn(List.of(chavePix));

        List<ChavePix> resultado = chavePixService.consultarChaves(null, dataInclusao, null);

        assertEquals(1, resultado.size());
        assertEquals(chavePix, resultado.get(0));
        verify(chavePixRepository, times(1)).findByDataHoraInclusao(dataInclusao);
    }

    @Test
    public void testAtualizarChave() {
        UUID idChavePix = UUID.randomUUID();
        ChavePix chavePixExistente = new ChavePix();
        chavePixExistente.setIdChavePix(idChavePix);
        chavePixExistente.setTipoChave(TipoChaveEnum.valueOf("EMAIL"));
        chavePixExistente.setValorChave("example@example.com");

        ChavePix chavePixAtualizada = new ChavePix();
        chavePixAtualizada.setIdChavePix(idChavePix);
        chavePixAtualizada.setTipoChave(TipoChaveEnum.valueOf("EMAIL"));
        chavePixAtualizada.setValorChave("new_example@example.com");

        when(chavePixRepository.findById(idChavePix)).thenReturn(Optional.of(chavePixExistente));
        when(chavePixRepository.save(chavePixExistente)).thenReturn(chavePixAtualizada);

        ChavePix resultado = chavePixService.atualizarChave(idChavePix, chavePixAtualizada);

        assertEquals(chavePixAtualizada, resultado);
        verify(chavePixRepository, times(1)).findById(idChavePix);
        verify(chavePixRepository, times(1)).save(chavePixExistente);
    }


    @Test
    public void testObterChave() {
        UUID idChavePix = UUID.randomUUID();
        ChavePix chavePix = new ChavePix();
        when(chavePixRepository.findById(idChavePix)).thenReturn(Optional.of(chavePix));

        ChavePix resultado = chavePixService.obterChave(idChavePix);

        assertEquals(chavePix, resultado);
        verify(chavePixRepository, times(1)).findById(idChavePix);
    }

    @Test
    public void testDeletarChave() {
        UUID idChavePix = UUID.randomUUID();
        ChavePix chavePix = new ChavePix();
        when(chavePixRepository.findById(idChavePix)).thenReturn(Optional.of(chavePix));

        chavePixService.deletarChave(idChavePix);

        verify(chavePixRepository, times(1)).findById(idChavePix);
        verify(chavePixRepository, times(1)).save(chavePix);
    }

    @Test
    public void testValidarFiltrosComIDAndDataInclusao() {
        UUID idChavePix = UUID.randomUUID();
        LocalDateTime dataInclusao = LocalDateTime.now();

        Exception exception = assertThrows(ChavePixInvalidException.class, () -> {
            chavePixService.consultarChaves(idChavePix, dataInclusao, null);
        });

        assertEquals("Quando um ID é fornecido, nenhum outro filtro deve ser aceito.", exception.getMessage());
    }

    @Test
    public void testValidarFiltrosComDataInclusaoAndDataInativacao() {
        LocalDateTime dataInclusao = LocalDateTime.now();
        LocalDateTime dataInativacao = LocalDateTime.now();

        Exception exception = assertThrows(ChavePixInvalidException.class, () -> {
            chavePixService.consultarChaves(null, dataInclusao, dataInativacao);
        });

        assertEquals("Não é permitido combinar os filtros de data de inclusão e data de inativação.", exception.getMessage());
    }
}

