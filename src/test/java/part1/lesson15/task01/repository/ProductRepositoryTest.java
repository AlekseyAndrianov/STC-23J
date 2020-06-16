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
import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.service.DefaultEntityFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    ShopRepository shopRepository = mock(ShopRepository.class);
    ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    ProductRepository productRepository = spy(new ProductRepository(shopRepository, connectionFactory));

    Connection connection = mock(Connection.class);
    PreparedStatement statement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    @BeforeAll
    static void offLog() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.OFF);
        ctx.updateLoggers();
    }

    @Test
    void create() throws SQLException {
        Product product = DefaultEntityFactory.getProduct();
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setObject(anyInt(), any());
        doNothing().when(statement).setDouble(anyInt(), anyDouble());
        doNothing().when(statement).setString(anyInt(), anyString());
        when(statement.execute()).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> productRepository.create(product));

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement, atLeastOnce()).setObject(anyInt(), any());
        verify(statement).setDouble(anyInt(), anyDouble());
        verify(statement).setString(anyInt(), anyString());
        verify(statement).execute();

    }

    @Test
    void createAll() throws SQLException {

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(DefaultEntityFactory.getProduct());
        }

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setObject(anyInt(), any());
        doNothing().when(statement).setDouble(anyInt(), anyDouble());
        doNothing().when(statement).setString(anyInt(), anyString());
        doNothing().when(statement).setObject(anyInt(), any());
        doNothing().when(statement).addBatch();
        doNothing().when(statement).clearParameters();
        when(statement.executeBatch()).thenReturn(new int[10]);

        Assertions.assertDoesNotThrow(() -> productRepository.createAll(products));

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement, atLeastOnce()).setObject(anyInt(), any());
        verify(statement, atLeastOnce()).setDouble(anyInt(), anyDouble());
        verify(statement, atLeastOnce()).setString(anyInt(), anyString());
        verify(statement, atLeastOnce()).setObject(anyInt(), any());
        verify(statement, atLeastOnce()).addBatch();
        verify(statement, atLeastOnce()).clearParameters();
        verify(statement).executeBatch();

    }

    @Test
    void get() throws SQLException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setObject(anyInt(), any());
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getDouble(2)).thenReturn(2.0);
        when(resultSet.getString(3)).thenReturn("any");
        when(resultSet.getObject(4)).thenReturn(UUID.randomUUID());
        when(shopRepository.get(any())).thenReturn(DefaultEntityFactory.getShop());

        UUID uuid = UUID.randomUUID();
        Product product = productRepository.get(uuid);

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setObject(anyInt(), any());
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getDouble(2);
        verify(resultSet).getString(3);
        verify(resultSet).getObject(4);
        verify(shopRepository).get(any());

        Assertions.assertNotNull(product);

    }

    @AfterAll
    static void onLog() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.ALL);
        ctx.updateLoggers();
    }

}