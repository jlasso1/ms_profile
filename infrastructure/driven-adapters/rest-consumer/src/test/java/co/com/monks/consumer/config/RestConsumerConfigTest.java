package co.com.monks.consumer.config;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;

public class RestConsumerConfigTest {
    private RestConsumerConfig restConsumerConfig;

    @Test
    public void createWebClientTest() throws SSLException {
        restConsumerConfig = new RestConsumerConfig();
        WebClient webClient = restConsumerConfig.webClient();
        Assert.assertNotNull(webClient);
    }
}
