package com.sicredi.desafio.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.sicredi.desafio.dto.request.VotacaoRequestDTO;
import com.sicredi.desafio.dto.response.AssociadoResponseDTO;
import com.sicredi.desafio.dto.response.SessaoResponseDTO;
import com.sicredi.desafio.exceptions.SessaoException;
import com.sicredi.desafio.exceptions.VotacaoException;
import com.sicredi.desafio.mapper.AssociadoMapper;
import com.sicredi.desafio.mapper.SessaoMapper;
import com.sicredi.desafio.model.Associado;
import com.sicredi.desafio.model.Sessao;
import com.sicredi.desafio.model.Votacao;
import com.sicredi.desafio.repository.AssociadoRepository;
import com.sicredi.desafio.repository.SessaoRepository;
import com.sicredi.desafio.repository.VotacaoRepository;

@Service
public class VotacaoService {

    private VotacaoRepository repository;

    private SessaoRepository sessaoRepository;

    private AssociadoRepository associadoRepository;
    
	private AssociadoExternoService associadoExternoService;
    
    
    private static final Logger logger = LogManager.getLogger(VotacaoService.class);
	
    public VotacaoService(VotacaoRepository repository, SessaoRepository sessaoRepository, AssociadoRepository associadoRepository,
            AssociadoExternoService associadoExternoService) {
    	this.repository = repository;
    	this.sessaoRepository = sessaoRepository;
    	this.associadoRepository = associadoRepository;
    	this.associadoExternoService = associadoExternoService;
}
    
    public void validaAssociadoJaVotou(Sessao sessao, Associado associado) {
        Optional<Votacao> votacao = repository.findBySessaoIdAndAssociadoCpf(sessao, associado);

        if (votacao.isPresent()) {
            logger.info("Associado já votou na sessão: " + associado.getCpf());
            throw new VotacaoException("Associado já votou na sessão");
        }
    }
    
    public void votar(VotacaoRequestDTO votacaoDto) {
        try {
        	Sessao sessao = sessaoRepository.findById(votacaoDto.getSessaoId())
 		            .orElseThrow(() -> new SessaoException("Sessão não encontrada com ID: " + votacaoDto.getSessaoId()));
 		    SessaoResponseDTO sessaoDto = SessaoMapper.toDTO(sessao);
        	
            validaVotacaoEncerrada(sessaoDto);

            Associado associado = associadoRepository.findByCpf(votacaoDto.getCpfAssociado())
                    .orElseThrow(() -> new VotacaoException("Associado não cadastrado"));
            AssociadoResponseDTO associadoDto = AssociadoMapper.toDTO(associado);

            chamaServicoExternoUsuarioPossuiPermissaoVotar(associadoDto);

            if (!tempoDaSessaoExpirou(sessaoDto)) {
            	
                validaAssociadoJaVotou(sessao, associado);

                Votacao votacao = new Votacao();
                votacao.setSessao(sessao);
                votacao.setAssociado(associado);
                votacao.setVoto(votacaoDto.getVoto());
                votacao.setDtVoto(LocalDateTime.now());

                repository.save(votacao);
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erro ao votar: " + e.getMessage(), e);
        }
    }

	private void chamaServicoExternoUsuarioPossuiPermissaoVotar(AssociadoResponseDTO associadoDto) {
		try {
			boolean resultado = associadoExternoService.chamaServicoExternoAssociadoPossuiPermissaoParaVotar(associadoDto);
			if(!resultado) {
				logger.info("Associado nao possui permissão para votar");
				throw new VotacaoException("Associado nao possui permissão para votar");
			}
		} catch (Exception e) {
			logger.info("Não foi possível validar associado no serviço externo, serviço pode estar fora.");
			
		}
	}

	private void validaVotacaoEncerrada(SessaoResponseDTO sessaoDto) {
		if(sessaoDto != null && sessaoDto.isFechada()) {
			logger.info("A votação está encerrada.");
			throw new VotacaoException("A votação está encerrada.");
		}
	}

	private boolean tempoDaSessaoExpirou(SessaoResponseDTO sessaoDto) {
		LocalDateTime dataVotacao = sessaoDto.getDtSessao();
		long tempoLimiteMinutos = sessaoDto.getDuracao();
		LocalDateTime agora = LocalDateTime.now();
		long diferencaMinutos = Duration.between(agora, dataVotacao).toMinutes();
		if (diferencaMinutos > tempoLimiteMinutos) {
			encerrarSessao(sessaoDto);
			logger.info("Sessão expirou e foi encerrada");
			return true;
		}
		return false;
	}
	
	public void encerrarSessao(SessaoResponseDTO sessaoVotacaoDto) {
	    Sessao sessao = sessaoRepository.findById(sessaoVotacaoDto.getIdSessao())
	            .orElseThrow(() -> new SessaoException("Sessão não encontrada"));

	    sessao.setFechada(true);
	    sessao.setDtEncerramento(LocalDateTime.now());

	    sessaoRepository.save(sessao);
	}
	
	public List<Votacao> listarVotacoes(){
		try {
			return repository.findAll();
		} catch (DataAccessException e) {
			throw new ServiceException("Erro ao listar votacoes: " + e.getMessage(), e);
		}
    }
    public Optional<Votacao> buscarPorId(Long id){
    	try {
    		return repository.findById(id);
    	} catch (DataAccessException e) {
    		throw new ServiceException("Erro ao buscar votacao por ID: " + e.getMessage(), e);
    	}
    }
    public void atualizarVotacao(Votacao votacao){
    	try {
    		repository.save(votacao);
    	} catch (DataAccessException e) {
    		throw new ServiceException("Erro ao atualizar votacao: " + e.getMessage(), e);
    	}
    }
    public void removerVotacaoPorId(Long id){
    	try {
    		repository.deleteById(id);
    	} catch (DataAccessException e) {
    		throw new ServiceException("Erro ao remover votacao: " + e.getMessage(), e);
    	}
    }
}
