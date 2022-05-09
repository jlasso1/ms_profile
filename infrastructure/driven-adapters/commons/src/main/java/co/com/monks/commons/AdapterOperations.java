package co.com.monks.commons;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public abstract class AdapterOperations<E, D, I, R extends ReactiveCrudRepository<D, I>> {

    protected R repository;
    protected Function<E,D> toData;
    protected Function<D,E> toEntity;

    @SuppressWarnings("unchecked")
    public AdapterOperations(R repository, Function<E,D> toData, Function<D,E> toEntity) {
        this.repository = repository;
        this.toData = toData;
        this.toEntity = toEntity;
    }

    protected Mono<E> doQuery(Mono<D> query) {
        return query.map(this::toEntity);
    }

    public Flux<E> findAll() {
        return doQueryMany(repository.findAll());
    }


    protected Flux<E> doQueryMany(Flux<D> query) {
        return query.map(this::toEntity);
    }

    protected E toEntity(D data){
        return toEntity.apply(data);
    }


}