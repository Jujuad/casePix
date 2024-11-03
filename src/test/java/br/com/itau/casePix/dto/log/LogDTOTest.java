package br.com.itau.casePix.dto.log;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LogDTOTest {

    @Test
    public void testLogErro() {
        String mensagem = "Erro ao processar a requisição";
        String classe = "br.com.itau.casePix.service.ExemploService";
        String metodo = "processarRequisicao";
        String detalhes = "Erro de validação de dados";

        LogDTO log = LogDTO.logErro(mensagem, classe, metodo, detalhes);

        assertNotNull(log);
        assertEquals("ERROR", log.getNivel());
        assertEquals(mensagem, log.getMensagem());
        assertNotNull(log.getDataHora());
        assertEquals(classe, log.getClasse());
        assertEquals(metodo, log.getMetodo());
        assertEquals(detalhes, log.getDetalhes());
    }

    @Test
    public void testLogInfo() {
        String mensagem = "Requisição processada com sucesso";
        String classe = "br.com.itau.casePix.service.ExemploService";
        String metodo = "processarRequisicao";

        LogDTO log = LogDTO.logInfo(mensagem, classe, metodo);

        assertNotNull(log);
        assertEquals("INFO", log.getNivel());
        assertEquals(mensagem, log.getMensagem());
        assertNotNull(log.getDataHora());
        assertEquals(classe, log.getClasse());
        assertEquals(metodo, log.getMetodo());
        assertEquals(null, log.getDetalhes());
    }

    @Test
    public void testBuilder() {
        LogDTO log = LogDTO.builder()
                .nivel("DEBUG")
                .mensagem("Mensagem de depuração")
                .dataHora(LocalDateTime.now())
                .classe("br.com.itau.casePix.service.ExemploService")
                .metodo("metodoDebug")
                .detalhes("Detalhes adicionais")
                .build();

        assertNotNull(log);
        assertEquals("DEBUG", log.getNivel());
        assertEquals("Mensagem de depuração", log.getMensagem());
        assertNotNull(log.getDataHora());
        assertEquals("br.com.itau.casePix.service.ExemploService", log.getClasse());
        assertEquals("metodoDebug", log.getMetodo());
        assertEquals("Detalhes adicionais", log.getDetalhes());
    }
}

