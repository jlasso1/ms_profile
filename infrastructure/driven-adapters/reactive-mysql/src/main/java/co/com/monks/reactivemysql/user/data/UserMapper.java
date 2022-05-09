package co.com.monks.reactivemysql.user.data;

import co.com.monks.model.twitter.rds.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserData userData);
    UserData toData(User user);
}
