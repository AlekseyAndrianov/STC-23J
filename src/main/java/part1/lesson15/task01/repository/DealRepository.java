package part1.lesson15.task01.repository;

import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Deal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * private Long id;
 * private Customer customer;
 * private Product product;
 * private Shop shop;
 */
public class DealRepository {

    private ProductRepository productRepository;
    private ShopRepository shopRepository;
    private ConnectionFactory connectionFactory;
    private CustomerRepository customerRepository;

    public DealRepository(ProductRepository productRepository, ShopRepository shopRepository, ConnectionFactory connectionFactory, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.connectionFactory = connectionFactory;
        this.customerRepository = customerRepository;
    }

    public void create(Deal deal) {
        String query = "INSERT INTO deal (customer, product, shop) VALUES (?, ?, ?)";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, deal.getCustomer().getId());
            statement.setObject(2, deal.getProduct().getArticle());
            statement.setObject(3, deal.getShop().getId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Deal get(Customer customer) {
        String query = "SELECT * FROM deal WHERE customer=?";
        Deal deal = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, customer.getId());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            deal = Deal.builder()
                    .id(resultSet.getLong(1))
                    .customer(customerRepository.get(resultSet.getLong(2)))
                    .shop(shopRepository.get(resultSet.getObject(3)))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deal;
    }
//
//    public void update(Deal deal) {
//        String query = "UPDATE deal SET customer=?, product=?, shop=? where id=?";
//
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setLong(1, deal.getCustomer().getId());
//            statement.setObject(2, deal.getProduct().getArticle());
//            statement.setObject(3, deal.getShop().getId());
//            statement.setLong(4, deal.getId());
//            statement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void delete(Deal deal) {
//        String query = "DELETE FROM deal where id=?";
//
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setLong(1, deal.getId());
//            statement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public List<Deal> getAllDeals() {
        String query = "SELECT * FROM deal";
        List<Deal> deals = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            Deal deal;
            while (resultSet.next()) {
                deal = Deal.builder()
                        .id(resultSet.getLong(1))
                        .customer(customerRepository.get(resultSet.getLong(2)))
                        .product(productRepository.get((UUID) resultSet.getObject(3)))
                        .shop(shopRepository.get(resultSet.getObject(4)))
                        .build();
                deals.add(deal);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deals;
    }
}
