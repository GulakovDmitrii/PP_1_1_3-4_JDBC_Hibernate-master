package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/user";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "Monitor4286";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    public static Connection getConnection()  {


        try{
            Class.forName(DRIVER);
            System.out.println("Подключение к базе данных прошло успешно");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Есть соединение с базой данных");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Нет соединения с базой данных");
        }
        return connection;
    }
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Соединение с базой данных закрыто");
        } catch (SQLException e) {
            System.out.println("Соединение с базой данных не закрыто");
        }
    }
}
