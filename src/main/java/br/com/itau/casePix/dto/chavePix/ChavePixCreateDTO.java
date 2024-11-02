package br.com.itau.casePix.dto.chavePix;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.UUID;

@Data
public class ChavePixCreateDTO {

    @NotNull(message = "Conta não pode ser nula.")
    private UUID contaId;

    @NotNull(message = "Tipo de chave não pode ser nulo.")
    @Size(max = 9, message = "Tipo de chave deve ter no máximo 9 caracteres.")
    @Pattern(regexp = "^(celular|email|cpf|cnpj|aleatorio)$",
            message = "Tipo de chave deve ser um dos seguintes: celular, email, cpf, cnpj, aleatorio.")
    private String tipoChave;

    @NotNull(message = "Valor da chave não pode ser nulo.")
    @Size(max = 77, message = "Valor da chave deve ter no máximo 77 caracteres.")
    private String valorChave;
}
