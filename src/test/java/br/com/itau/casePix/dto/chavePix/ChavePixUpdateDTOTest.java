package br.com.itau.casePix.dto.chavePix;

import br.com.itau.casePix.dto.chavePix.ChavePixUpdateDTO;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ChavePixUpdateDTOTest {

    private final Validator validator;

    public ChavePixUpdateDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidChavePixUpdateDTO() {
        ChavePixUpdateDTO dto = new ChavePixUpdateDTO();
        dto.setIdChavePix(UUID.randomUUID());
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("123.456.789-00");

        Set<ConstraintViolation<ChavePixUpdateDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Deveria ser válido");
    }

    @Test
    void testInvalidChavePixUpdateDTO_NullIdChavePix() {
        ChavePixUpdateDTO dto = new ChavePixUpdateDTO();
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("123.456.789-00");

        Set<ConstraintViolation<ChavePixUpdateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a idChavePix nulo");
    }

    @Test
    void testInvalidChavePixUpdateDTO_NullTipoChave() {
        ChavePixUpdateDTO dto = new ChavePixUpdateDTO();
        dto.setIdChavePix(UUID.randomUUID());
        dto.setContaId(UUID.randomUUID());
        dto.setValorChave("123.456.789-00");

        Set<ConstraintViolation<ChavePixUpdateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a tipoChave nulo");
    }

    @Test
    void testInvalidChavePixUpdateDTO_TooLongValorChave() {
        ChavePixUpdateDTO dto = new ChavePixUpdateDTO();
        dto.setIdChavePix(UUID.randomUUID());
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("a".repeat(78)); // 78 caracteres

        Set<ConstraintViolation<ChavePixUpdateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a valorChave excedendo o tamanho máximo");
    }
}

