package com.sicredi.desafio.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import com.sicredi.desafio.dto.request.AssociadoRequestDTO;
import com.sicredi.desafio.dto.response.AssociadoResponseDTO;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotacaoResponseDTO {
    private Long idVotacao;
    private LocalDateTime dtVoto;
    private String voto;
    private SessaoResponseDTO sessaoDto;
    private AssociadoResponseDTO associadoDto;
	public SessaoResponseDTO getSessaoDto() {
		return sessaoDto;
	}
	public void setSessaoDto(SessaoResponseDTO sessaoDto) {
		this.sessaoDto = sessaoDto;
	}
	public AssociadoResponseDTO getAssociadoDto() {
		return associadoDto;
	}
	public void setAssociadoDto(AssociadoResponseDTO associadoDto) {
		this.associadoDto = associadoDto;
	}
	public Long getIdVotacao() {
		return idVotacao;
	}
	public void setIdVotacao(Long idVotacao) {
		this.idVotacao = idVotacao;
	}
	public LocalDateTime getDtVoto() {
		return dtVoto;
	}
	public void setDtVoto(LocalDateTime dtVoto) {
		this.dtVoto = dtVoto;
	}
	public String getVoto() {
		return voto;
	}
	public void setVoto(String voto) {
		this.voto = voto;
	}
}
