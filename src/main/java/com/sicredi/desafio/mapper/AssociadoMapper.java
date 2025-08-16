package com.sicredi.desafio.mapper;

import com.sicredi.desafio.dto.request.AssociadoRequestDTO;
import com.sicredi.desafio.dto.response.AssociadoResponseDTO;
import com.sicredi.desafio.model.Associado;

public class AssociadoMapper {

    public static Associado toEntity(AssociadoRequestDTO dto) {
        Associado associado = new Associado();
        associado.setCpf(dto.getCpf());
        associado.setNome(dto.getNome());
        return associado;
    }

    public static AssociadoResponseDTO toDTO(Associado associado) {
        AssociadoResponseDTO dto = new AssociadoResponseDTO();
        dto.setCpf(associado.getCpf());
        dto.setNome(associado.getNome());
        return dto;
    }
}