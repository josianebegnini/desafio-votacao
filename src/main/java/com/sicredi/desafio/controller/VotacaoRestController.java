package com.sicredi.desafio.controller;

import java.util.List;

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

import com.sicredi.desafio.dto.request.VotacaoRequestDTO;
import com.sicredi.desafio.exceptions.VotacaoException;
import com.sicredi.desafio.model.Votacao;
import com.sicredi.desafio.service.ContabilizaService;
import com.sicredi.desafio.service.VotacaoService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/votacoes")
public class VotacaoRestController {

    private final VotacaoService votacaoService;
    private final ContabilizaService contabilizarService;

    public VotacaoRestController(VotacaoService votacaoService, ContabilizaService contabilizarService) {
        this.votacaoService = votacaoService;
        this.contabilizarService = contabilizarService;
    }

    @Operation(summary = "Votar em uma sessão")
    @PostMapping
    public ResponseEntity<?> votar(@RequestBody VotacaoRequestDTO votacaoDto) {
        try {
            votacaoService.votar(votacaoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Voto registrado com sucesso.");
        } catch (VotacaoException ve) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao votar: " + e.getMessage());
        }
    }

    @Operation(summary = "Lista todas as votações")
    @GetMapping
    public ResponseEntity<List<Votacao>> listarVotacoes() {
        return ResponseEntity.ok(votacaoService.listarVotacoes());
    }

    @Operation(summary = "Busca votação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Votacao> buscarPorId(@PathVariable Long id) {
        return votacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza Votação")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVotacao(@PathVariable Long id, @RequestBody Votacao votacao) {
        try {
            votacaoService.atualizarVotacao(id, votacao);
            return ResponseEntity.ok("Votação atualizada com sucesso.");
        } catch (VotacaoException ve) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar voto: " + e.getMessage());
        }
    }

    @Operation(summary = "Remove Votação")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerVotacao(@PathVariable Long id) {
        try {
            votacaoService.removerVotacaoPorId(id);
            return ResponseEntity.noContent().build();
        } catch (VotacaoException ve) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ve.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover voto: " + e.getMessage());
        }
    }

    @Operation(summary = "Contabiliza os votos")
    @PostMapping("/sessoes/{idSessao}/contabilizar")
    public ResponseEntity<?> contabilizarVotacao(@PathVariable Long idSessao) {
        try {
            String resultado = contabilizarService.contabilizar(idSessao);
            return ResponseEntity.ok("Sessão de votação encerrada e votos contabilizados. Resultado: " + resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao contabilizar votos: " + e.getMessage());
        }
    }
}
