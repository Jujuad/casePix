package br.com.itau.casePix.enumerators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoClienteEnumTest {

    @Test
    public void testGetTipo() {
        assertEquals("Pessoa Fisica", TipoClienteEnum.PESSOA_FISICA.getTipo());
        assertEquals("Pessoa Juridica", TipoClienteEnum.PESSOA_JURIDICA.getTipo());
    }

    @Test
    public void testToString() {
        assertEquals("Pessoa Fisica", TipoClienteEnum.PESSOA_FISICA.toString());
        assertEquals("Pessoa Juridica", TipoClienteEnum.PESSOA_JURIDICA.toString());
    }
}
