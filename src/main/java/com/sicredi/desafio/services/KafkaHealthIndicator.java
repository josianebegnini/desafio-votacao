package com.sicredi.desafio.services;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaHealthIndicator implements HealthIndicator {

    private final String bootstrapServers = "localhost:9092"; // ou sua configuração real

    @Override
    public Health health() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient client = AdminClient.create(props)) {
            client.listTopics().names().get(); // tenta listar tópicos para testar a conexão
            return Health.up().build();
        } catch (InterruptedException | ExecutionException e) {
            return Health.down(e).build();
        }
    }
}
