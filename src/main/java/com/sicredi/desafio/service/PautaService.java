package com.sicredi.desafio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sicredi.desafio.dto.request.PautaRequestDTO;
import com.sicredi.desafio.dto.response.PautaResponseDTO;
import com.sicredi.desafio.mapper.PautaMapper;
import com.sicredi.desafio.model.Pauta;
import com.sicredi.desafio.repository.PautaRepository;

@Service
public class PautaService {
	

    @Autowired
	private PautaRepository repository;
	
	public PautaResponseDTO criarPauta(PautaRequestDTO dto){
		try {
		     Pauta pauta = PautaMapper.toEntity(dto);
	         Pauta salvo = repository.save(pauta);
	         return PautaMapper.toDTO(salvo);
		} catch (DataAccessException e) {
			throw new ServiceException("Erro ao cadastrar Pauta: " + e.getMessage(), e);
		}
    }
    public List<PautaResponseDTO> listarPautas() {
        try {
            return repository.findAll().stream()
                    .map(PautaMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao listar pautas: " + e.getMessage(), e);
        }
    }
    public PautaResponseDTO buscarPorId(Long id) {
        try {
            return repository.findById(id)
                    .map(PautaMapper::toDTO)
                    .orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada"));
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao buscar pauta por ID: " + e.getMessage(), e);
        }
    }
    public PautaResponseDTO atualizarPauta(PautaRequestDTO dto) {
        try {
        	Pauta pauta = PautaMapper.toEntity(dto);
            Pauta atualizado = repository.save(pauta);
            return PautaMapper.toDTO(atualizado);
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao atualizar pauta: " + e.getMessage(), e);
        }
    }
    public void removerPauta(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao remover pauta: " + e.getMessage(), e);
        }
    }
    public List<PautaResponseDTO> buscarPorTitulo(String titulo){
    	try {
    		return repository.findByTituloContaining(titulo).stream()
                    .map(PautaMapper::toDTO)
                    .collect(Collectors.toList());
    	} catch (DataAccessException e) {
    		throw new ServiceException("Erro ao buscar pauta por titulo: " + e.getMessage(), e);
    	}
    }
    public List<PautaResponseDTO> buscarPautasAbertas(){
    	try {
    		return repository.findByFechadaTrue().stream()
                    .map(PautaMapper::toDTO)
                    .collect(Collectors.toList());
    	} catch (DataAccessException e) {
    		throw new ServiceException("Erro ao buscar pautas em aberto: " + e.getMessage(), e);
    	}
    }
}
