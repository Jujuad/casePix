package br.com.itau.casePix.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import br.com.itau.casePix.enumerators.TipoClienteEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CONTAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "conta_id", nullable = false, updatable = false)
    private UUID idConta;

    @Column(name = "tipo_conta", nullable = false, length = 20)
    private String tipoConta;

    @Column(name = "numero_agencia", nullable = false, length = 4)
    private int numeroAgencia;

    @Column(name = "numero_conta", nullable = false, length = 8)
    private int numeroConta;

    @Column(name = "nome_correntista", nullable = false, length = 30)
    private String nomeCorrentista;

    @Column(name = "sobrenome_correntista", length = 45)
    private String sobrenomeCorrentista;

    @Column(name = "data_hora_inclusao", nullable = false)
    private LocalDateTime dataHoraInclusao;

    @Column(name = "tipo_cliente", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoClienteEnum tipoCliente;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChavePix> chavesPix;
}
