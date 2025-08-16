package com.sicredi.desafio.mapper;

import com.sicredi.desafio.dto.request.SessaoRequestDTO;
import com.sicredi.desafio.dto.response.SessaoResponseDTO;
import com.sicredi.desafio.model.Sessao;

public class SessaoMapper {

    public static Sessao toEntity(SessaoRequestDTO dto) {
        Sessao sessao = new Sessao();
        sessao.setNome(dto.getNome());
        sessao.setDescricao(dto.getDescricao());
        sessao.setDuracao(dto.getDuracao());
        return sessao;
    }

    public static SessaoResponseDTO toDTO(Sessao sessao) {
        SessaoResponseDTO dto = new SessaoResponseDTO();
        dto.setIdSessao(sessao.getIdSessao());
        dto.setNome(sessao.getNome());
        dto.setDescricao(sessao.getDescricao());
        dto.setDtSessao(sessao.getDtSessao());
        dto.setDtEncerramento(sessao.getDtEncerramento());
        dto.setDuracao(sessao.getDuracao());
        dto.setFechada(sessao.isFechada());
        dto.setPauta(PautaMapper.toDTO(sessao.getPauta()));
        return dto;
    }
}
