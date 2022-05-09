package co.com.monks.oauthadapter;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.test.StepVerifier;



@RunWith(MockitoJUnitRunner.class)
public class OauthAdapterTest {

    @Autowired
    private OauthAdapter oauthAdapter;

    private static final String URL_TWITTER = "https://api.twitter.com/1.1/statuses";
    private static final String PATH = "/user_timeline.json";
    private static final String URL = "http://localhost:8080/profile/v1/twitter?screen_name=Rosell___&account=5";


    @Before
    public void init()  {
        oauthAdapter = new OauthAdapter();
        ReflectionTestUtils.setField(oauthAdapter,"url",URL_TWITTER);
        ReflectionTestUtils.setField(oauthAdapter,"path",PATH);
        ReflectionTestUtils.setField(oauthAdapter,"secret","secret");
        ReflectionTestUtils.setField(oauthAdapter,"token","token");
        ReflectionTestUtils.setField(oauthAdapter,"tokenSecret","tokenSecret");
    }

    @Test
    public void getAccessToken() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        ReflectionTestUtils.setField(oauthAdapter,"key","key");
        oauthAdapter.getAccessToken(URL);
        Assert.assertNotNull(oauthAdapter);
    }

    @Test
    public void getAccessTokenError() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
        StepVerifier.create(oauthAdapter.getAccessToken(URL))
                .verifyError();
    }

}
