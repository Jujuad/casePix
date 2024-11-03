package br.com.itau.casePix.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChavePixInvalidExceptionTest {

    @Test
    public void testChavePixInvalidExceptionMessage() {
        String expectedMessage = "Chave Pix inválida";

        ChavePixInvalidException exception = new ChavePixInvalidException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testChavePixInvalidExceptionInheritance() {

        ChavePixInvalidException exception = new ChavePixInvalidException("message");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
