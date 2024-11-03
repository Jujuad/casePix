package br.com.itau.casePix.service;

import br.com.itau.casePix.exception.ChavePixInvalidException;
import br.com.itau.casePix.exception.ChavePixNotFoundException;
import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.repository.ChavePixRepository;
import br.com.itau.casePix.validation.ChavePixValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChavePixService {

    private final ChavePixRepository chavePixRepository;

    public ChavePix criarChave(@Valid ChavePix chavePix) {
        log.info("Criando nova chave Pix: {}", chavePix);
        ChavePixValidator.validarChave(chavePix);
        chavePix.setDataHoraInclusao(LocalDateTime.now());
        ChavePix novaChave = chavePixRepository.save(chavePix);
        log.info("Chave Pix criada com sucesso: {}", novaChave);
        return novaChave;
    }

    public List<ChavePix> consultarChaves(UUID idChavePix, LocalDateTime dataInclusao, LocalDateTime dataInativacao) {
        log.info("Consultando chaves Pix com filtros - ID: {}, Data de Inclusão: {}, Data de Inativação: {}",
                idChavePix, dataInclusao, dataInativacao);

        validarFiltros(idChavePix, dataInclusao, dataInativacao);

        if (idChavePix != null) {
            return List.of(chavePixRepository.findById(idChavePix)
                    .orElseThrow(() -> new ChavePixNotFoundException("Chave não encontrada")));
        }

        if (dataInclusao != null) {
            return chavePixRepository.findByDataHoraInclusao(dataInclusao);
        } else if (dataInativacao != null) {
            return chavePixRepository.findByDataHoraInativacao(dataInativacao);
        }

        return chavePixRepository.findAll();
    }

    public ChavePix atualizarChave(UUID idChavePix, @Valid ChavePix chavePixAtualizada) {
        log.info("Atualizando chave Pix com ID {}: {}", idChavePix, chavePixAtualizada);
        ChavePixValidator.validarChave(chavePixAtualizada);
        ChavePix chaveExistente = chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new ChavePixNotFoundException("Chave não encontrada"));

        chaveExistente.setTipoChave(chavePixAtualizada.getTipoChave());
        chaveExistente.setValorChave(chavePixAtualizada.getValorChave());
        chaveExistente.setDataHoraInativacao(null);
        log.info("Chave Pix com ID {} atualizada: {}", idChavePix, chaveExistente);
        return chavePixRepository.save(chaveExistente);
    }

    public ChavePix obterChave(UUID idChavePix) {
        log.info("Recuperando chave Pix com ID {}", idChavePix);
        return chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new ChavePixNotFoundException("Chave não encontrada"));
    }

    public void deletarChave(UUID idChavePix) {
        log.info("Deletando chave Pix com ID {}", idChavePix);
        ChavePix chavePix = chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new ChavePixNotFoundException("Chave não encontrada"));
        chavePix.setDataHoraInativacao(LocalDateTime.now());
        chavePixRepository.save(chavePix);
        log.info("Chave Pix com ID {} foi inativada com sucesso.", idChavePix);
    }

    private void validarFiltros(UUID idChavePix, LocalDateTime dataInclusao, LocalDateTime dataInativacao) {
        if (idChavePix != null && (dataInclusao != null || dataInativacao != null)) {
            throw new ChavePixInvalidException("Quando um ID é fornecido, nenhum outro filtro deve ser aceito.");
        }

        if (dataInclusao != null && dataInativacao != null) {
            throw new ChavePixInvalidException("Não é permitido combinar os filtros de data de inclusão e data de inativação.");
        }
    }
}
