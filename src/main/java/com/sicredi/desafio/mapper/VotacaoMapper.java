package com.sicredi.desafio.mapper;

import com.sicredi.desafio.dto.request.VotacaoRequestDTO;
import com.sicredi.desafio.dto.response.VotacaoResponseDTO;
import com.sicredi.desafio.model.Votacao;

public class VotacaoMapper {

    public static Votacao toEntity(VotacaoRequestDTO dto) {
        Votacao votacao = new Votacao();
        votacao.setVoto(dto.getVoto());
        return votacao;
    }

    public static VotacaoResponseDTO toDTO(Votacao votacao) {
        VotacaoResponseDTO dto = new VotacaoResponseDTO();
        dto.setIdVotacao(votacao.getId());
        dto.setDtVoto(votacao.getDtVoto());
        dto.setVoto(votacao.getVoto());
        dto.setSessaoDto(SessaoMapper.toDTO(votacao.getSessao()));
        dto.setAssociadoDto(AssociadoMapper.toDTO(votacao.getAssociado()));
        return dto;
    }
}