package co.com.monks.reactivemysql.user.config;

import co.com.monks.reactivemysql.config.MySQLAdapterConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

public class MySQLAdapterConfigTest {

    public static final String HOST = "host";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String DATABASE = "database";
    @InjectMocks
    MySQLAdapterConfig mySQLAdapterConfig;

    @Before
    public void init()  {
        mySQLAdapterConfig = new MySQLAdapterConfig();
        ReflectionTestUtils.setField(mySQLAdapterConfig,HOST,HOST);
        ReflectionTestUtils.setField(mySQLAdapterConfig,USERNAME,USERNAME);
        ReflectionTestUtils.setField(mySQLAdapterConfig,PASSWORD,PASSWORD);
        ReflectionTestUtils.setField(mySQLAdapterConfig,DATABASE,DATABASE);
    }

    @Test
    public void connectionFactoryTest() {
        mySQLAdapterConfig.connectionFactory();
    }
}
