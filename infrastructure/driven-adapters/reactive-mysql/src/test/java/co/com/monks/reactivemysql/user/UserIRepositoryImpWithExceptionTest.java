package co.com.monks.reactivemysql.user;

import co.com.monks.model.exception.rds.TechnicalException;
import co.com.monks.model.twitter.rds.User;
import co.com.monks.reactivemysql.user.data.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserIRepositoryImpWithExceptionTest {

    @InjectMocks
    private UserRepositoryImplement userRepositoryImplement;
    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    private final String ID = "1";
    private final String DESCRIPTION = "Description";
    private final String IMAGE_URL = "image";
    private final String NAME = "name";
    private final String TITLE = "title";

    private User user = User.builder()
            .id(ID)
            .description(DESCRIPTION)
            .imageUrl(IMAGE_URL)
            .twitterUserName(NAME)
            .title(TITLE)
            .build();

    @Test
    public void findAllProviderWithException() {
        when(userRepository.findAll()).thenReturn(Flux.error(RuntimeException::new));
        userRepositoryImplement.findAllUser()
                .as(StepVerifier::create)
                .expectError(TechnicalException.class)
                .verify();
    }

    @Test
    public void findUserByIdWithException() {
        when(userRepository.findById(ID)).thenReturn(Mono.error(RuntimeException::new));
        userRepositoryImplement.findById(ID)
                .as(StepVerifier::create)
                .expectError(TechnicalException.class)
                .verify();
    }

    @Test
    public void updateByIdWithException() {
        when(userRepository.updateById(DESCRIPTION,IMAGE_URL,
                NAME,TITLE,ID)).thenReturn(Mono.error(RuntimeException::new));
        userRepositoryImplement.updateById(user)
                .as(StepVerifier::create)
                .expectError(TechnicalException.class)
                .verify();
    }
}
