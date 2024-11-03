package br.com.itau.casePix.dto.chavePix;

import br.com.itau.casePix.dto.chavePix.ChavePixDTO;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ChavePixDTOTest {

    private final Validator validator;

    public ChavePixDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidChavePixDTO() {
        ChavePixDTO dto = new ChavePixDTO();
        dto.setIdChavePix(UUID.randomUUID());
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("123.456.789-00");
        dto.setDataHoraInclusao(LocalDateTime.now());

        Set<ConstraintViolation<ChavePixDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Deveria ser válido");
    }

    @Test
    void testInvalidChavePixDTO_NullContaId() {
        ChavePixDTO dto = new ChavePixDTO();
        dto.setIdChavePix(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("123.456.789-00");
        dto.setDataHoraInclusao(LocalDateTime.now());

        Set<ConstraintViolation<ChavePixDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a contaId nulo");
    }

    @Test
    void testInvalidChavePixDTO_NullDataHoraInclusao() {
        ChavePixDTO dto = new ChavePixDTO();
        dto.setIdChavePix(UUID.randomUUID());
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("123.456.789-00");

        Set<ConstraintViolation<ChavePixDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a dataHoraInclusao nula");
    }

    @Test
    void testInvalidChavePixDTO_TooLongValorChave() {
        ChavePixDTO dto = new ChavePixDTO();
        dto.setIdChavePix(UUID.randomUUID());
        dto.setContaId(UUID.randomUUID());
        dto.setTipoChave("cpf");
        dto.setValorChave("a".repeat(78)); // 78 caracteres
        dto.setDataHoraInclusao(LocalDateTime.now());

        Set<ConstraintViolation<ChavePixDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deveria ser inválido devido a valorChave excedendo o tamanho máximo");
    }
}
