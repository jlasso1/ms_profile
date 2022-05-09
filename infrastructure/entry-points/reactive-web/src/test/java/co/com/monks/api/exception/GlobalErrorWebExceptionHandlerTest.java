package co.com.monks.api.exception;

import co.com.monks.model.exception.rds.BusinessException;
import co.com.monks.model.exception.rds.TechnicalException;
import co.com.monks.model.exception.rds.message.BussinessErrorMessage;
import co.com.monks.model.exception.rds.message.TechnicalErrorMessage;
import co.com.monks.model.twitter.api.TwitterUser;
import co.com.monks.model.twitter.rds.User;
import co.com.monks.usecase.TwitterUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GlobalErrorWebExceptionHandlerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ApplicationContext applicationContext;
    @MockBean
    private TwitterUseCase twitterUseCase;
    private static final String BUILD_ERROR_RESPONSE = "buildErrorResponse";
    private static final String BASE_PATH = "twitter/v1/findAllNot";
    private final User user = User.builder().id("1").description("description").imageUrl("image").twitterUserName("name").title("title").build();

    @Before
    public void init() {
        when(twitterUseCase.findAll()).thenReturn(Flux.just(user));
    }

    @Test
    public void findAllChannelsWithException() {
        GlobalErrorWebExceptionHandler exceptHandler = applicationContext.getBean(GlobalErrorWebExceptionHandler.class);
        ReflectionTestUtils.invokeMethod(exceptHandler, BUILD_ERROR_RESPONSE, new TechnicalException(
                new RuntimeException("1"), TechnicalErrorMessage.USER_FIND_ALL));
        ReflectionTestUtils.invokeMethod(exceptHandler, BUILD_ERROR_RESPONSE,
                new BusinessException(BussinessErrorMessage.USER_NOT_FOUND));
        final WebTestClient.ResponseSpec spec = webTestClient.get().uri(BASE_PATH).exchange();
        spec.expectStatus().is4xxClientError();
    }
}
