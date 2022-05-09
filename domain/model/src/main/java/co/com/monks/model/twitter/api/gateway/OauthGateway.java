package co.com.monks.model.twitter.api.gateway;

import reactor.core.publisher.Mono;

public interface OauthGateway {
    Mono<String> getAccessToken(String uri);
}
