package ru.rnrc.re2.partnercheck.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class Log4J2JDBCConnectionFactory {
    private static HikariDataSource dataSource;

    public Log4J2JDBCConnectionFactory(@Value("${spring.datasource.url}") String dbUrl,
                                       @Value("${spring.datasource.username}") String dbUsername,
                                       @Value("${spring.datasource.password}") String dbPassword,
                                       @Value("${spring.datasource.driver-class-name}") String dbDriverClassName) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setDriverClassName(dbDriverClassName);
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized.");
        }
        return dataSource.getConnection();
    }
}