package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sicredi.desafio.DesafioBackendApplication;
import com.sicredi.desafio.model.Associado;
import com.sicredi.desafio.model.Sessao;
import com.sicredi.desafio.model.Votacao;
import com.sicredi.desafio.repository.VotacaoRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest(classes = DesafioBackendApplication.class)
@Testcontainers
class VotacaoRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("votacao")
            .withUsername("user")
            .withPassword("pass");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void deveSalvarEVincularAssociado() {
        Associado associado = new Associado();
        associado.setCpf("26981994070");
        associado.setNome("Associado");
        entityManager.persist(associado);

        Sessao sessao = new Sessao();
        sessao.setDescricao("sessao teste");
        entityManager.persist(sessao);

        Votacao votacao = new Votacao();
        votacao.setAssociado(associado);
        votacao.setSessao(sessao);
        votacao.setVoto("S");

        Votacao salvo = votacaoRepository.save(votacao);

        assertNotNull(salvo.getId());
        assertEquals("S", salvo.getVoto());
        assertEquals("Associado", salvo.getAssociado().getNome());
        assertEquals("sessao teste", salvo.getSessao().getDescricao());
    }
}
