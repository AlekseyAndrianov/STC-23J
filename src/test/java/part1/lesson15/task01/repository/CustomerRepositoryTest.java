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
import part1.lesson15.task01.domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomerRepositoryTest {

    private Connection connection = mock(Connection.class);
    private PreparedStatement statement = mock(PreparedStatement.class);
    private ResultSet resultSet = mock(ResultSet.class);
    private ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    private CustomerRepository customerRepository = spy(new CustomerRepository(connectionFactory));

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
        doNothing().when(statement).setString(anyInt(), anyString());
        when(statement.executeUpdate()).thenReturn(1);

        Customer customer1 = Customer.builder()
                .id(1L)
                .fName("fName")
                .lName("lName")
                .eMail("eMail")
                .phoneNumber("phNumber")
                .build();
        Assertions.assertDoesNotThrow(() -> customerRepository.create(customer1));
        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement, atLeastOnce()).setString(anyInt(), anyString());
        verify(statement).executeUpdate();
    }

    @Test
    void get() throws SQLException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setLong(anyInt(), anyLong());
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(anyString())).thenReturn("mock");

        Customer customer = customerRepository.get(1L);

        verify(connectionFactory, atLeastOnce()).getConnection();
        verify(connection, atLeastOnce()).prepareStatement(anyString());
        verify(statement, atLeastOnce()).setLong(anyInt(), anyLong());
        verify(statement, atLeastOnce()).executeQuery();
        verify(resultSet, atLeastOnce()).next();
        verify(resultSet, atLeastOnce()).getString(anyString());

        Assertions.assertEquals(customer.getEMail(), "mock");
        Assertions.assertEquals(customer.getId(), 1L);
    }

    @Test
    void update() throws SQLException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setString(anyInt(), anyString());
        doNothing().when(statement).setLong(anyInt(), anyLong());
        when(statement.execute()).thenReturn(true);
        Customer customer = Customer.builder()
                .id(1L)
                .fName("fName")
                .lName("lName")
                .eMail("eMail")
                .phoneNumber("phNumber")
                .build();
        customerRepository.update(customer);
        verify(connectionFactory, atLeastOnce()).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement, atLeast(3)).setString(anyInt(), anyString());
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).execute();

    }

    @Test
    void delete() throws SQLException {
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setLong(anyInt(), anyLong());
        when(statement.execute()).thenReturn(true);

        Customer customer = Customer.builder()
                .id(1L)
                .fName("fName")
                .lName("lName")
                .eMail("eMail")
                .phoneNumber("phNumber")
                .build();
        customerRepository.delete(customer);

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).execute();
    }

    @Test
    void getDistinct() throws SQLException {

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("fName")).thenReturn("fName");
        when(resultSet.getString("lName")).thenReturn("lName");
        when(resultSet.getString("eMail")).thenReturn("eMail");
        when(resultSet.getString("phoneNumber")).thenReturn("phoneNumber");

        Customer customer = customerRepository.getDistinct();

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).executeQuery();
        verify(resultSet).next();

        verify(resultSet).getLong("id");
        verify(resultSet).getString("fName");
        verify(resultSet).getString("lName");
        verify(resultSet).getString("eMail");
        verify(resultSet).getString("phoneNumber");

        Assertions.assertEquals(customer.getEMail(), "eMail");
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