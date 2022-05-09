package co.com.monks.consumer.exception;

import co.com.monks.model.exception.ExceptionModel;
import com.google.gson.Gson;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public class CustomException {
    public static <T> Mono<T> createCustomException(final ClientResponse clientResponse) {
        Integer status = clientResponse.statusCode().value();
        return clientResponse.bodyToMono(String.class).flatMap(body -> {
            Gson gson = new Gson();
            ExceptionModel e = gson.fromJson(body, ExceptionModel.class);
            return Mono.error(new ExceptionModel(e.getErrors(), body));
        });
    }
}
