package com.sicredi.desafio.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "votacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Votacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_votacao")
    private Long idVotacao;

    @Column(name = "data_voto", nullable = false)
    private LocalDateTime dtVoto;

    @Column(nullable = false, length = 1) // Ex: "S" ou "N"
    private String voto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_id", nullable = false)
    private Sessao sessao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cpf", nullable = false)
    private Associado associado;

    @PrePersist
    public void prePersist() {
        if (this.dtVoto == null) {
            this.dtVoto = LocalDateTime.now();
        }
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

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}
    
    
}
