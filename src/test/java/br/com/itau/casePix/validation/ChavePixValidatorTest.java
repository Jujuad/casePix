package br.com.itau.casePix.validation;

import br.com.itau.casePix.exception.ChavePixInvalidException;
import br.com.itau.casePix.model.ChavePix;
import br.com.itau.casePix.enumerators.TipoChaveEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChavePixValidatorTest {

    @Test
    @DisplayName("Deve validar chave de celular corretamente")
    void testValidarChaveCelularValida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.CELULAR);
        chavePix.setValorChave("+5511912345678"); // Exemplo de número de celular válido

        assertDoesNotThrow(() -> ChavePixValidator.validarChave(chavePix));
    }

    @Test
    @DisplayName("Deve lançar exceção para chave de celular inválida")
    void testValidarChaveCelularInvalida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.CELULAR);
        chavePix.setValorChave("11912345678"); // Chave de celular inválida

        Exception exception = assertThrows(ChavePixInvalidException.class, () -> {
            ChavePixValidator.validarChave(chavePix);
        });

        assertEquals("Chave de celular inválida. Deve iniciar com '+', seguido do código do país, DDD e número com nove dígitos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar chave de e-mail corretamente")
    void testValidarChaveEmailValida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.EMAIL);
        chavePix.setValorChave("exemplo@dominio.com");

        assertDoesNotThrow(() -> ChavePixValidator.validarChave(chavePix));
    }

    @Test
    @DisplayName("Deve lançar exceção para chave de e-mail inválida")
    void testValidarChaveEmailInvalida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.EMAIL);
        chavePix.setValorChave("exemplo.dominio.com"); // Chave de e-mail inválida

        Exception exception = assertThrows(ChavePixInvalidException.class, () -> {
            ChavePixValidator.validarChave(chavePix);
        });

        assertEquals("Chave de e-mail inválida. Deve conter '@' e ter no máximo 77 caracteres.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar chave de CPF corretamente")
    void testValidarChaveCPFValida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.CPF);
        chavePix.setValorChave("12345678901"); // Exemplo de CPF válido

        assertDoesNotThrow(() -> ChavePixValidator.validarChave(chavePix));
    }

    @Test
    @DisplayName("Deve lançar exceção para chave de CPF inválida")
    void testValidarChaveCPFInvalida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.CPF);
        chavePix.setValorChave("1234567890"); // Chave de CPF inválida

        Exception exception = assertThrows(ChavePixInvalidException.class, () -> {
            ChavePixValidator.validarChave(chavePix);
        });

        assertEquals("Chave de CPF inválida. Deve ter exatamente 11 dígitos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar chave de CNPJ corretamente")
    void testValidarChaveCNPJValida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.CNPJ);
        chavePix.setValorChave("12345678000195"); // Exemplo de CNPJ válido

        assertDoesNotThrow(() -> ChavePixValidator.validarChave(chavePix));
    }

    @Test
    @DisplayName("Deve lançar exceção para chave de CNPJ inválida")
    void testValidarChaveCNPJInvalida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.CNPJ);
        chavePix.setValorChave("1234567800019"); // Chave de CNPJ inválida

        Exception exception = assertThrows(ChavePixInvalidException.class, () -> {
            ChavePixValidator.validarChave(chavePix);
        });

        assertEquals("Chave de CNPJ inválida. Deve ter exatamente 14 dígitos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar chave aleatória corretamente")
    void testValidarChaveAleatoriaValida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.ALEATORIA);
        chavePix.setValorChave("123456789012345678901234567890123456"); // Exemplo de chave aleatória válida

        assertDoesNotThrow(() -> ChavePixValidator.validarChave(chavePix));
    }

    @Test
    @DisplayName("Deve lançar exceção para chave aleatória inválida")
    void testValidarChaveAleatoriaInvalida() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(TipoChaveEnum.ALEATORIA);
        chavePix.setValorChave("12345"); // Chave aleatória inválida

        Exception exception = assertThrows(ChavePixInvalidException.class, () -> {
            ChavePixValidator.validarChave(chavePix);
        });

        assertEquals("Chave aleatória inválida. Deve ter 36 caracteres alfanuméricos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para tipo de chave inválido")
    void testValidarChaveTipoInvalido() {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipoChave(null); // Tipo de chave inválido
        chavePix.setValorChave("qualquerValor");

        Exception exception = assertThrows(ChavePixInvalidException.class, () -> {
            ChavePixValidator.validarChave(chavePix);
        });

        assertEquals("Tipo de chave inválido.", exception.getMessage());
    }

}

