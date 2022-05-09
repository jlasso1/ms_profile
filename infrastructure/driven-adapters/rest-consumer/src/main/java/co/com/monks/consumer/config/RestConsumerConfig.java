package co.com.monks.consumer.config;

import co.com.monks.consumer.RestConsumer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
@RequiredArgsConstructor
public class RestConsumerConfig {

    @Value("${adapter.restconsumer.url}")
    private String url;

    @Bean
    public WebClient webClient() throws SSLException {

        SslContext context = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        HttpClient httpClient = HttpClient.newConnection().secure(t -> t.sslContext(context)).compress(true);

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(url)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

}
