package part1.lesson15.task01.repository;

import org.junit.jupiter.api.Test;
import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Deal;
import part1.lesson15.task01.service.DefaultEntityFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DealRepositoryTest {

    private ProductRepository productRepository = mock(ProductRepository.class);
    private ShopRepository shopRepository = mock(ShopRepository.class);
    private ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    private CustomerRepository customerRepository = mock(CustomerRepository.class);

    private DealRepository dealRepository = spy(new DealRepository(productRepository, shopRepository, connectionFactory, customerRepository));

    private Connection connection = mock(Connection.class);
    private PreparedStatement statement = mock(PreparedStatement.class);
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    void create() throws SQLException {

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setLong(anyInt(), anyLong());
        doNothing().when(statement).setObject(anyInt(), any());
        when(statement.execute()).thenReturn(true);

        Deal deal = DefaultEntityFactory.getDeal();
        dealRepository.create(deal);

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement, atMost(2)).setObject(anyInt(), any());
        verify(statement).execute();

        assertNotNull(deal);
    }

    @Test
    void get() throws SQLException {

        Customer customer = DefaultEntityFactory.getCustomer();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        doNothing().when(statement).setLong(anyInt(), anyLong());
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(any())).thenReturn(1L);
        when(resultSet.getObject(anyInt())).thenReturn(new Object());
        when(customerRepository.get(anyLong())).thenReturn(customer);
        when(shopRepository.get(any())).thenReturn(DefaultEntityFactory.getShop());


        Deal deal = dealRepository.get(customer);

        verify(connectionFactory).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setLong(anyInt(), anyLong());
        verify(statement).executeQuery();

        verify(resultSet).next();
        verify(resultSet, atMost(2)).getLong(any());
        verify(resultSet).getObject(anyInt());
        verify(customerRepository).get(anyLong());
        verify(shopRepository).get(any());
        assertNotNull(deal);
    }

    @Test
    void getAllDeals() throws SQLException {
        UUID uuid = UUID.randomUUID();
        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
        when(resultSet.getObject(anyInt())).thenReturn(uuid);
        when(customerRepository.get(1L)).thenReturn(DefaultEntityFactory.getCustomer());
        when(productRepository.get(uuid)).thenReturn(DefaultEntityFactory.getProduct());
        when(shopRepository.get(uuid)).thenReturn(DefaultEntityFactory.getShop());

        List<Deal> deals = dealRepository.getAllDeals();

        verify(connectionFactory).getConnection();
        verify(connection).createStatement();
        verify(statement).executeQuery(anyString());
        verify(resultSet, atLeastOnce()).next();
        verify(resultSet, atLeastOnce()).getLong(anyInt());
        verify(resultSet, atLeastOnce()).getObject(3);
        verify(resultSet, atLeastOnce()).getObject(4);
        verify(customerRepository, atLeastOnce()).get(1L);
        verify(productRepository, atLeastOnce()).get(uuid);
        verify(shopRepository, atLeastOnce()).get(uuid);

        assertEquals(deals.size(), 2);
    }
}