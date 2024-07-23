package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/user";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "root";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private Util() {

    }

    private static SessionFactory sessionFactory;

    private static Connection connection = null;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Соединение с базой данных прошло успешно");
            } catch (Exception e) {
                System.out.println("Нет соединения с базой данных");
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }

    public static Connection getConnection() {


        try {
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
