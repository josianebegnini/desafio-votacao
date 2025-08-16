package com.sicredi.desafio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sicredi.desafio.dto.request.SessaoRequestDTO;
import com.sicredi.desafio.dto.response.SessaoResponseDTO;
import com.sicredi.desafio.mapper.SessaoMapper;
import com.sicredi.desafio.model.Pauta;
import com.sicredi.desafio.model.Sessao;
import com.sicredi.desafio.repository.PautaRepository;
import com.sicredi.desafio.repository.SessaoRepository;

@Service
public class SessaoService {
	
	@Autowired
	private SessaoRepository repository;
	@Autowired
	private PautaRepository pautaRepository;
	
    public SessaoResponseDTO criarSessao(SessaoRequestDTO dto) {
        try {
            Sessao sessao = SessaoMapper.toEntity(dto);
            Pauta pauta = pautaRepository.findById(dto.getPautaId())
                    .orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada"));
            sessao.setPauta(pauta);
            Sessao sessaoSalva = repository.save(sessao);
            return SessaoMapper.toDTO(sessaoSalva);
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao criar sessão: " + e.getMessage(), e);
        }
    }
    
    public List<SessaoResponseDTO> listarSessoes() {
        try {
            List<Sessao> lista = repository.findAll();

            return lista.stream()
                    .map(SessaoMapper::toDTO) // chama método estático
                    .toList();
            
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao listar sessões: " + e.getMessage(), e);
        }
    }
    
    public SessaoResponseDTO buscarPorId(Long id) {
        try {
            return repository.findById(id)
                    .map(SessaoMapper::toDTO)
                    .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada"));
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao buscar sessão por ID: " + e.getMessage(), e);
        }
    }
    
    public SessaoResponseDTO atualizarSessao(Sessao sessao) {
        try {
        	Sessao atualizado = repository.save(sessao);
            
            return SessaoMapper.toDTO(atualizado);
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao atualizar sessão: " + e.getMessage(), e);
        }
    }
    
    public void removerSessao(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao remover sessão: " + e.getMessage(), e);
        }
    }
    
    public List<SessaoResponseDTO> buscarAbertas() {
        try {
            return repository.findByFechadaFalse().stream()
                    .map(SessaoMapper::toDTO)
                    .toList();
        } catch (DataAccessException e) {
            throw new ServiceException("Erro ao buscar Sessoes abertas: " + e.getMessage(), e);
        }
    }
}
