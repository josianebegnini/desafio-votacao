package com.sicredi.desafio.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotacaoRequestDTO {
    private String cpfAssociado;
    private Long sessaoId;
    private String voto; // "SIM" ou "NAO"
	public Long getSessaoId() {
		return sessaoId;
	}
	public void setSessaoId(Long sessaoId) {
		this.sessaoId = sessaoId;
	}
	public String getCpfAssociado() {
		return cpfAssociado;
	}
	public void setCpfAssociado(String cpfAssociado) {
		this.cpfAssociado = cpfAssociado;
	}
	public String getVoto() {
		return voto;
	}
	public void setVoto(String voto) {
		this.voto = voto;
	}
}
