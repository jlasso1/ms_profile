package co.com.monks.consumer;

import co.com.monks.model.twitter.api.TwitterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import org.apache.http.HttpHeaders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class RestConsumerTest {
    private WebClient webClient;

    private RestConsumer restConsumer;

    public static MockWebServer mockBackEnd;

    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        webClient = WebClient.builder().baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        restConsumer = new RestConsumer(webClient);
    }

    @Test
    public void getUserTimelineTest() throws IOException {
        MultiValueMap params = new LinkedMultiValueMap();
        TwitterResponse response = TwitterResponse.builder().build();
        Flux<TwitterResponse> source = Flux.just(response);
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mapper.writeValueAsString(source))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        restConsumer.getUserTimeline(params,"123");

        StepVerifier.create(source)
                .expectNext(response)
                .expectComplete()
                .verify();
    }

    @After
    public void endTest() throws IOException {
        mockBackEnd.shutdown();
    }

}
