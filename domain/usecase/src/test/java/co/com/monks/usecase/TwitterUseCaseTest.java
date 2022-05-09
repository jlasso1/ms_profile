package co.com.monks.usecase;

import co.com.monks.model.exception.ExceptionModel;
import co.com.monks.model.twitter.api.TwitterResponse;
import co.com.monks.model.twitter.api.TwitterUser;
import co.com.monks.model.twitter.api.gateway.OauthGateway;
import co.com.monks.model.twitter.api.gateway.TwitterGateway;
import co.com.monks.model.twitter.rds.User;
import co.com.monks.model.twitter.rds.repository.IUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.LinkedList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterUseCaseTest {

    @InjectMocks
    private TwitterUseCase twitterUseCase;

    @Mock
    private TwitterGateway twitterGateway;

    @Mock
    private OauthGateway oauthGateway;

    @Mock
    private IUserRepository repository;

    private final String ID = "1";
    private final String DESCRIPTION = "Description";
    private final String IMAGE_URL = "image";
    private final String NAME = "name";
    private final String TITLE = "title";

    private User user = User.builder()
            .id(ID)
            .description(DESCRIPTION)
            .imageUrl(IMAGE_URL)
            .twitterUserName(NAME)
            .title(TITLE)
            .build();

    private Flux<User> userFlux;
    private Mono<User> userMono;

    private TwitterUser twitterUser = TwitterUser
            .builder()
            .name(NAME)
            .profile_image_url(IMAGE_URL)
            .build();

    private TwitterResponse twitterResponse = TwitterResponse
            .builder()
            .build();
    private MultiValueMap param = new LinkedMultiValueMap();
    private static final String url = "http://localhost:8080/profile/v1/twitter?screen_name=Rosell___&account=5";

    @Before
    public void init() {
        userFlux = Flux.just(user);
        userMono = Mono.just(user);
    }

    @Test
    public void getUserTimeline() {
        when(oauthGateway.getAccessToken(url)).thenReturn(Mono.just("123"));
        when(twitterGateway.getUserTimeline(param,"123")).thenReturn(Mono.just(twitterResponse));
        Mono<TwitterResponse> response = twitterUseCase.getUserTimeline(param,url);
        StepVerifier.create(response)
                .expectNext(twitterResponse)
                .expectComplete()
                .verify();
    }

    @Test
    public void getUserTimelineError() {
        when(oauthGateway.getAccessToken(url)).thenReturn(Mono.error(new ExceptionModel(new LinkedList(),"")));
        Mono<TwitterResponse> response = twitterUseCase.getUserTimeline(param,url);
        StepVerifier.create(response)
                .verifyError();
    }

    @Test
    public void findAllUser() {
        when(twitterUseCase.findAll()).thenReturn(userFlux);
        final Flux<User> result = twitterUseCase.findAll();
        StepVerifier.create(result).expectNextCount(1).verifyComplete();
        verify(repository).findAllUser();
    }

    /*@Test
    public void findByIdAgentWithException() {
        Mockito.when(twitterUseCase.findById(ID).thenReturn(Mono.empty()));
        twitterUseCase.updateById(user)
                .as(StepVerifier::create)
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    public void findById() {
        final Mono<User> result = twitterUseCase.findById(ID);
        StepVerifier.create(result).expectNextCount(1).verifyComplete();
        verify(repository).findById(ID);
    }

    @Test
    public void updateById() {
        when(twitterUseCase.findById("1")).thenReturn(Mono.empty());
        twitterUseCase.updateById(user)
                .as(StepVerifier::create)
                .expectError(BusinessException.class)
                .verify();
    }*/

}
