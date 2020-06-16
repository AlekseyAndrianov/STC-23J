package part1.lesson15.task01.repository;

import part1.lesson15.task01.domain.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * private long id;
 * private String fName;
 * private String lName;
 * private String phoneNumber;
 * private String eMail;
 */
public class CustomerRepository {

    private ConnectionFactory connectionFactory;

    public CustomerRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void create(Customer customer) {
        String query = "INSERT INTO customer (fName, lName, phoneNumber, eMail) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getFName());
            statement.setString(2, customer.getLName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEMail());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer get(long id) {
        String query = "SELECT * FROM customer WHERE id=?";
        Customer customer = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            customer = Customer.builder()
                    .id(id)
                    .fName(resultSet.getString("fName"))
                    .lName(resultSet.getString("lName"))
                    .eMail(resultSet.getString("eMail"))
                    .phoneNumber(resultSet.getString("phoneNumber"))
                    .build();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void update(Customer customer) {
        String query = "UPDATE customer SET fName=?, lName=?, eMail=?, phoneNumber=? where id=?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, customer.getFName());
            statement.setString(2, customer.getLName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEMail());
            statement.setLong(5, customer.getId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Customer customer) {
        String query = "DELETE FROM customer where id=?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, customer.getId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getDistinct() {
        String query = "SELECT * FROM customer LIMIT 1";
        Customer customer = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            customer = Customer.builder()
                    .id(resultSet.getLong("id"))
                    .fName(resultSet.getString("fName"))
                    .lName(resultSet.getString("lName"))
                    .eMail(resultSet.getString("eMail"))
                    .phoneNumber(resultSet.getString("phoneNumber"))
                    .build();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
