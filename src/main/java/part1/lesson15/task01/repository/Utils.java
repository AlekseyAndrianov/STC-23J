package part1.lesson15.task01.repository;

import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.domain.Shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class Utils {

    static final ResourceBundle resourceBundle = ResourceBundle.getBundle("application");

    private static final String createCustomerTable = "CREATE TABLE customer (" +
            "id serial, " +
            "fName varchar(30), " +
            "lName varchar(30), " +
            "phoneNumber varchar(30), " +
            "eMail varchar(30))";
    private static final String createDealTable = "CREATE TABLE deal (" +
            "id serial, " +
            "customer bigint, " +
            "product uuid, " +
            "shop uuid)";
    private static final String createProductTable = "CREATE TABLE product (" +
            "article uuid, " +
            "price double precision, " +
            "type varchar(30), " +
            "shop uuid)";
    private static final String createShopTable = "CREATE TABLE shop (" +
            "id uuid, " +
            "city varchar(30), " +
            "address varchar(30))";

    public static void renewDB() {
        String dropTable = "DROP TABLE IF EXISTS %s;";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(String.format(dropTable, "customer"));
            statement.execute(String.format(dropTable, "deal"));
            statement.execute(String.format(dropTable, "product"));
            statement.execute(String.format(dropTable, "shop"));
            Savepoint savepoint = connection.setSavepoint("sp1");

            boolean isTest = true;
            try {
                statement.execute(createCustomerTable);
                statement.execute(createDealTable);
                statement.execute(createProductTable);
                statement.execute(createShopTable);
                if (isTest)
                    throw new SQLException();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback(savepoint);
                statement.execute(createCustomerTable);
                statement.execute(createDealTable);
                statement.execute(createProductTable);
                statement.execute(createShopTable);
            }
            connection.commit();
            initDB();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }


    public static void initDB() {

        Shop shop = Shop.builder()
                .id(UUID.randomUUID())
                .city("Иннополис")
                .address("Андрианова 7")
                .build();
        Customer customer = Customer.builder()
                .id(10L)
                .phoneNumber("89260121000")
                .eMail("andrianov@innopolis.ru")
                .fName("Алексей")
                .lName("Андрианов")
                .build();

        CustomerRepository customerRepository = new CustomerRepository();
        customerRepository.create(customer);

        ShopRepository shopRepository = new ShopRepository();
        shopRepository.create(shop);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Product product = Product.builder()
                    .article(UUID.randomUUID())
                    .price(Math.random() * 1000)
                    .type("Some Type")
                    .shop(shop)
                    .build();
            products.add(product);
        }
        ProductRepository productRepository = new ProductRepository();
        productRepository.createAll(products);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                resourceBundle.getString("db.host"),
                resourceBundle.getString("db.user"),
                resourceBundle.getString("db.password"));
    }
}
