package br.com.itau.casePix.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import br.com.itau.casePix.enumerators.TipoChaveEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "CHAVES_PIX")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChavePix {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "pix_id", nullable = false, updatable = false)
    private UUID idChavePix;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Column(name = "tipo_chave", nullable = false, length = 9)
    @Enumerated(EnumType.STRING)
    private TipoChaveEnum tipoChave;

    @Column(name = "valor_chave", nullable = false, unique = true, length = 77)
    private String valorChave;

    @Column(name = "data_hora_inclusao", nullable = false)
    private LocalDateTime dataHoraInclusao;

    @Column(name = "data_hora_inativacao")
    private LocalDateTime dataHoraInativacao;
}
