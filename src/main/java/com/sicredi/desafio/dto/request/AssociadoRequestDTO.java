package com.sicredi.desafio.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AssociadoRequestDTO {
	    @NotBlank
	    private String nome;

	    @NotBlank
	    private String cpf;

	    public String getNome() { return nome; }
	    public void setNome(String nome) { this.nome = nome; }

	    public String getCpf() { return cpf; }
	    public void setCpf(String cpf) { this.cpf = cpf; }
}
