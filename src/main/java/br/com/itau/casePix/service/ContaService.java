package br.com.itau.casePix.service;

import br.com.itau.casePix.exception.ContaNotFoundException;
import br.com.itau.casePix.model.Conta;
import br.com.itau.casePix.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContaService {

    private final ContaRepository contaRepository;

    public Conta criarConta(@Valid Conta conta) {
        log.info("Iniciando a criação de nova conta: {}", conta);
        Conta novaConta = contaRepository.save(conta);
        log.info("Conta criada com sucesso: {}", novaConta);
        return novaConta;
    }

    public List<Conta> consultarContas() {
        log.info("Consultando todas as contas.");
        List<Conta> resultado = contaRepository.findAll();
        log.info("Todas as contas foram recuperadas. Total: {}", resultado.size());
        return resultado;
    }

    public Conta consultarContaPorId(UUID idConta) {
        log.info("Consultando conta com ID {}", idConta);
        return contaRepository.findById(idConta)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada"));
    }

    public Conta atualizarConta(UUID idConta, @Valid Conta contaAtualizada) {
        log.info("Iniciando a atualização da conta com ID {}: {}", idConta, contaAtualizada);
        Conta contaExistente = contaRepository.findById(idConta)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada"));

        log.info("Conta existente antes da atualização: {}", contaExistente);

        contaExistente.setTipoConta(contaAtualizada.getTipoConta());

        Conta contaAtualizadaFinal = contaRepository.save(contaExistente);
        log.info("Conta com ID {} atualizada com sucesso: {}", idConta, contaAtualizadaFinal);
        return contaAtualizadaFinal;
    }


    public Conta obterConta(UUID idConta) {
        log.info("Recuperando conta com ID {}", idConta);
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada"));
        log.info("Conta recuperada: {}", conta);
        return conta;
    }

    public void deletarConta(UUID idConta) {
        log.info("Iniciando a inativação da conta com ID {}", idConta);
        Conta conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada"));

        log.info("Conta antes da inativação: {}", conta);
        contaRepository.delete(conta);
        log.info("Conta com ID {} foi deletada com sucesso.", idConta);
    }
}
