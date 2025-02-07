package br.com.TriagemCheck.configs;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class OpenAPIConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("br.com.TriagemCheck")
                .pathsToMatch("/**")
                .build();
    }
}
