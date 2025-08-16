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

import com.sicredi.desafio.dto.request.AssociadoRequestDTO;
import com.sicredi.desafio.dto.response.AssociadoResponseDTO;
import com.sicredi.desafio.service.AssociadoService;

@RestController
@RequestMapping("/api/v1/associados")
public class AssociadoRestController {
	
    private AssociadoService associadoService;

    @Autowired
    public AssociadoRestController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @PostMapping(value = "cadastrar", headers = "Accept=application/json")
    public ResponseEntity<AssociadoResponseDTO> criarAssociado(@RequestBody AssociadoRequestDTO requestDTO) {
    	try {	
    		AssociadoResponseDTO responseDTO = associadoService.criarAssociado(requestDTO);
    		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    	}
    }

    @GetMapping(value = "listar", headers = "Accept=application/json")
    public ResponseEntity<List<AssociadoResponseDTO>> listarAssociados() {
        List<AssociadoResponseDTO> associados = associadoService.listarAssociados();
        return ResponseEntity.ok(associados);
    }
    
    @GetMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<AssociadoResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            AssociadoResponseDTO associado = associadoService.buscarPorId(id);
            return ResponseEntity.ok(associado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping(value = "atualizar", headers = "Accept=application/json")
    public ResponseEntity<AssociadoResponseDTO> atualizarAssociado(@RequestBody AssociadoResponseDTO requestDTO) {
        try {
            AssociadoResponseDTO atualizado = associadoService.atualizarAssociado(requestDTO);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping(value = "remover/{id}", headers = "Accept=application/json")
    public ResponseEntity<Void> removerAssociado(@PathVariable Long id) {
        try {
            associadoService.removerAssociadoPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/cpf/{cpf}", headers = "Accept=application/json")
    public ResponseEntity<AssociadoResponseDTO> buscarPorCpf(@PathVariable String cpf) {
        try {
            AssociadoResponseDTO associado = associadoService.buscarPorCpf(cpf);
            return ResponseEntity.ok(associado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
