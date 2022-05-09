package co.com.monks.api.twitter.commons;

import co.com.monks.api.utility.BodyValidator;
import co.com.monks.model.exception.rds.BusinessException;
import co.com.monks.model.exception.rds.message.BussinessErrorMessage;
import co.com.monks.model.twitter.rds.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.Validator;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final Validator validator;

    public Mono<User> isValidUpdateBody(UserDTO userDTO, String id) {
        return Mono.just(userDTO)
                .flatMap(res -> BodyValidator.validateBody(userDTO))
                .map(validator::validate)
                .map(Set::isEmpty)
                .filter(valid -> valid)
                .switchIfEmpty(Mono.error(new BusinessException(BussinessErrorMessage.BAD_REQUEST_BODY)))
                .then(Mono.just(toAgentModel(userDTO,id)));
    }

    private User toAgentModel(UserDTO userDTO, String id) {
        return User.builder()
                .id(id)
                .description(userDTO.getDescription())
                .imageUrl(userDTO.getImageUrl())
                .twitterUserName(userDTO.getTwitterUserName())
                .title(userDTO.getTitle())
                .build();
    }
}
