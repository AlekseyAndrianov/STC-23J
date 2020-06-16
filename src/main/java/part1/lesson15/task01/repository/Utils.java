package part1.lesson15.task01.repository;

import lombok.extern.log4j.Log4j2;
import part1.lesson15.task01.domain.Customer;
import part1.lesson15.task01.domain.Product;
import part1.lesson15.task01.domain.Shop;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
public class Utils {

    private static CustomerRepository customerRepository;
    private static ConnectionFactory connectionFactory;
    private static ShopRepository shopRepository;
    private static ProductRepository productRepository;


    public static void initUtils(ConnectionFactory connFactory, CustomerRepository customerRep, ShopRepository shopRep, ProductRepository productRep){
        connectionFactory = connFactory;
        customerRepository = customerRep;
        shopRepository = shopRep;
        productRepository = productRep;

    }

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
    private static final String createLogTable = "create table if not exists APP_LOGS(\n" +
            "    LOG_ID varchar(100) primary key,\n" +
            "    ENTRY_DATE timestamp,\n" +
            "    LOGGER varchar(100),\n" +
            "    LOG_LEVEL varchar(100),\n" +
            "    MESSAGE TEXT,\n" +
            "    EXCEPTION TEXT\n" +
            ");";


    public static void renewDB() {
        String dropTable = "DROP TABLE IF EXISTS %s;";

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(String.format(dropTable, "customer"));
            log.debug("Drop table 'customer'");
            statement.execute(String.format(dropTable, "deal"));
            log.debug("Drop table 'deal'");
            statement.execute(String.format(dropTable, "product"));
            log.debug("Drop table 'product'");
            statement.execute(String.format(dropTable, "shop"));
            log.debug("Drop table 'shop'");
            Savepoint savepoint = connection.setSavepoint("sp1");
            log.info("Dropped all tables");
            boolean isTest = true;
            try {
                statement.execute(createCustomerTable);
                statement.execute(createDealTable);
                statement.execute(createProductTable);
                statement.execute(createShopTable);
                statement.execute(createLogTable);
                log.debug("Create 5 tables");
                if (isTest) {
                    throw new SQLException();
                }
            } catch (SQLException ex) {
                log.debug("Rollback", ex);
                connection.rollback(savepoint);
                statement.execute(createCustomerTable);
                statement.execute(createDealTable);
                statement.execute(createProductTable);
                statement.execute(createShopTable);
                statement.execute(createLogTable);
                log.debug("Create 5 tables");

            }
            log.info("Create 5 tables");
            connection.commit();
            log.debug("Commit connection");
            initDB();
            log.info("DataBase initialized.");
        } catch (SQLException e) {
            log.debug("Problem with table creation", e);
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

        customerRepository.create(customer);
        log.debug("Create customer, put to db");
        shopRepository.create(shop);
        log.debug("Create shop, put to db");

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

        productRepository.createAll(products);
        log.debug("Create products, put to db");
    }

}
