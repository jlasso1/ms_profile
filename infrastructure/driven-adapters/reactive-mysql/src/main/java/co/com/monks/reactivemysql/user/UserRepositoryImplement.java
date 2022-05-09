package co.com.monks.reactivemysql.user;

import co.com.monks.commons.AdapterOperations;
import co.com.monks.model.exception.rds.TechnicalException;
import co.com.monks.model.exception.rds.message.TechnicalErrorMessage;
import co.com.monks.model.twitter.rds.repository.IUserRepository;
import co.com.monks.model.twitter.rds.User;
import co.com.monks.reactivemysql.user.data.UserData;
import co.com.monks.reactivemysql.user.data.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImplement  extends
        AdapterOperations<User, UserData, String, UserRepository>
        implements IUserRepository {

    private static final int NUMBER_OF_ROWS = 1;

    @Autowired
    public UserRepositoryImplement(UserRepository repository,
                                          UserMapper userMapper) {
        super(repository, userMapper::toData, userMapper::toEntity);
    }

    @Override
    public Flux<User> findAllUser() {
        return super.findAll()
                .onErrorMap(Exception.class, exception ->
                        new TechnicalException(exception, TechnicalErrorMessage.USER_FIND_ALL));
    }

    @Override
    public Mono<User> findById(String id) {
        return doQuery(repository.findById(id))
                .onErrorMap(Exception.class, exception ->
                        new TechnicalException(exception, TechnicalErrorMessage.USER_FIND_BY_ID));
    }

    @Override
    public Mono<User> updateById(User user) {
        return repository.updateById(user.getDescription(), user.getImageUrl(),
                        user.getTwitterUserName(), user.getTitle(), user.getId())
                .filter(rowsAffected -> rowsAffected == NUMBER_OF_ROWS)
                .map(queryResult -> user)
                .onErrorMap(Exception.class, exception ->
                        new TechnicalException(exception, TechnicalErrorMessage.USER_UPDATE));
    }
}
