package com.sicredi.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.desafio.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    List<Pauta> findByTituloContaining(String titulo); // busca parcial
    List<Pauta> findByFechadaTrue();   // só fechadas
    List<Pauta> findByFechadaFalse();  // só abertas
}
