package com.sicredi.desafio.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessaoResponseDTO {
    private Long idSessao;
    private String nome;
    private String descricao;
    private LocalDateTime dtSessao;
    private LocalDateTime dtEncerramento;
    private int duracao;
    private boolean fechada;
    private PautaResponseDTO pauta; 
	public boolean isFechada() {
		return fechada;
	}
	public void setFechada(boolean fechada) {
		this.fechada = fechada;
	}
	public LocalDateTime getDtSessao() {
		return dtSessao;
	}
	public void setDtSessao(LocalDateTime dtSessao) {
		this.dtSessao = dtSessao;
	}
	public LocalDateTime getDtEncerramento() {
		return dtEncerramento;
	}
	public void setDtEncerramento(LocalDateTime dtEncerramento) {
		this.dtEncerramento = dtEncerramento;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	public Long getIdSessao() {
		return idSessao;
	}
	public void setIdSessao(Long idSessao) {
		this.idSessao = idSessao;
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
	public PautaResponseDTO getPauta() {
		return pauta;
	}
	public void setPauta(PautaResponseDTO pauta) {
		this.pauta = pauta;
	}
}
