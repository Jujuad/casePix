package br.com.itau.casePix.enumerators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoContaEnumTest {

    @Test
    public void testGetTipo() {
        assertEquals("poupanca", TipoContaEnum.POUPANCA.getTipo());
        assertEquals("corrente", TipoContaEnum.CORRENTE.getTipo());
    }

    @Test
    public void testToString() {
        assertEquals("poupanca", TipoContaEnum.POUPANCA.toString());
        assertEquals("corrente", TipoContaEnum.CORRENTE.toString());
    }
}
