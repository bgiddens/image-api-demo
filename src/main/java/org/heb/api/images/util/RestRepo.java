package org.heb.api.images.util;

import lombok.AllArgsConstructor;
import org.heb.api.images.exceptions.RestRequestException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
public class RestRepo {

    private final HttpMethod method;
    private final WebClient webClient;

    public <RESPONSE> RESPONSE execute(MultiValueMap<String, String> params, Class<RESPONSE> responseType)
            throws RestRequestException {

        try {
            var mono = this.webClient
                    .method(method)
                    .uri(uriBuilder -> uriBuilder.queryParams(params).build())
                    .exchangeToMono(res -> {
                        if (res.statusCode().equals(HttpStatus.OK)) {
                            return res.bodyToMono(responseType);
                        } else {
                            return res.createException()
                                    .flatMap(Mono::error);
                        }
                    });
            return mono.block(Duration.of(5000, ChronoUnit.MILLIS));
        } catch (Exception e) {
            throw new RestRequestException("Imagga API Failure", e);
        }
    }
}
