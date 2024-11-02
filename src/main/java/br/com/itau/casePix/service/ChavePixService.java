package br.com.itau.casePix.service;

import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.repository.ChavePixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChavePixService {

    private final ChavePixRepository chavePixRepository;

    public ChavePix criarChave(@Valid ChavePix chavePix) {
        chavePix.setDataHoraInclusao(LocalDateTime.now());
        log.info("Criando nova chave Pix: {}", chavePix);
        return chavePixRepository.save(chavePix);
    }

    public ChavePix atualizarChave(UUID idChavePix, @Valid ChavePix chavePixAtualizada) {
        ChavePix chaveExistente = chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new RuntimeException("Chave não encontrada"));

        chaveExistente.setTipoChave(chavePixAtualizada.getTipoChave());
        chaveExistente.setValorChave(chavePixAtualizada.getValorChave());
        chaveExistente.setDataHoraInativacao(null);
        log.info("Atualizando chave Pix com ID {}: {}", idChavePix, chaveExistente);
        return chavePixRepository.save(chaveExistente);
    }

    public ChavePix obterChave(UUID idChavePix) {
        return chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new RuntimeException("Chave não encontrada"));
    }

    public void deletarChave(UUID idChavePix) {
        ChavePix chavePix = chavePixRepository.findById(idChavePix)
                .orElseThrow(() -> new RuntimeException("Chave não encontrada"));
        chavePix.setDataHoraInativacao(LocalDateTime.now());
        chavePixRepository.save(chavePix);
        log.info("Chave Pix com ID {} foi inativada.", idChavePix);
    }
}
