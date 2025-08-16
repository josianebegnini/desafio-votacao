package com.sicredi.desafio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.desafio.model.Associado;
import com.sicredi.desafio.model.Sessao;
import com.sicredi.desafio.model.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
    List<Votacao> findBySessaoId(Long sessaoId);               // todos os votos de uma sessão
    List<Votacao> findByAssociadoCpf(String cpf);             // todos os votos de um associado
    Optional<Votacao> findBySessaoIdAndAssociadoCpf(Sessao sessao, Associado associado); // voto específico
}