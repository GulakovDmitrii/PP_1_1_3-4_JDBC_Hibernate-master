package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Есть соединение с базой данных");
                connection.createStatement().execute("CREATE TABLE IF NOT EXISTS user" +
                        "(id BIGINT AUTO_INCREMENT," +
                        "name VARCHAR(45)," +
                        "lastName VARCHAR(45)," +
                        "age TINYINT," +
                        "PRIMARY KEY (id))");
            } catch (Exception e) {
                System.out.println("Таблица не создана");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных");
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Есть соединение с базой данных");
                connection.createStatement().execute("DROP TABLE IF EXISTS user");
            } catch (Exception e) {
                System.out.println("Не удалось удалить таблицу");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных");
            System.out.println(e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Есть соединение с базой данных");
                connection.createStatement().execute("INSERT INTO `user` (name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
                System.out.println("User с именем - " + name + " добавлен в базу данных");
            } catch (Exception e) {
                System.out.println("Не удалось добавить пользователя");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных");
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Есть соединение с базой данных");
                connection.createStatement().execute("DELETE FROM user WHERE id = " + id);
            } catch (Exception e) {
                System.out.println("Не удалось удалить пользователя с id = " + id);
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных");
            System.out.println(e.getMessage());
        }

    }

    public List<User> getAllUsers() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Есть соединение с базой данных");
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "user", null);
                if (tables.next()) {
                    ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM user");
                    List<User> userList = new ArrayList<>();
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getLong("id"));
                        user.setName(resultSet.getString("name"));
                        user.setLastName(resultSet.getString("lastName"));
                        user.setAge((byte) resultSet.getInt("age"));
                        userList.add(user);
                    }
                    return userList;
                } else {
                    System.out.println("Таблица 'user' не существует");
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Не удалось получить список пользователей");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void cleanUsersTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Есть соединение с базой данных. Очистка таблицы 'user' началась");
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "user", null);

                if (tables.next()) {
                    connection.createStatement().execute("DELETE FROM user");
                } else {
                    System.out.println("Таблица 'user' не существует. Ничего не произошло");
                }
            } catch (Exception e) {
                System.out.println("Не удалось очистить таблицу 'user'");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Не удалось подключиться к базе данных");
            System.out.println(e.getMessage());
        }
    }
}
