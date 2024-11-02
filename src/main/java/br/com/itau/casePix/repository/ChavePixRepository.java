package br.com.itau.casePix.repository;

import br.com.itau.casePix.model.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChavePixRepository extends JpaRepository<ChavePix, UUID> {
    Optional<ChavePix> findByValorChave(String valorChave);
}
