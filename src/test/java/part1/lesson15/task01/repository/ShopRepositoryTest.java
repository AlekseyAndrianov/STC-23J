package part1.lesson15.task01.repository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import part1.lesson15.task01.domain.Shop;
import part1.lesson15.task01.service.DefaultEntityFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ShopRepositoryTest {

    ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    ShopRepository shopRepository = spy(new ShopRepository(connectionFactory));


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
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setObject(anyInt(), any());
        doNothing().when(statement).setString(anyInt(), anyString());
        when(statement.execute()).thenReturn(true);

        assertDoesNotThrow(() -> shopRepository.create(DefaultEntityFactory.getShop()));

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement, atLeastOnce()).setObject(anyInt(), any());
        verify(statement, atLeastOnce()).setString(anyInt(), anyString());
        verify(statement).execute();

    }

    @Test
    void get() throws SQLException {

        UUID uuid = UUID.randomUUID();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setObject(anyInt(), any());
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("address")).thenReturn("address");
        when(resultSet.getString("city")).thenReturn("city");

        Shop shop = shopRepository.get(uuid);

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setObject(anyInt(), any());
        verify(statement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getString("address");
        verify(resultSet).getString("city");

        assertEquals(shop.getId(), uuid);
    }

    @Test
    void getDistinct() throws SQLException {
        UUID uuid = UUID.randomUUID();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getObject("id")).thenReturn(uuid);
        when(resultSet.getString("address")).thenReturn("address");
        when(resultSet.getString("city")).thenReturn("city");

        Shop shop = shopRepository.getDistinct();


        verify(connectionFactory).getConnection();
        verify(connection).createStatement();
        verify(statement).executeQuery(anyString());
        verify(resultSet).next();
        verify(resultSet).getObject("id");
        verify(resultSet).getString("address");
        verify(resultSet).getString("city");

        assertEquals(uuid, shop.getId());
    }
}