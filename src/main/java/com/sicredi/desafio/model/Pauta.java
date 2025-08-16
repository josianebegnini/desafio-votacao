package com.sicredi.desafio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;

@Entity
@Table(name = "pauta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pauta")
    private Long idPauta;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date criacao;

    @Column(nullable = false)
    private boolean fechada;

    @PrePersist
    public void prePersist() {
        this.criacao = new Date();
        this.fechada = false;
    }

	public Long getIdPauta() {
		return idPauta;
	}

	public void setIdPauta(Long idPauta) {
		this.idPauta = idPauta;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getCriacao() {
		return criacao;
	}

	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}

	public boolean isFechada() {
		return fechada;
	}

	public void setFechada(boolean fechada) {
		this.fechada = fechada;
	}
}
