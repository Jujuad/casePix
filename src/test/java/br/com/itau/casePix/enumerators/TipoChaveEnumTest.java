package br.com.itau.casePix.enumerators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoChaveEnumTest {

    @Test
    public void testGetTipo() {
        assertEquals("celular", TipoChaveEnum.CELULAR.getTipo());
        assertEquals("email", TipoChaveEnum.EMAIL.getTipo());
        assertEquals("cpf", TipoChaveEnum.CPF.getTipo());
        assertEquals("cnpj", TipoChaveEnum.CNPJ.getTipo());
        assertEquals("aleatorio", TipoChaveEnum.ALEATORIA.getTipo());
    }

    @Test
    public void testToString() {
        assertEquals("celular", TipoChaveEnum.CELULAR.toString());
        assertEquals("email", TipoChaveEnum.EMAIL.toString());
        assertEquals("cpf", TipoChaveEnum.CPF.toString());
        assertEquals("cnpj", TipoChaveEnum.CNPJ.toString());
        assertEquals("aleatorio", TipoChaveEnum.ALEATORIA.toString());
    }
}

