package br.com.itau.casePix.dto.conta;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContaDTOTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidContaDTO() {
        ContaDTO conta = new ContaDTO();
        conta.setTipoConta("Conta Corrente");
        conta.setNumeroAgencia(1234);
        conta.setNumeroConta(56789);
        conta.setNomeCorrentista("Carlos");
        conta.setSobrenomeCorrentista("Santos");
        conta.setDataHoraInclusao(LocalDateTime.now());
        conta.setChavesPix(Collections.emptyList());

        var violations = validator.validate(conta);
        assertTrue(violations.isEmpty(), "Não deve haver violações para DTO válido");
    }

    @Test
    public void testInvalidContaDTO() {
        ContaDTO conta = new ContaDTO();

        var violations = validator.validate(conta);

        assertEquals(6, violations.size(), "Deve haver 6 violações de validação");

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Tipo de conta não pode ser nulo.")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Número da agência não pode ser nulo.")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Número da conta não pode ser nulo.")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Nome do titular não pode ser nulo.")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Sobrenome do titular não pode ser nulo.")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Data de inclusão não pode ser nula.")));
    }
}
