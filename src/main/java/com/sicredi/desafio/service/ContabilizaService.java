package com.sicredi.desafio.service;

import java.text.Normalizer;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sicredi.desafio.dto.response.ResultadoVotacaoDTO;
import com.sicredi.desafio.dto.response.SessaoResponseDTO;
import com.sicredi.desafio.exceptions.SessaoException;
import com.sicredi.desafio.mapper.SessaoMapper;
import com.sicredi.desafio.model.Sessao;
import com.sicredi.desafio.model.Votacao;
import com.sicredi.desafio.repository.SessaoRepository;
import com.sicredi.desafio.repository.VotacaoRepository;

@Service
public class ContabilizaService {
	private VotacaoRepository votacaoRepo;
	private KafkaProducerService produce;
	private KafkaConsumerService consumer;
	private VotacaoService votacaoService;
	private SessaoRepository sessaoRepository;

	private static final Logger logger = LogManager.getLogger(ContabilizaService.class);
	
	 public ContabilizaService(VotacaoRepository votacaoRepo, VotacaoService votacaoService, KafkaProducerService kafkaService, SessaoRepository sessaoRepository) {
	        this.votacaoRepo = votacaoRepo;
	        this.produce = kafkaService;
	        this.votacaoService = votacaoService;
	        this.sessaoRepository = sessaoRepository;
	    }

	public String contabilizar(Long idSessao) throws Exception {

		String retorno = null;
		try {
			
			Sessao sessao = sessaoRepository.findById(idSessao)
 		            .orElseThrow(() -> new SessaoException("Sessão não encontrada com ID: " + idSessao));
 		    SessaoResponseDTO sessaoDto = SessaoMapper.toDTO(sessao);
 		    
			encerraSessaoParaContagemDeVotos(sessaoDto);

			ResultadoVotacaoDTO resultado = contabilizaVotosDaSessao(sessaoDto);
			
			retorno = publicaNoTopicoResultadoVotacao(resultado);

			return retorno;
		} catch (DataAccessException de) {
			logger.error("Erro ao contabilizar os votos: " + de.getMessage(), de);
			throw new ServiceException("Erro ao contabilizar os votos: " + de.getMessage(), de);
		}catch (Exception e) {
			logger.error("Erro ao contabilizar os votos: " + e.getMessage(), e);
		}
		return retorno;
	}

	private String publicaNoTopicoResultadoVotacao(ResultadoVotacaoDTO resultado) {
		String mensagem = montaMensagem(resultado);
		produce.enviaMensagem("resultado-votacao", mensagem);
		return mensagem;
	}
	
	private ResultadoVotacaoDTO contabilizaVotosDaSessao(SessaoResponseDTO sessaoDto) {
		ResultadoVotacaoDTO resultado = new ResultadoVotacaoDTO();
		List<Votacao> votos = votacaoRepo.findBySessaoId(sessaoDto.getIdSessao());
		int contagemVotosSim = 0;
		int contagemVotosNao = 0;

		for (Votacao votacao : votos) {
			String votoNormalizado = Normalizer.normalize(votacao.getVoto(), Normalizer.Form.NFD)
					.replaceAll("\\p{M}", "");
			if ("s".equalsIgnoreCase(votacao.getVoto())) {
				contagemVotosSim++;
			}
			if ("n".equalsIgnoreCase(votoNormalizado)) {
				contagemVotosNao++;
			}
		}
		resultado.setTotalNegativo(contagemVotosNao);
		resultado.setTotalPositivo(contagemVotosSim);
		resultado.setSessaoId(sessaoDto.getIdSessao());
		return resultado;
	}
	private String montaMensagem(ResultadoVotacaoDTO resultado) {
		Gson gson = new Gson();
		String json = gson.toJson(resultado);
		logger.info("Mensagem JSON gerada: {}", json);
		return json;
	}


	private void encerraSessaoParaContagemDeVotos(SessaoResponseDTO sessaoDto) {
		votacaoService.encerrarSessao(sessaoDto);
		logger.info("Sessão de votação encerrada para contagem de votos");
	}	
}
