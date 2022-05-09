package co.com.monks.consumer;

import co.com.monks.consumer.exception.CustomException;
import co.com.monks.model.twitter.api.TwitterResponse;
import co.com.monks.model.twitter.api.gateway.TwitterGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RestConsumer implements TwitterGateway {

    @Value("${adapter.restconsumer.path}")
    private String path;
    private final WebClient client;
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public Mono<TwitterResponse> getUserTimeline(MultiValueMap<String, String> params, String authorization) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParams(params)
                        .build())
                .header(AUTHORIZATION,authorization)
                .retrieve()
                .onStatus(HttpStatus::isError, CustomException::createCustomException)
                .bodyToMono(TwitterResponse.class);
    }
}