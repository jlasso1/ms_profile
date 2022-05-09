package co.com.monks.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

public class CorsConfigTest {

    @InjectMocks
    public CorsConfig corsConfig;

    static class Security extends ServerHttpSecurity {
    }

    @Before
    public void init(){
        corsConfig = new CorsConfig();
    }

    @Test
    public void springSecurityFilterChainTest() {
        Security security = new Security();
        SecurityWebFilterChain securityWebFilterChain = corsConfig.springSecurityFilterChain(security);
        Assert.assertNotNull(securityWebFilterChain);
    }

    @Test
    public void getHeadersTest(){
        Assert.assertNotNull(corsConfig.getHeaders());
    }

    @Test
    public void corsConfigurationTest(){
        Assert.assertNotNull(corsConfig.corsConfiguration());
    }
}
