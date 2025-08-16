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

@RestController
@RequestMapping("/api/v1/pautas") // versão 1
public class PautaRestController {

    private final PautaService pautaService;

    @Autowired
    public PautaRestController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public PautaResponseDTO criarPauta(@RequestBody PautaRequestDTO dto) {
        return pautaService.criarPauta(dto);
    }

    @GetMapping
    public List<PautaResponseDTO> listarPautas() {
        return pautaService.listarPautas();
    }

    @GetMapping("/{id}")
    public PautaResponseDTO buscarPorId(@PathVariable Long id) {
        return pautaService.buscarPorId(id);
    }

    @PutMapping
    public PautaResponseDTO atualizarPauta(@RequestBody PautaRequestDTO dto) {
        return pautaService.atualizarPauta(dto);
    }

    @DeleteMapping("/{id}")
    public void removerPauta(@PathVariable Long id) {
        pautaService.removerPauta(id);
    }

    @GetMapping("/titulo/{titulo}")
    public List<PautaResponseDTO> listarPorTitulo(@PathVariable String titulo) {
        return pautaService.buscarPorTitulo(titulo);
    }

    @GetMapping("/abertas")
    public List<PautaResponseDTO> listarAbertas() {
        return pautaService.buscarPautasAbertas();
    }

}
