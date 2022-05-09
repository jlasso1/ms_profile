package co.com.monks.reactivemysql.user;

import co.com.monks.model.twitter.rds.User;
import co.com.monks.reactivemysql.user.data.UserData;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends
        ReactiveCrudRepository<UserData, String> {

    @Modifying
    @Query("update zemoga_test_db.portfolio set description = ?, image_url = ?, twitter_user_name = ?, " +
            "title = ?  where idportfolio = ?")
    Mono<Integer> updateById(String description, String imageUrl, String twitterUseName, String title, String id);

    @Query("SELECT idportfolio, description, image_url, twitter_user_name, title FROM zemoga_test_db.portfolio where idportfolio = ?")
    Mono<UserData> findById(String id);
}
