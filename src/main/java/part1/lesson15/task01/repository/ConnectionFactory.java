package part1.lesson15.task01.repository;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Log4j2
public class ConnectionFactory {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    private static BasicDataSource dataSource;

    private ConnectionFactory() {
    }
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(resourceBundle.getString("db.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String host = resourceBundle.getString("db.host");
        String user = resourceBundle.getString("db.user");
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(host);
            dataSource.setDriverClassName(resourceBundle.getString("db.driver"));
            dataSource.setUsername(user);
            dataSource.setPassword(resourceBundle.getString("db.password"));
        }
        log.trace(String.format("User '%s' connected to db '%s'", user, host));
        return dataSource.getConnection();
    }
}
