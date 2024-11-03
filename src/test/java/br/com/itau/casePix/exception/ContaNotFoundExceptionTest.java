package br.com.itau.casePix.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContaNotFoundExceptionTest {

    @Test
    public void testContaNotFoundExceptionMessage() {
        String expectedMessage = "Conta não encontrada";

        ContaNotFoundException exception = new ContaNotFoundException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testContaNotFoundExceptionMessageWithCause() {
        String expectedMessage = "Conta não encontrada";
        Throwable cause = new Throwable("Causa do erro");

        ContaNotFoundException exception = new ContaNotFoundException(expectedMessage, cause);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testContaNotFoundExceptionInheritance() {
        ContaNotFoundException exception = new ContaNotFoundException("message");

        assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
    }
}
