package co.com.monks.api.twitter;

import co.com.monks.model.twitter.api.TwitterResponse;
import co.com.monks.model.twitter.rds.User;
import co.com.monks.usecase.TwitterUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouterTest {
    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private String port;

    @MockBean
    private TwitterUseCase twitterUseCase;

    private User user;
    private MultiValueMap map = new LinkedMultiValueMap();
    private String url;
    private static String basepath = "profile/v1";

    @Before
    public void init() {
        url = "http://localhost:".concat(port).concat("/"+basepath).concat("/twitter");
        user = User.builder().id("1").description("description").imageUrl("image").twitterUserName("name").title("title").build();
        when(twitterUseCase.getUserTimeline(map,url)).thenReturn(Mono.just(TwitterResponse.builder().build()));
        when(twitterUseCase.findAll()).thenReturn(Flux.just(user));
        when(twitterUseCase.findById("2")).thenReturn(Mono.just(user));
        when(twitterUseCase.updateById(user)).thenReturn(Mono.just(user));
    }

    @Test
    public void listenUserTimeline() {
        final WebTestClient.ResponseSpec spec = webTestClient.get().uri(basepath.concat("/twitter"))
                .exchange();
        spec.expectStatus().isOk();
        verify(twitterUseCase).getUserTimeline(map,url);
    }

    @Test
    public void findAllUser() {
        final WebTestClient.ResponseSpec spec = webTestClient.get().uri(basepath.concat("/user/findAll"))
                .exchange();
        spec.expectStatus().isOk();
        verify(twitterUseCase).findAll();
    }

    @Test
    public void findUserById() {
        final WebTestClient.ResponseSpec spec = webTestClient.get().uri(basepath.concat("/user/2"))
                .exchange();
        spec.expectStatus().isOk();
        verify(twitterUseCase).findById("2");
    }

    @Test
    public void updateUserByCode() {
        final WebTestClient.ResponseSpec spec = webTestClient.patch().uri(basepath.concat("/user/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange();
        spec.expectStatus().isOk();
        verify(twitterUseCase).updateById(user);
    }
}
