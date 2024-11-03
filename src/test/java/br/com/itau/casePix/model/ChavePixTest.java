package br.com.itau.casePix.model;

import br.com.itau.casePix.enumerators.TipoClienteEnum;
import br.com.itau.casePix.enumerators.TipoChaveEnumTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChavePixTest {

    @Test
    void testChavePixCreation() {
        Conta conta = new Conta();
        conta.setTipoConta("Corrente");
        conta.setNumeroAgencia(1234);
        conta.setNumeroConta(567890);
        conta.setNomeCorrentista("João");
        conta.setSobrenomeCorrentista("Silva");
        conta.setTipoCliente(TipoClienteEnum.PESSOA_FISICA);
        conta.setDataHoraInclusao(LocalDateTime.now());

        ChavePix chavePix = ChavePix.builder()
                .conta(conta)
                .tipoChave(TipoChaveEnumTest.CPF)
                .valorChave("123.456.789-00")
                .dataHoraInclusao(LocalDateTime.now())
                .build();

        assertNotNull(chavePix);
        assertNotNull(chavePix.getIdChavePix());
        assertEquals("123.456.789-00", chavePix.getValorChave());
        assertEquals(TipoChaveEnumTest.CPF, chavePix.getTipoChave());
        assertEquals(conta, chavePix.getConta());
    }

    @Test
    void testChavePixNullValorChave() {
        ChavePix chavePix = new ChavePix();
        chavePix.setValorChave(null);

        assertThrows(NullPointerException.class, chavePix::getValorChave);
    }
}
