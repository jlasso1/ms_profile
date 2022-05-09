package co.com.monks.model.twitter.rds.repository;

import co.com.monks.model.twitter.rds.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRepository {
    Flux<User> findAllUser();
    Mono<User> findById(String id);
    Mono<User> updateById(User user);
}
