package com.sicredi.desafio.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.desafio.dto.response.ResultadoVotacaoDTO;

@Service
public class KafkaConsumerService {
	
	  private static final Logger logger = LogManager.getLogger(KafkaConsumerService.class);
		

	private final ObjectMapper objectMapper;
	public KafkaConsumerService() {
		this.objectMapper = new ObjectMapper();
	}
	
    @KafkaListener(topics = "resultado-votacao", groupId = "grupo-votacao")
    public void consomeTopicoResultadoVotacao(String mensagem) {
    	try {
    		ResultadoVotacaoDTO resultado = objectMapper.readValue(mensagem, ResultadoVotacaoDTO.class);
    		System.out.println("Mensagem recebida do tópico resultado_votacao: SessaoId: " + resultado.getSessaoId());
    		System.out.println("Mensagem recebida do tópico resultado_votacao: " + mensagem);
		} catch (Exception e) {
			logger.error("Não foi possivel converter mensagem para ResultadoVotacao. " + e.getMessage());
		}
    }
}
