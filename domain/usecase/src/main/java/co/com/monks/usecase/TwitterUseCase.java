package co.com.monks.usecase;

import co.com.monks.model.exception.ExceptionModel;
import co.com.monks.model.exception.rds.BusinessException;
import co.com.monks.model.exception.rds.message.BussinessErrorMessage;
import co.com.monks.model.twitter.api.TwitterResponse;
import co.com.monks.model.twitter.api.gateway.OauthGateway;
import co.com.monks.model.twitter.api.gateway.TwitterGateway;
import co.com.monks.model.twitter.rds.repository.IUserRepository;
import co.com.monks.model.twitter.rds.User;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TwitterUseCase {
    private final TwitterGateway twitterGateway;
    private final OauthGateway oauthGateway;
    private final IUserRepository repository;

    public Mono<TwitterResponse> getUserTimeline(MultiValueMap<String,String> params, String uri) {
        return oauthGateway.getAccessToken(uri)
                .flatMap(authorization -> twitterGateway.getUserTimeline(params,authorization))
                .onErrorResume(ExceptionModel.class, e -> Mono.error(e));
    }

    public Flux<User> findAll(){ return repository.findAllUser(); }

    public Mono<User> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(BussinessErrorMessage.USER_NOT_FOUND)));
    }

    public Mono<User> updateById(User user){
        return findById(user.getId())
                .flatMap(res -> repository.updateById(user));
    }

}
