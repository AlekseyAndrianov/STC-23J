package part1.lesson15.task01.repository;

import part1.lesson15.task01.domain.Shop;

import java.sql.*;
import java.util.UUID;

/**
 * private UUID id;
 * private String city;
 * private String address;
 */
public class ShopRepository {

    public void create(Shop shop) {
        String query = "INSERT INTO shop (id, city, address) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, shop.getId());
            statement.setString(2, shop.getCity());
            statement.setString(3, shop.getAddress());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Shop get(Object id) {
        String query = "SELECT * FROM shop WHERE id=?";
        Shop shop = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            shop = Shop.builder()
                    .id((UUID) id)
                    .address(resultSet.getString("address"))
                    .city(resultSet.getString("city"))
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }

    public void update(Shop shop) {
        String query = "UPDATE shop SET city=?, address=? where id=?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, shop.getCity());
            statement.setString(2, shop.getAddress());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Shop shop) {
        String query = "DELETE FROM shop where id=?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, shop.getId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Shop getDistinct() {
        String query = "SELECT * FROM shop LIMIT 1";
        Shop shop = null;

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            shop = Shop.builder()
                    .id((UUID) resultSet.getObject("id"))
                    .address(resultSet.getString("address"))
                    .city(resultSet.getString("city"))
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }
}
