package com.overflow.stack.commons.config;

import com.overflow.stack.commons.repository.es.EsAnswerRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = EsAnswerRepository.class)
public class SoEsConfig extends AbstractElasticsearchConfiguration {
    @Value("#{'${elastic.search.hosts:localhost:9200}'.split(',')}")
    private List<String> esHosts;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esHosts.toArray(new String[0]))
                .build();
        return RestClients.create(clientConfiguration).rest();
    }


}