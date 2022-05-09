package co.com.monks.config;

import co.com.monks.model.twitter.api.gateway.OauthGateway;
import co.com.monks.model.twitter.api.gateway.TwitterGateway;
import co.com.monks.model.twitter.rds.repository.IUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UseCaseConfigTest {

    @InjectMocks
    UseCasesConfig useCaseConfig;

    @Mock
    TwitterGateway twitterGateway;

    @Mock
    OauthGateway oauthGateway;

    @Mock
    IUserRepository repository;

    @Test
    public void twitterUseCaseBeanTest() {
        Assert.assertNotNull(useCaseConfig.twitterUseCase(twitterGateway,
                oauthGateway,repository));
    }

}
