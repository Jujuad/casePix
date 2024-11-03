package br.com.itau.casePix.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleChavePixNotFound() {
        ChavePixNotFoundException exception = new ChavePixNotFoundException("Chave Pix não encontrada");

        ResponseEntity<String> response = globalExceptionHandler.handleChavePixNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Chave Pix não encontrada", response.getBody());
    }

    @Test
    public void testHandleChavePixInvalid() {
        ChavePixInvalidException exception = new ChavePixInvalidException("Chave Pix inválida");

        ResponseEntity<String> response = globalExceptionHandler.handleChavePixInvalid(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Chave Pix inválida", response.getBody());
    }

    @Test
    public void testHandleGenericException() {
        Exception exception = new Exception("Erro genérico");

        ResponseEntity<String> response = globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno do servidor: Erro genérico", response.getBody());
    }
}
