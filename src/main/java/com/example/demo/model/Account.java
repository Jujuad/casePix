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
@Table(name = "ACCOUNTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false, updatable = false)
    private UUID accountId;

    @Column(name = "account_type", nullable = false, length = 20)
    private String accountType;

    @Column(name = "agency_number", nullable = false, length = 4)
    private int agencyNumber;

    @Column(name = "account_number", nullable = false, length = 8)
    private int accountNumber;

    @Column(name = "account_holder", nullable = false, length = 30)
    private String accountHolder;

    @Column(name = "account_holder_last_name", length = 45)
    private String accountHolderLastName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PixKey> pixKeys;
}
