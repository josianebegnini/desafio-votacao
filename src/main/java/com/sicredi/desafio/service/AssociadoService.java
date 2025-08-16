package com.sicredi.desafio.service;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sicredi.desafio.dto.request.AssociadoRequestDTO;
import com.sicredi.desafio.dto.response.AssociadoResponseDTO;
import com.sicredi.desafio.model.Associado;
import com.sicredi.desafio.repository.AssociadoRepository;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository repository;

    public AssociadoResponseDTO criarAssociado(AssociadoRequestDTO dto) {
        try {
            String cpfNormalizado = Normalizer.normalize(dto.getCpf(), Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "");
            dto.setCpf(cpfNormalizado);

            if (repository.existsByCpf(dto.getCpf())) {
                throw new IllegalArgumentException("CPF já cadastrado");
            }

            Associado associado = new Associado();
            associado.setNome(dto.getNome());
            associado.setCpf(dto.getCpf());
            Associado salvo = repository.save(associado);

            return new AssociadoResponseDTO(salvo.getId(), salvo.getNome(), salvo.getCpf());
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao criar associado: " + e.getMessage(), e);
        }
    }

    public List<AssociadoResponseDTO> listarAssociados() {
        try {
            return repository.findAll().stream()
                    .map(a -> new AssociadoResponseDTO(a.getId(), a.getNome(), a.getCpf()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao listar associados: " + e.getMessage(), e);
        }
    }

    public AssociadoResponseDTO buscarPorId(Long id) {
        try {
            return repository.findById(id)
                    .map(a -> new AssociadoResponseDTO(a.getId(), a.getNome(), a.getCpf()))
                    .orElseThrow(() -> new IllegalArgumentException("Associado não encontrado"));
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao buscar associado por ID: " + e.getMessage(), e);
        }
    }

    public AssociadoResponseDTO atualizarAssociado(AssociadoResponseDTO dto) {
        try {
        	Associado associado = new Associado();
        	associado.setId(dto.getId());
        	associado.setCpf(dto.getCpf());
        	associado.setNome(dto.getNome());
            Associado atualizado = repository.save(associado);
            return new AssociadoResponseDTO(atualizado.getId(), atualizado.getNome(), atualizado.getCpf());
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao atualizar associado: " + e.getMessage(), e);
        }
    }

    public void removerAssociadoPorId(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao remover associado por ID: " + e.getMessage(), e);
        }
    }

    public AssociadoResponseDTO buscarPorCpf(String cpf) {
        try {
            Associado associado = repository.findByCpf(cpf)
                    .orElseThrow(() -> new IllegalArgumentException("Associado não encontrado"));
            return new AssociadoResponseDTO(associado.getId(), associado.getNome(), associado.getCpf());
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao buscar associado por CPF: " + e.getMessage(), e);
        }
    }
}
