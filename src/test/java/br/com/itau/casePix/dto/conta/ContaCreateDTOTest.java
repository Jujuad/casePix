package br.com.itau.casePix.dto.conta;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContaCreateDTOTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidContaCreateDTO() {
        ContaCreateDTO contaCreate = new ContaCreateDTO();
        contaCreate.setTipoConta("Conta Poupança");
        contaCreate.setNumeroAgencia(1234);
        contaCreate.setNumeroConta(56789);
        contaCreate.setNomeCorrentista("Maria");
        contaCreate.setSobrenomeCorrentista("Oliveira");

        var violations = validator.validate(contaCreate);
        assertTrue(violations.isEmpty(), "Não deve haver violações para DTO válido");
    }

    @Test
    public void testInvalidContaCreateDTO() {
        ContaCreateDTO contaCreate = new ContaCreateDTO();

        var violations = validator.validate(contaCreate);
        assertEquals(4, violations.size(), "Deve haver 4 violações de validação");
    }
}

