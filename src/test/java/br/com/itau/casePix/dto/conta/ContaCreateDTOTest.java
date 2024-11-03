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

    @Test
    public void testInvalidNomeCorrentistaTooLong() {
        ContaCreateDTO contaCreate = new ContaCreateDTO();
        contaCreate.setNomeCorrentista("Maria muito longa que ultrapassa os limites permitidos");
        contaCreate.setTipoConta("Conta Poupança");
        contaCreate.setNumeroAgencia(1234);
        contaCreate.setNumeroConta(56789);

        var violations = validator.validate(contaCreate);
        assertEquals(1, violations.size(), "Deve haver 1 violação de validação para nome muito longo");
    }

    @Test
    public void testInvalidTipoContaNull() {
        ContaCreateDTO contaCreate = new ContaCreateDTO();
        contaCreate.setNumeroAgencia(1234);
        contaCreate.setNumeroConta(56789);
        contaCreate.setNomeCorrentista("Maria");

        var violations = validator.validate(contaCreate);
        assertEquals(1, violations.size(), "Deve haver 1 violação de validação para tipo de conta nulo");
    }
}
