package com.sicredi.desafio.mapper;

import com.sicredi.desafio.dto.request.PautaRequestDTO;
import com.sicredi.desafio.dto.response.PautaResponseDTO;
import com.sicredi.desafio.model.Pauta;

public class PautaMapper {

    public static Pauta toEntity(PautaRequestDTO dto) {
        Pauta pauta = new Pauta();
        pauta.setTitulo(dto.getTitulo());
        pauta.setDescricao(dto.getDescricao());
        pauta.setFechada(dto.isFechada());
        return pauta;
    }

    public static PautaResponseDTO toDTO(Pauta pauta) {
        PautaResponseDTO dto = new PautaResponseDTO();
        dto.setId(pauta.getId());
        dto.setTitulo(pauta.getTitulo());
        dto.setDescricao(pauta.getDescricao());
        dto.setFechada(pauta.isFechada());
        return dto;
    }
}
