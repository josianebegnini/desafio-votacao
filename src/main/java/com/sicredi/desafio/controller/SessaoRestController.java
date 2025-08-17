package com.sicredi.desafio.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicredi.desafio.dto.request.SessaoRequestDTO;
import com.sicredi.desafio.dto.response.SessaoResponseDTO;
import com.sicredi.desafio.model.Sessao;
import com.sicredi.desafio.service.SessaoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/sessoes")
public class SessaoRestController {

    private final SessaoService sessaoService;

    public SessaoRestController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @Operation(summary = "Abre uma nova sessão")
    @PostMapping
    public ResponseEntity<String> criarSessao(@RequestBody SessaoRequestDTO sessaoDto) {
        try {
            sessaoService.criarSessao(sessaoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sessão aberta com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar sessão: " + e.getMessage());
        }
    }

    @Operation(summary = "Lista todas as sessões")
    @GetMapping
    public List<SessaoResponseDTO> listarSessoes() {
        return sessaoService.listarSessoes();
    }

    @Operation(summary = "Busca sessão por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            SessaoResponseDTO sessao = sessaoService.buscarPorId(id);
            return ResponseEntity.ok(sessao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Atualiza sessão existente")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarSessao(@PathVariable Long id, @RequestBody Sessao sessao) {
        try {
            sessaoService.atualizarSessao(id, sessao);
            return ResponseEntity.ok("Sessão atualizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar sessão: " + e.getMessage());
        }
    }

    @Operation(summary = "Remove sessão pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerSessao(@PathVariable Long id) {
        try {
            sessaoService.removerSessao(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover sessão: " + e.getMessage());
        }
    }

    @Operation(summary = "Lista apenas sessões abertas")
    @GetMapping("/abertas")
    public List<SessaoResponseDTO> listarAbertas() {
        return sessaoService.buscarAbertas();
    }
}

