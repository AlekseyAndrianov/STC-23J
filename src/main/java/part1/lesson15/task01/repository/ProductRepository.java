package part1.lesson15.task01.repository;

import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.domain.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ProductRepository {

    private ShopRepository shopRepository;
    private ConnectionFactory connectionFactory;

    public ProductRepository(ShopRepository shopRepository, ConnectionFactory connectionFactory) {
        this.shopRepository = shopRepository;
        this.connectionFactory = connectionFactory;
    }

    public void create(Product product) {
        String query = "INSERT INTO product (article, price, type, shop) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, product.getArticle());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getType());
            statement.setObject(4, product.getShop().getId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAll(List<Product> products) {
        String query = "INSERT INTO product (article, price, type, shop) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (Product product : products) {
                statement.setObject(1, product.getArticle());
                statement.setDouble(2, product.getPrice());
                statement.setString(3, product.getType());
                statement.setObject(4, product.getShop().getId());
                statement.addBatch();
                statement.clearParameters();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product get(UUID article) {
        String query = "SELECT * FROM product WHERE article=?";

        Product product = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, article);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            product = Product.builder()
                    .article(article)
                    .price(resultSet.getDouble(2))
                    .type(resultSet.getString(3))
                    .shop(shopRepository.get(resultSet.getObject(4)))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
//
//    public void update(Product product) {
//        String query = "UPDATE product SET price=?, type=? where article=?";
//
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setDouble(1, product.getPrice());
//            statement.setString(2, product.getType());
//            statement.setObject(3, product.getArticle());
//            statement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void update(Product product, Shop shop) {
//        String query = "UPDATE product SET price=?, type=?, shop=? where article=?";
//
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setDouble(1, product.getPrice());
//            statement.setString(2, product.getType());
//            statement.setObject(3, shop.getId());
//            statement.setObject(4, product.getArticle());
//            statement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void delete(Product product) {
//        String query = "DELETE FROM product where article=?";
//
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setObject(1, product.getArticle());
//            statement.execute();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
