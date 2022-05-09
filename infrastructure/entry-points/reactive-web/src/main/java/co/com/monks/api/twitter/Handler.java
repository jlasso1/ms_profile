package co.com.monks.api.twitter;

import co.com.monks.api.twitter.commons.RequestValidator;
import co.com.monks.api.twitter.commons.UserDTO;
import co.com.monks.model.twitter.api.TwitterResponse;
import co.com.monks.model.twitter.rds.User;
import co.com.monks.usecase.TwitterUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class Handler {
    public static final String PARAMETER_ID = "id";

    private final RequestValidator requestValidator;
    private final TwitterUseCase twitterUseCase;
    public Mono<ServerResponse> listenUserTimeline(ServerRequest serverRequest) {
        return twitterUseCase.getUserTimeline(serverRequest.queryParams(),serverRequest.uri().toString())
                .flatMap(res -> bodyBuilder(res));
    }

    public Mono<ServerResponse> findAll(){
        return twitterUseCase.findAll()
                .collectList()
                .flatMap(userList -> bodyBuilder(userList));
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return twitterUseCase.findById(serverRequest.pathVariable("id"))
                .flatMap(user -> bodyBuilder(user));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String userId = request.pathVariable(PARAMETER_ID);
        return request.bodyToMono(UserDTO.class)
                .flatMap(user -> requestValidator.isValidUpdateBody(user,userId)
                .flatMap(twitterUseCase::updateById)
                .flatMap(this::bodyBuilder));
    }

    private Mono<ServerResponse> bodyBuilder(User response) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response);
    }

    private Mono<ServerResponse> bodyBuilder(List<User> response) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response);
    }

    private Mono<ServerResponse> bodyBuilder(TwitterResponse response) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response);
    }


}
