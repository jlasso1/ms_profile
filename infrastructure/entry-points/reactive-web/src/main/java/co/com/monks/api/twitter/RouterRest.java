package co.com.monks.api.twitter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@RequiredArgsConstructor
@Configuration
public class RouterRest {
    private static String path = "profile/v1";

    @Bean
    public RouterFunction<ServerResponse> routes(Handler handler) {
        return route()
                .GET(path.concat("/twitter"), accept(MediaType.APPLICATION_JSON),handler::listenUserTimeline)
                .GET(path.concat("/user/findAll"), accept(MediaType.APPLICATION_JSON),request -> handler.findAll())
                .GET(path.concat("/user/{id}"), accept(MediaType.APPLICATION_JSON),handler::findById)
                .PATCH(path.concat("/user/{id}"), accept(MediaType.APPLICATION_JSON),handler::update)
                .build();
    }
}
