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
    Connection connection = Util.getConnection();



    public void createUsersTable() {
        try {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS user(id BIGINT AUTO_INCREMENT,name VARCHAR(45),lastName VARCHAR(45),age TINYINT,PRIMARY KEY (id))");
        } catch (Exception e) {
            System.out.println("Таблица не создана");
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            connection.createStatement().execute("DROP TABLE IF EXISTS user");
        } catch (Exception e) {
            System.out.println("Таблица не удалена");
            System.out.println(e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection.createStatement().execute("INSERT INTO user(name, lastName, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
            System.out.println("User с именем - " + name + " " + lastName + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Пользователь не добавлен");
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try {
            connection.createStatement().execute("DELETE FROM user WHERE id = " + id);
        } catch (Exception e) {
            System.out.println("Пользователь не удален. Пользователь с идентификатором " + id + " не найден");
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        try {
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
        return null;
    }

    public void cleanUsersTable() {
        try{
            System.out.println("Очистка таблицы 'user' началась");
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "user", null);

            if (tables.next()) {
                connection.createStatement().execute("DELETE FROM user");
                System.out.println("Таблица 'user' очищена");
            } else {
                System.out.println("Таблица 'user' не существует. Ничего не произошло");
            }
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу 'user'");
            System.out.println(e.getMessage());
        }
    }
}
