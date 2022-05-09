package co.com.monks.api.utility;

import co.com.monks.api.twitter.commons.UserDTO;
import co.com.monks.model.exception.rds.BusinessException;
import co.com.monks.model.exception.rds.message.BussinessErrorMessage;
import reactor.core.publisher.Mono;
public class BodyValidator {

    public static Mono<Boolean> validateBody(UserDTO userDTO) {
        return notNullValidation(userDTO.getDescription())
                .flatMap(validate -> notNullValidation(userDTO.getImageUrl()))
                .flatMap(validate -> notNullValidation(userDTO.getTwitterUserName()))
                .flatMap(validate -> notNullValidation(userDTO.getTitle()));
    }

    private static Mono<Boolean> notNullValidation(String fieldValue) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            return Mono.error(new BusinessException(BussinessErrorMessage.BAD_REQUEST_BODY));
        }
        return Mono.just(true);
    }
}
