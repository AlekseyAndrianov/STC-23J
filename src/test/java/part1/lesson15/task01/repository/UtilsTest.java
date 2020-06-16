package part1.lesson15.task01.repository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.mockito.Mockito.*;

class UtilsTest {

    ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    CustomerRepository customerRepository = mock(CustomerRepository.class);
    ProductRepository productRepository = mock(ProductRepository.class);
    ShopRepository shopRepository = mock(ShopRepository.class);

    Connection connection = mock(Connection.class);
    Statement statement = mock(Statement.class);
    Savepoint savepoint = mock(Savepoint.class);


    @BeforeAll
    static void offLog() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.OFF);
        ctx.updateLoggers();
    }

    @Test
    void renewDB() throws SQLException {

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        doNothing().when(connection).setAutoCommit(anyBoolean());
        when(statement.execute(anyString())).thenReturn(true);
        when(connection.setSavepoint(anyString())).thenReturn(savepoint);
        doNothing().when(connection).rollback(savepoint);
        doNothing().when(connection).commit();

        Utils.initUtils(connectionFactory, customerRepository, shopRepository, productRepository);
        Assertions.assertDoesNotThrow(Utils::renewDB);

        verify(connectionFactory).getConnection();
        verify(connection).createStatement();
        verify(connection).setAutoCommit(anyBoolean());
        verify(statement, atLeastOnce()).execute(anyString());
        verify(connection).setSavepoint(anyString());
        verify(connection).rollback(savepoint);
        verify(connection).commit();
    }

    @Test
    void initDB() {

        doNothing().when(customerRepository).create(any());
        doNothing().when(shopRepository).create(any());
        doNothing().when(productRepository).createAll(any());

        Utils.initUtils(connectionFactory, customerRepository, shopRepository, productRepository);
        Assertions.assertDoesNotThrow(Utils::initDB);

        verify(customerRepository).create(any());
        verify(shopRepository).create(any());
        verify(productRepository).createAll(any());

    }


    @AfterAll
    static void onLog() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.OFF);
        ctx.updateLoggers();
    }

}