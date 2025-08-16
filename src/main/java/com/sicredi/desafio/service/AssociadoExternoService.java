package com.sicredi.desafio.service;

import java.text.Normalizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.sicredi.desafio.dto.response.AssociadoResponseDTO;
import com.sicredi.desafio.exceptions.VotacaoException;

@Service
public class AssociadoExternoService {
    private static final String SERVICO_EXTERNO_URL = "https://user-info.herokuapp.com/users/";
    private static final Logger logger = LogManager.getLogger(AssociadoExternoService.class);

    public boolean chamaServicoExternoAssociadoPossuiPermissaoParaVotar(AssociadoResponseDTO associadoDto) {
        if (associadoDto == null || associadoDto.getCpf() == null) {
            logger.error("CPF não informado na votação");
            throw new VotacaoException("CPF inválido");
        }

        String cpfNormalizado = normalizarCpf(associadoDto.getCpf());
        String url = SERVICO_EXTERNO_URL + cpfNormalizado;

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            String body = response.getBody();
            return "ABLE_TO_VOTE".equals(body);
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.info("Serviço externo retornou CPF inválido: " + cpfNormalizado);
                throw new VotacaoException("CPF inválido!");
            }
            logger.error("Erro ao chamar o serviço externo: " + ex.getMessage(), ex);
            throw new VotacaoException("Erro ao chamar o serviço externo");
        } catch (Exception e) {
            logger.error("Erro ao chamar o serviço de validação de associado: " + e.getMessage(), e);
            throw new VotacaoException("Erro ao chamar o serviço de validação de associado");
        }
    }

    private String normalizarCpf(String cpf) {
        return Normalizer.normalize(cpf, Normalizer.Form.NFD)
                         .replaceAll("\\p{M}", "")
                         .replaceAll("\\D", ""); // remove tudo que não for número
    }
}
