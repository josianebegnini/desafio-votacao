package com.sicredi.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
	  @Autowired
	    private KafkaTemplate<String, String> kafkaTemplate;

	    public void enviaMensagem(String topico, String mensagem) {
	        this.kafkaTemplate.send(topico, mensagem);
	    }
}
