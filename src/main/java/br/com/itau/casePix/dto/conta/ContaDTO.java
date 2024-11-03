package br.com.itau.casePix.dto.conta;

import br.com.itau.casePix.dto.chavePix.ChavePixDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ContaDTO {

    private UUID idConta;

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
    @NotNull(message = "Sobrenome do titular não pode ser nulo.")
    private String sobrenomeCorrentista;

    @NotNull(message = "Data de inclusão não pode ser nula.")
    private LocalDateTime dataHoraInclusao;

    private List<ChavePixDTO> chavesPix;
}

