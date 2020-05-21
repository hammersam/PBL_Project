package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public Connection connection() {

        try {
            /**
             * Database Information here
             */
            String url = ConstantValue.jdbcUrl;
            String user = ConstantValue.user;
            String password = ConstantValue.password;

            Class.forName(ConstantValue.driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;

        } catch (Exception e) {

        }

        return null;
    }
}
