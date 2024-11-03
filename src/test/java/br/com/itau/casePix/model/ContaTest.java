package br.com.itau.casePix.model;

import br.com.itau.casePix.enumerators.TipoClienteEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    @Test
    void testContaCreation() {
        Conta conta = Conta.builder()
                .tipoConta("Corrente")
                .numeroAgencia(1234)
                .numeroConta(567890)
                .nomeCorrentista("Maria")
                .sobrenomeCorrentista("Oliveira")
                .dataHoraInclusao(LocalDateTime.now())
                .tipoCliente(TipoClienteEnum.PESSOA_JURIDICA)
                .build();

        assertNotNull(conta);
        assertNull(conta.getIdConta());
        assertEquals("Maria", conta.getNomeCorrentista());
        assertEquals(TipoClienteEnum.PESSOA_JURIDICA, conta.getTipoCliente());
    }

    @Test
    void testContaNullNomeCorrentista() {
        Conta conta = new Conta();
        conta.setNomeCorrentista(null);
        assertNull(conta.getNomeCorrentista());
    }
}
