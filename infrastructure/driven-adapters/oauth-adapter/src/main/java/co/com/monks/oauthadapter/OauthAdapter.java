package co.com.monks.oauthadapter;

import co.com.monks.model.twitter.api.gateway.OauthGateway;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class OauthAdapter implements OauthGateway {
    @Value("${adapter.restconsumer.url}")
    private String url;

    @Value("${adapter.restconsumer.path}")
    private String path;

    @Value("${adapter.restconsumer.consumer-key}")
    private String key;

    @Value("${adapter.restconsumer.consumer-secret}")
    private String secret;

    @Value("${adapter.restconsumer.access-token}")
    private String token;

    @Value("${adapter.restconsumer.token-secret}")
    private String tokenSecret;

    private static final String AUTHORIZATION = "Authorization";

    private OAuthConsumer oAuthConsumer;

    public void setupContext(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        this.oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        oAuthConsumer.setTokenWithSecret(accessToken, accessTokenSecret);
        oAuthConsumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
    }

    @Override
    public Mono<String> getAccessToken(String uriP) {
        setupContext(key, secret, token, tokenSecret);

        String param = "";
        if(uriP.contains("?")) {
            Integer position = uriP.indexOf("?");
            param = uriP.substring(position);
        }
        HttpRequestBase httpRequest = null;
        URI uri = null;
        try {
            uri = new URI(url.concat(path).concat(param));
            httpRequest = new HttpGet(uri);
            return Mono.just(oAuthConsumer.sign(httpRequest).getHeader(AUTHORIZATION));
        } catch (OAuthMessageSignerException e) {
            return Mono.error(e);
        } catch (OAuthExpectationFailedException e) {
            return Mono.error(e);
        } catch (OAuthCommunicationException e) {
            return Mono.error(e);
        } catch (URISyntaxException e) {
            return Mono.error(e);
        }
    }
}
