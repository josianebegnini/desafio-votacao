package com.example.demo;

import com.sicredi.desafio.dto.request.VotacaoRequestDTO;
import com.sicredi.desafio.dto.response.AssociadoResponseDTO;
import com.sicredi.desafio.exceptions.VotacaoException;
import com.sicredi.desafio.mapper.PautaMapper;
import com.sicredi.desafio.mapper.SessaoMapper;
import com.sicredi.desafio.model.Associado;
import com.sicredi.desafio.model.Pauta;
import com.sicredi.desafio.model.Sessao;
import com.sicredi.desafio.service.AssociadoExternoService;
import com.sicredi.desafio.service.VotacaoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class VotacaoServiceTest {

    @InjectMocks
    private VotacaoService votacaoService;

    @Mock
    private AssociadoExternoService associadoExternoService;

    @Mock
    private SessaoMapper sessaoMapper;

    @Mock
    private PautaMapper pautaMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVotar_SessaoEncerrada() {
        // Mock do serviço externo
        when(associadoExternoService.chamaServicoExternoAssociadoPossuiPermissaoParaVotar(any(AssociadoResponseDTO.class)))
                .thenReturn(true);

        // Criando DTO de requisição
        VotacaoRequestDTO request = new VotacaoRequestDTO();
        request.setSessaoId(1L);
        request.setCpfAssociado("12345678900");
        request.setVoto("SIM");

        // Mock da sessão e da pauta
        Pauta pauta = mock(Pauta.class);
        when(pauta.getIdPauta()).thenReturn(1L);

        Sessao sessao = mock(Sessao.class);
        when(sessao.getPauta()).thenReturn(pauta);
        when(sessao.isFechada()).thenReturn(true);

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        // Executando teste
        assertThrows(VotacaoException.class, () -> {
            votacaoService.votar(request);
        });
    }
    
    @Test
    void testVotar_SessaoAberta() {
        Pauta pauta = mock(Pauta.class);
        when(pauta.getIdPauta()).thenReturn(1L);

        Sessao sessao = mock(Sessao.class);
        when(sessao.getPauta()).thenReturn(pauta);
        when(sessao.isFechada()).thenReturn(false);

        Associado associado = mock(Associado.class);

        when(associadoExternoService.getAssociado(anyLong()))
                .thenReturn(new AssociadoResponseDTO());

        // Não deve lançar exceção
        assertDoesNotThrow(() -> votacaoService.votar(sessao, associado));
    }

    @Test
    void testVotar_AssociadoJaVotou() {
        Pauta pauta = mock(Pauta.class);
        when(pauta.getIdPauta()).thenReturn(1L);

        Sessao sessao = mock(Sessao.class);
        when(sessao.getPauta()).thenReturn(pauta);
        when(sessao.isFechada()).thenReturn(false);

        Associado associado = mock(Associado.class);

        // Simula que o associado já votou
        when(votacaoService.associadoJaVotou(sessao, associado))
                .thenReturn(true);

        // Testa exceção
        assertThrows(VotacaoException.class, () -> {
            votacaoService.votar(sessao, associado);
        });
    }
}
