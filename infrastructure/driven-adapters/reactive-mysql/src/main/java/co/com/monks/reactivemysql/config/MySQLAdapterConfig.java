package co.com.monks.reactivemysql.config;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@RequiredArgsConstructor
@EnableR2dbcRepositories
public class MySQLAdapterConfig extends AbstractR2dbcConfiguration {

    @Value("${postgres.host}")
    private String host;

    @Value("${postgres.database}")
    private String database;

    @Value("${postgres.port}")
    private Integer port;

    @Value("${postgres.username}")
    private String username;

    @Value("${postgres.password}")
    private String password;

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return MySqlConnectionFactory.from(MySqlConnectionConfiguration.builder()
                .host(host)
                .username(username)
                .password(password)
                .database(database)
                .build());
    }
}
