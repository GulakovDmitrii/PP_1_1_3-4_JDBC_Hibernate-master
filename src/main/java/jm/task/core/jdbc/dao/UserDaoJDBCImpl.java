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
                System.out.println("Get connection");
                connection.createStatement().execute("CREATE TABLE IF NOT EXISTS user" +
                        "(id BIGINT AUTO_INCREMENT," +
                        "name VARCHAR(45)," +
                        "lastName VARCHAR(45)," +
                        "age TINYINT," +
                        "PRIMARY KEY (id))");
            } catch (Exception e) {
                System.out.println("Can't create table");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Get connection");
                connection.createStatement().execute("DROP TABLE IF EXISTS user");
            } catch (Exception e) {
                System.out.println("Can't drop table");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Get connection");
                connection.createStatement().execute("INSERT INTO `user` (name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
            } catch (Exception e) {
                System.out.println("Can't save user");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Get connection");
                connection.createStatement().execute("DELETE FROM user WHERE id = " + id);
            } catch (Exception e) {
                System.out.println("Can't remove user");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }

    }

    public List<User> getAllUsers() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Get connection");
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
                    System.out.println("Table does not exist");
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Can't get all users");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void cleanUsersTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = Util.getConnection()) {
                System.out.println("Get connection");
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "user", null);

                if (tables.next()) {
                    connection.createStatement().execute("DELETE FROM user");
                } else {
                    System.out.println("Table 'user' does not exist");
                }
            } catch (Exception e) {
                System.out.println("Can't clean table");
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
    }
}
