package br.com.itau.casePix.dto.chavePix;

import br.com.itau.casePix.dto.chavePix.ChavePixCreateDTO;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ChavePixCreateDTOTest {

    private final Validator validator;

    public ChavePixCreateDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidChavePixCreateDTO() {
        ChavePixCreateDTO dto = new ChavePixCreateDTO();
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("123.456.789-00");

        Set<ConstraintViolation<ChavePixCreateDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Deveria ser válido");
    }

    @Test
    void testInvalidChavePixCreateDTO_NullContaId() {
        ChavePixCreateDTO dto = new ChavePixCreateDTO();
        dto.setTipoChave("cpf");
        dto.setValorChave("123.456.789-00");

        Set<ConstraintViolation<ChavePixCreateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a contaId nulo");
    }

    @Test
    void testInvalidChavePixCreateDTO_InvalidTipoChave() {
        ChavePixCreateDTO dto = new ChavePixCreateDTO();
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("invalido");
        dto.setValorChave("123.456.789-00");

        Set<ConstraintViolation<ChavePixCreateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a tipoChave inválido");
    }

    @Test
    void testInvalidChavePixCreateDTO_TooLongValorChave() {
        ChavePixCreateDTO dto = new ChavePixCreateDTO();
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("a".repeat(78)); // 78 caracteres

        Set<ConstraintViolation<ChavePixCreateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a valorChave excedendo o tamanho máximo");
    }
}
