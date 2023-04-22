package org.heb.api.images.config;


import org.heb.api.images.util.RestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestConfig {

    @Bean(name = "imaggaClient")
    public WebClient imaggaClient(
            @Value("${heb.imagga-url}") String url,
            @Value("${heb.imagga-api-key}") String key) {
        return WebClient.builder()
                .defaultHeader("Authorization", String.format("Basic %s", key))
                .baseUrl(url)
                .build();
    }

    @Bean
    public RestRepo imaggaTagger(
            @Autowired WebClient imaggaClient) {
        return new RestRepo(HttpMethod.GET, imaggaClient);
    }
}
