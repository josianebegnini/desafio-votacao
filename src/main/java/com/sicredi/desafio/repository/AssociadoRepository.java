package com.sicredi.desafio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sicredi.desafio.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long>{
	boolean existsByCpf(String cpf);
	Optional<Associado> findById(Long id);
	Optional<Associado> findByCpf(String cpf);
}
