package co.com.monks.api.utility;

import co.com.monks.api.twitter.commons.UserDTO;
import co.com.monks.model.exception.rds.BusinessException;
import co.com.monks.model.twitter.rds.User;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BodyValidatorTest {
    private BodyValidator bodyValidator;

    @Test
    public void validateIdNullTest() {
        UserDTO userDTO = new UserDTO();
        Mono<Boolean> res = bodyValidator.validateBody(userDTO);
        StepVerifier.create(res)
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    public void validateDescriptionEmptyTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setDescription("");
        userDTO.setImageUrl("image");
        userDTO.setTwitterUserName("name");
        userDTO.setTitle("title");
        Mono<Boolean> res = bodyValidator.validateBody(userDTO);
        StepVerifier.create(res)
                .expectError(BusinessException.class)
                .verify();
    }
}
