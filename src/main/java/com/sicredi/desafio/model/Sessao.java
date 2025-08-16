package com.sicredi.desafio.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sessao")
    private Long idSessao;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dtSessao;

    @Column(name = "data_fim")
    private LocalDateTime dtEncerramento;

    @Column(nullable = false)
    private int duracao; // minutos

    @Column(nullable = false)
    private boolean fechada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @PrePersist
    public void prePersist() {
        if (this.dtSessao == null) {
            this.dtSessao = LocalDateTime.now();
        }
        if (this.dtEncerramento == null && duracao > 0) {
            this.dtEncerramento = this.dtSessao.plusMinutes(duracao);
        }
        this.fechada = false;
    }

	public Long getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(Long idSessao) {
		this.idSessao = idSessao;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public boolean isFechada() {
		return fechada;
	}

	public void setFechada(boolean fechada) {
		this.fechada = fechada;
	}

	public LocalDateTime getDtEncerramento() {
		return dtEncerramento;
	}

	public void setDtEncerramento(LocalDateTime dtEncerramento) {
		this.dtEncerramento = dtEncerramento;
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

	public LocalDateTime getDtSessao() {
		return dtSessao;
	}

	public void setDtSessao(LocalDateTime dtSessao) {
		this.dtSessao = dtSessao;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
}
