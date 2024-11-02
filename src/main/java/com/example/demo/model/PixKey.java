package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PIX_KEYS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PixKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pix_id", nullable = false, updatable = false)
    private UUID pixId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "key_type", nullable = false, length = 9)
    private String keyType;

    @Column(name = "key_value", nullable = false, unique = true, length = 77)
    private String keyValue;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;
}
