package co.com.monks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebFluxSecurity
public class CorsConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .anyExchange()
                .permitAll()
                .and()
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .logout()
                .disable()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedMethod(HttpMethod.GET);
        corsConfig.addAllowedMethod(HttpMethod.PATCH);
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfig.setAllowedHeaders(Arrays.asList(getHeaders()));
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    protected String[] getHeaders() {
        return new String[]{
                "X-Requested-With",
                "Origin",
                "Content-Type",
                "Accept",
                "Authorization",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Headers",
                "Content-Security-Policy",
                "Strict-Transport-Security",
                "X-Frame-Options",
                "X-XSS-Protection",
                "X-Content-Type-Options",
                "X-Download-Options"
        };
    }
}
