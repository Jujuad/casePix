package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ContaComChavesDTO {

    private UUID idConta;

    @NotNull(message = "Tipo de conta não pode ser nulo.")
    @Size(max = 20, message = "Tipo de conta deve ter no máximo 20 caracteres.")
    private String tipoConta;

    @NotNull(message = "Número da agência não pode ser nulo.")
    private int numeroAgencia;

    @NotNull(message = "Número da conta não pode ser nulo.")
    private int numeroConta;

    @NotNull(message = "Nome do titular não pode ser nulo.")
    @Size(max = 30, message = "Nome do titular deve ter no máximo 30 caracteres.")
    private String nomeTitular;

    @Size(max = 45, message = "Sobrenome do titular deve ter no máximo 45 caracteres.")
    private String sobrenomeTitular;

    @NotNull(message = "Data de inclusão não pode ser nula.")
    private LocalDateTime dataHoraInclusao;

    private List<ChavePixDTO> chavesPix;
}
