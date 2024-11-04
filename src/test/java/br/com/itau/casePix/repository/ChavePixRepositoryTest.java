package br.com.itau.casePix.repository;

import br.com.itau.casePix.enumerators.TipoChaveEnum;
import br.com.itau.casePix.enumerators.TipoClienteEnum;
import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.model.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ChavePixRepositoryTest {

    @Autowired
    private ChavePixRepository chavePixRepository;

    @Autowired
    private ContaRepository contaRepository;

    private Conta conta;
    private ChavePix chavePix;

    @BeforeEach
    public void setUp() {
        conta = Conta.builder()
                .tipoConta("corrente")
                .numeroAgencia(1234)
                .numeroConta(567890)
                .nomeCorrentista("João")
                .sobrenomeCorrentista("Silva")
                .dataHoraInclusao(LocalDateTime.now())
                .tipoCliente(TipoClienteEnum.PESSOA_FISICA)
                .build();

        conta = contaRepository.save(conta);

        chavePix = ChavePix.builder()
                .conta(conta)
                .tipoChave(TipoChaveEnum.CELULAR)
                .valorChave("11987654321")
                .dataHoraInclusao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)) // Truncar a data
                .build();

        chavePixRepository.save(chavePix);
    }

    @Test
    public void testFindByValorChave() {
        Optional<ChavePix> foundChavePix = chavePixRepository.findByValorChave("11987654321");
        assertThat(foundChavePix).isPresent();
        assertThat(foundChavePix.get().getValorChave()).isEqualTo("11987654321");
    }

    @Test
    public void testFindByDataHoraInclusao() {
        LocalDateTime dataHoraInclusao = chavePix.getDataHoraInclusao(); // Não é necessário truncar aqui

        List<ChavePix> foundChaves = chavePixRepository.findByDataHoraInclusao(dataHoraInclusao);

        // Verifica se a lista de chaves encontradas não está vazia
        assertThat(foundChaves).isNotEmpty();
        // Verifica se a chave encontrada é a que foi salva
        assertThat(foundChaves.get(0).getValorChave()).isEqualTo("11987654321");
    }

    @Test
    public void testFindByDataHoraInativacao() {
        LocalDateTime dataHoraInativacao = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        chavePix.setDataHoraInativacao(dataHoraInativacao);
        chavePixRepository.save(chavePix);
        chavePixRepository.flush();

        List<ChavePix> foundChaves = chavePixRepository.findByDataHoraInativacao(dataHoraInativacao);

        assertThat(foundChaves).isNotEmpty();
        assertThat(foundChaves.get(0).getValorChave()).isEqualTo("11987654321");
    }

    @Test
    public void testFindByValorChave_NotFound() {
        Optional<ChavePix> foundChavePix = chavePixRepository.findByValorChave("99999999999");
        assertThat(foundChavePix).isNotPresent();
    }

    @Test
    public void testChavePixPersistence() {
        ChavePix foundChavePix = chavePixRepository.findById(chavePix.getIdChavePix()).orElse(null);
        assertThat(foundChavePix).isNotNull();
        assertThat(foundChavePix.getValorChave()).isEqualTo("11987654321");
    }

    @Test
    public void testDeleteChavePix() {
        chavePixRepository.delete(chavePix);
        Optional<ChavePix> foundChavePix = chavePixRepository.findById(chavePix.getIdChavePix());
        assertThat(foundChavePix).isNotPresent();
    }
}
