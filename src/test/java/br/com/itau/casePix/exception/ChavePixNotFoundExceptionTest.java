package br.com.itau.casePix.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChavePixNotFoundExceptionTest {

    @Test
    public void testChavePixNotFoundExceptionMessage() {
        String expectedMessage = "Chave Pix não encontrada";

        ChavePixNotFoundException exception = new ChavePixNotFoundException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testChavePixNotFoundExceptionInheritance() {
        ChavePixNotFoundException exception = new ChavePixNotFoundException("message");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
