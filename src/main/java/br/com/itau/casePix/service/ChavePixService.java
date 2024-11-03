package br.com.itau.casePix.service;

import br.com.itau.casePix.dto.log.LogDTO;
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
        log.info("Iniciando a criação de nova chave Pix: {}", chavePix);
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

        List<ChavePix> resultado;
        if (idChavePix != null) {
            ChavePix chave = chavePixRepository.findById(idChavePix)
                    .orElseThrow(() -> new ChavePixNotFoundException("Chave não encontrada"));
            resultado = List.of(chave);
            log.info("Chave Pix encontrada: {}", chave);
        } else if (dataInclusao != null) {
            resultado = chavePixRepository.findByDataHoraInclusao(dataInclusao);
            log.info("Chaves Pix encontradas com Data de Inclusão {}: {}", dataInclusao, resultado);
        } else if (dataInativacao != null) {
            resultado = chavePixRepository.findByDataHoraInativacao(dataInativacao);
            log.info("Chaves Pix encontradas com Data de Inativação {}: {}", dataInativacao, resultado);
        } else {
            resultado = chavePixRepository.findAll();
            log.info("Todas as chaves Pix foram recuperadas. Total: {}", resultado.size());
        }

        return resultado;
    }

    public ChavePix atualizarChave(UUID idChavePix, @Valid ChavePix chavePixAtualizada) {
        log.info("Iniciando a atualização da chave Pix com ID {}: {}", idChavePix, chavePixAtualizada);
        ChavePixValidator.validarChave(chavePixAtualizada);
        ChavePix chaveExistente = chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new ChavePixNotFoundException("Chave não encontrada"));

        log.info("Chave Pix existente antes da atualização: {}", chaveExistente);

        chaveExistente.setTipoChave(chavePixAtualizada.getTipoChave());
        chaveExistente.setValorChave(chavePixAtualizada.getValorChave());
        chaveExistente.setDataHoraInativacao(null);

        ChavePix chaveAtualizada = chavePixRepository.save(chaveExistente);
        log.info("Chave Pix com ID {} atualizada com sucesso: {}", idChavePix, chaveAtualizada);
        return chaveAtualizada;
    }

    public ChavePix obterChave(UUID idChavePix) {
        log.info("Recuperando chave Pix com ID {}", idChavePix);
        ChavePix chavePix = chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new ChavePixNotFoundException("Chave não encontrada"));
        log.info("Chave Pix recuperada: {}", chavePix);
        return chavePix;
    }

    public void deletarChave(UUID idChavePix) {
        log.info("Iniciando a inativação da chave Pix com ID {}", idChavePix);
        ChavePix chavePix = chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new ChavePixNotFoundException("Chave não encontrada"));

        log.info("Chave Pix antes da inativação: {}", chavePix);
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
