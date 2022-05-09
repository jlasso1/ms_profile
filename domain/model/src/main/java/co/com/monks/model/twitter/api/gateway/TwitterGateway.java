package co.com.monks.model.twitter.api.gateway;

import co.com.monks.model.twitter.api.TwitterResponse;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public interface TwitterGateway {
    Mono<TwitterResponse> getUserTimeline(MultiValueMap<String,String> params, String authorization);
}
