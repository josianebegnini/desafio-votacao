package com.sicredi.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.desafio.dto.request.PautaRequestDTO;
import com.sicredi.desafio.dto.response.PautaResponseDTO;
import com.sicredi.desafio.service.PautaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/pautas")
public class PautaRestController {

    private final PautaService pautaService;

    @Autowired
    public PautaRestController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @Operation(summary = "Cria uma nova pauta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pauta criada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public PautaResponseDTO criarPauta(@RequestBody PautaRequestDTO dto) {
        return pautaService.criarPauta(dto);
    }

    @Operation(summary = "Lista todas as pautas")
    @GetMapping
    public List<PautaResponseDTO> listarPautas() {
        return pautaService.listarPautas();
    }

    @Operation(summary = "Busca pauta pelo ID")
    @GetMapping("/{id}")
    public PautaResponseDTO buscarPorId(@PathVariable Long id) {
        return pautaService.buscarPorId(id);
    }


    @Operation(summary = "Atualiza uma pauta existente")
    @PutMapping("/{id}")
    public PautaResponseDTO atualizarPauta(@PathVariable Long id, @RequestBody PautaRequestDTO dto) {
        return pautaService.atualizarPauta(id, dto);
    }

    @Operation(summary = "Remove uma pauta pelo ID")
    @DeleteMapping("/{id}")
    public void removerPauta(@PathVariable Long id) {
        pautaService.removerPauta(id);
    }

    @Operation(summary = "Busca pautas pelo título")
    @GetMapping("/titulo/{titulo}")
    public List<PautaResponseDTO> listarPorTitulo(@PathVariable String titulo) {
        return pautaService.buscarPorTitulo(titulo);
    }

    @Operation(summary = "Lista apenas pautas abertas")
    @GetMapping("/abertas")
    public List<PautaResponseDTO> listarAbertas() {
        return pautaService.buscarPautasAbertas();
    }

}