package co.com.monks.api.exception;

import co.com.monks.model.exception.ErrorResponse;
import co.com.monks.model.exception.ExceptionModel;
import co.com.monks.model.exception.rds.BusinessException;
import co.com.monks.model.exception.rds.TechnicalException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    private static final String UNEXPECTED_EXCEPTION = "Error inesperado";
    private static final String UNEXPECTED_EXCEPTION_CODE = "TXT";
    private static final String ERRORS = "errors";

    public GlobalErrorWebExceptionHandler(DefaultErrorAttributes errorAttributes, ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        return Mono.just(request)
                .map(this::getError)
                .flatMap(Mono::error)
                .onErrorResume(TechnicalException.class, this::buildErrorResponse)
                .onErrorResume(BusinessException.class, this::buildErrorResponse)
                .onErrorResume(this::buildErrorResponse)
                .cast(ErrorResponse.class)
                .flatMap(this::buildResponse);
    }

    private Mono<ErrorResponse> buildErrorResponse(TechnicalException technicalException) {
        return Mono.just(ErrorResponse.builder()
                .reason(technicalException.getTechnicalErrorMessage().getMessage())
                .code(technicalException.getTechnicalErrorMessage().getCode())
                .message(technicalException.getTechnicalErrorMessage().getMessage())
                .build());
    }

    private Mono<ErrorResponse> buildErrorResponse(BusinessException businessException) {
        return Mono.just(ErrorResponse.builder()
                .reason(businessException.getBusinessErrorMessage().getMessage())
                .code(businessException.getBusinessErrorMessage().getCode())
                .message(businessException.getBusinessErrorMessage().getMessage())
                .build());
    }

    private Mono<ErrorResponse> buildErrorResponse(Throwable throwable) {
        return Mono.just(ErrorResponse.builder()
                .reason(UNEXPECTED_EXCEPTION)
                .code(UNEXPECTED_EXCEPTION_CODE)
                .message(throwable.getMessage())
                .build());
    }

    private Mono<ServerResponse> buildResponse(ErrorResponse errorDto) {
        Map<String, List<ErrorResponse>> errorResponse = Map.of(ERRORS, List.of(errorDto));
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }


}
