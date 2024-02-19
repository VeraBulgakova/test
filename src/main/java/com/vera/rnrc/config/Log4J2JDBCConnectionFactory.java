package com.vera.rnrc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class Log4J2JDBCConnectionFactory {
    private HikariDataSource dataSource;
    private HikariConfig config;

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