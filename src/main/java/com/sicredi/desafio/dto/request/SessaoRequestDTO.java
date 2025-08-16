package com.sicredi.desafio.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessaoRequestDTO {
    private String nome;
    private String descricao;
    private int duracao; // minutos
    private Long pautaId;
	public Long getPautaId() {
		return pautaId;
	}
	public void setPautaId(Long pautaId) {
		this.pautaId = pautaId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
}
