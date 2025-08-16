package com.sicredi.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.desafio.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findByPautaId(Long pautaId); // todas as sessões de uma pauta
    List<Sessao> findByFechadaTrue();        // só sessões fechadas
    List<Sessao> findByFechadaFalse();       // só sessões abertas
    Sessao findByIdAndFechadaFalse(Long id); // buscar sessão aberta específica
}