package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.sicredi.desafio.dto.request.VotacaoRequestDTO;
import com.sicredi.desafio.dto.response.AssociadoResponseDTO;
import com.sicredi.desafio.exceptions.VotacaoException;
import com.sicredi.desafio.mapper.PautaMapper;
import com.sicredi.desafio.mapper.SessaoMapper;
import com.sicredi.desafio.model.Associado;
import com.sicredi.desafio.model.Pauta;
import com.sicredi.desafio.model.Sessao;
import com.sicredi.desafio.repository.AssociadoRepository;
import com.sicredi.desafio.repository.SessaoRepository;
import com.sicredi.desafio.repository.VotacaoRepository;
import com.sicredi.desafio.service.AssociadoExternoService;
import com.sicredi.desafio.service.VotacaoService;

class VotacaoServiceTest {

    @Spy
    @InjectMocks
    private VotacaoService votacaoService;

    @Mock
    private AssociadoExternoService associadoExternoService;

    @Mock
    private SessaoMapper sessaoMapper;

    @Mock
    private PautaMapper pautaMapper;

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private VotacaoRepository votacaoRepository; // ✅ mock do repository do voto

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVotar_SessaoEncerrada() {
        when(associadoExternoService.chamaServicoExternoAssociadoPossuiPermissaoParaVotar(any(AssociadoResponseDTO.class)))
                .thenReturn(true);

        VotacaoRequestDTO request = new VotacaoRequestDTO();
        request.setSessaoId(1L);
        request.setCpfAssociado("12345678900");
        request.setVoto("SIM");

        Pauta pauta = mock(Pauta.class);
        when(pauta.getId()).thenReturn(1L);

        Sessao sessao = mock(Sessao.class);
        when(sessao.getPauta()).thenReturn(pauta);
        when(sessao.isFechada()).thenReturn(true);
        when(sessao.getDtSessao()).thenReturn(LocalDateTime.now().minusMinutes(10));
        when(sessao.getDtEncerramento()).thenReturn(LocalDateTime.now().minusMinutes(1));

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        assertThrows(VotacaoException.class, () -> votacaoService.votar(request));
    }

    @Test
    void testVotar_SessaoAberta() {
        Pauta pauta = mock(Pauta.class);
        when(pauta.getId()).thenReturn(1L);

        Sessao sessao = mock(Sessao.class);
        when(sessao.getPauta()).thenReturn(pauta);
        when(sessao.isFechada()).thenReturn(false);
        when(sessao.getDtSessao()).thenReturn(LocalDateTime.now().minusMinutes(10));
        when(sessao.getDtEncerramento()).thenReturn(LocalDateTime.now().plusMinutes(10));

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(associadoExternoService.chamaServicoExternoAssociadoPossuiPermissaoParaVotar(any()))
                .thenReturn(true);

        Associado associado = mock(Associado.class);
        when(associadoRepository.findByCpf("12345678900")).thenReturn(Optional.of(associado));

        VotacaoRequestDTO request = new VotacaoRequestDTO();
        request.setSessaoId(1L);
        request.setCpfAssociado("12345678900");
        request.setVoto("SIM");

        assertDoesNotThrow(() -> votacaoService.votar(request));
        
    }

    @Test
    void testVotar_AssociadoJaVotou() {
        Pauta pauta = mock(Pauta.class);
        when(pauta.getId()).thenReturn(1L);

        Sessao sessao = mock(Sessao.class);
        when(sessao.getPauta()).thenReturn(pauta);
        when(sessao.isFechada()).thenReturn(false);
        when(sessao.getDtSessao()).thenReturn(LocalDateTime.now().minusMinutes(10));
        when(sessao.getDtEncerramento()).thenReturn(LocalDateTime.now().plusMinutes(10));

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(associadoExternoService.chamaServicoExternoAssociadoPossuiPermissaoParaVotar(any()))
                .thenReturn(true);

        Associado associado = mock(Associado.class);
        when(associadoRepository.findByCpf("12345678900")).thenReturn(Optional.of(associado));

        // Faz o método void lançar a exceção usando spy
        doThrow(new VotacaoException("Associado já votou"))
            .when(votacaoService)
            .validaAssociadoJaVotou(sessao, associado);

        VotacaoRequestDTO request = new VotacaoRequestDTO();
        request.setSessaoId(1L);
        request.setCpfAssociado("12345678900");
        request.setVoto("SIM");

        assertThrows(VotacaoException.class, () -> votacaoService.votar(request));
    }
}
