package co.com.monks.consumer.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import reactor.core.publisher.Mono;

public class CustomExceptionTest {
    private CustomException customException;
    private ClientResponse clientResponse;

    @Before
    public void createWrapper() {
        this.clientResponse = Mockito.mock(ClientResponse.class);
    }

    @Test
    public void createCustomExceptionTest() {
        customException = new CustomException();
        String body = "{\"detail\": 123132123}";
        Mockito.when(clientResponse.bodyToMono(String.class)).thenReturn(Mono.just(body));
        ClientResponse clientResponse = ClientResponse.create(HttpStatus.BAD_REQUEST, jacksonStrategies()).body(body).build();
        customException.createCustomException(clientResponse);
        Assert.assertNotNull(customException);
    }

    static ExchangeStrategies jacksonStrategies() {
        return ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigure -> {
                    clientDefaultCodecsConfigure.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigure.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
                }).build();
    }
}
