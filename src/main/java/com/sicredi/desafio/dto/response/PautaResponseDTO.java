package com.sicredi.desafio.dto.response;

public class PautaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private boolean fechada;

    public PautaResponseDTO() {
    }

    public PautaResponseDTO(Long id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

	public boolean isFechada() {
		return fechada;
	}

	public void setFechada(boolean fechada) {
		this.fechada = fechada;
	}
}
