package co.com.monks.config;

import co.com.monks.model.twitter.api.gateway.OauthGateway;
import co.com.monks.model.twitter.api.gateway.TwitterGateway;
import co.com.monks.model.twitter.rds.repository.IUserRepository;
import co.com.monks.usecase.TwitterUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
public class UseCasesConfig {

        @Bean
        public TwitterUseCase twitterUseCase(TwitterGateway twitterGateway,
                                             OauthGateway oauthGateway, IUserRepository iUserRepository) {
                return new TwitterUseCase(twitterGateway,oauthGateway,iUserRepository);
        }
}
