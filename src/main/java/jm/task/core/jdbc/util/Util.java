package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://192.168.99.100:5432/postGr";

    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "dostup";


    public static Connection getPostgresqlConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager
                    .getConnection(URL,
                            USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return connection;
    }
}

// реализуйте настройку соеденения с БД

