package br.com.itau.casePix.repository;

import br.com.itau.casePix.enumerators.TipoClienteEnum;
import br.com.itau.casePix.model.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ContaRepositoryTest {

    @Autowired
    private ContaRepository contaRepository;

    private Conta conta;

    @BeforeEach
    public void setUp() {
        conta = Conta.builder()
                .tipoConta("corrente")
                .numeroAgencia(1234)
                .numeroConta(567890)
                .nomeCorrentista("João")
                .sobrenomeCorrentista("Silva")
                .tipoCliente(TipoClienteEnum.PESSOA_FISICA)
                .build();

        conta = contaRepository.save(conta);
    }

    @Test
    public void testFindById() {
        Optional<Conta> foundConta = contaRepository.findById(conta.getIdConta());
        assertThat(foundConta).isPresent();
        assertThat(foundConta.get().getNomeCorrentista()).isEqualTo("João");
    }

    @Test
    public void testDeleteConta() {
        contaRepository.delete(conta);
        Optional<Conta> foundConta = contaRepository.findById(conta.getIdConta());
        assertThat(foundConta).isNotPresent();
    }

    @Test
    public void testSaveConta() {
        Conta newConta = Conta.builder()
                .tipoConta("poupança")
                .numeroAgencia(4321)
                .numeroConta(987654)
                .nomeCorrentista("Maria")
                .sobrenomeCorrentista("Oliveira")
                .tipoCliente(TipoClienteEnum.PESSOA_JURIDICA)
                .build();

        Conta savedConta = contaRepository.save(newConta);
        assertThat(savedConta).isNotNull();
        assertThat(savedConta.getIdConta()).isNotNull();
        assertThat(savedConta.getNomeCorrentista()).isEqualTo("Maria");
    }

    @Test
    public void testUpdateConta() {
        conta.setNomeCorrentista("Carlos");
        Conta updatedConta = contaRepository.save(conta);
        assertThat(updatedConta.getNomeCorrentista()).isEqualTo("Carlos");
    }
}
