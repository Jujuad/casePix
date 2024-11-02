package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

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

    @Column(name = "nome_titular", nullable = false, length = 30)
    private String nomeTitular;

    @Column(name = "sobrenome_titular", length = 45)
    private String sobrenomeTitular;

    @Column(name = "data_hora_inclusao", nullable = false)
    private LocalDateTime dataHoraInclusao;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChavePix> chavesPix;
}
