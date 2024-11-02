package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChavePixDTO {

    private UUID idChavePix;

    @NotNull(message = "Conta não pode ser nula.")
    private UUID contaId;

    @NotNull(message = "Tipo de chave não pode ser nulo.")
    @Size(max = 9, message = "Tipo de chave deve ter no máximo 9 caracteres.")
    private String tipoChave;

    @NotNull(message = "Valor da chave não pode ser nulo.")
    @Size(max = 77, message = "Valor da chave deve ter no máximo 77 caracteres.")
    private String valorChave;

    @NotNull(message = "Data de inclusão não pode ser nula.")
    private LocalDateTime dataHoraInclusao;

    private LocalDateTime dataHoraInativacao;
}
