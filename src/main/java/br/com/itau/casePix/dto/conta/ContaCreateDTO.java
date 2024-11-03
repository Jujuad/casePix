package br.com.itau.casePix.dto.conta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContaCreateDTO {

    @NotNull(message = "Tipo de conta não pode ser nulo.")
    @Size(max = 20, message = "Tipo de conta deve ter no máximo 20 caracteres.")
    private String tipoConta;

    @NotNull(message = "Número da agência não pode ser nulo.")
    private Integer numeroAgencia;

    @NotNull(message = "Número da conta não pode ser nulo.")
    private Integer numeroConta;

    @NotNull(message = "Nome do titular não pode ser nulo.")
    @Size(max = 30, message = "Nome do titular deve ter no máximo 30 caracteres.")
    private String nomeCorrentista;

    @Size(max = 45, message = "Sobrenome do titular deve ter no máximo 45 caracteres.")
    private String sobrenomeCorrentista;
}

