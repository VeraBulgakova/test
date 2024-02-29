package ru.rnrc.re2.partnercheck.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class Log4J2JDBCConnectionFactory {
    private HikariDataSource dataSource;
    private HikariConfig config;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
private static interface Singleton {
        final Log4J2JDBCConnectionFactory INSTANCE = new Log4J2JDBCConnectionFactory();
    }

    private Log4J2JDBCConnectionFactory() {
        config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setMaximumPoolSize(1);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }
}