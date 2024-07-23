package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS user(id BIGINT AUTO_INCREMENT,name VARCHAR(45),lastName VARCHAR(45),age TINYINT,PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println("Таблица не создана");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            System.out.println("Таблица не удалена");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO user(name, lastName, age) VALUES (?, ?, ?)");

            try {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
                System.out.println("User с именем - " + name + " " + lastName + " добавлен в базу данных");
            } finally {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.out.println("Не удалось закрыть подготовленное выражение");
                    System.out.println(ex.getMessage());
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Транзакция отменена");
                System.out.println(ex.getMessage());
            }
            System.out.println("Пользователь не добавлен");
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Не удалось сбросить автоматическое подтверждение транзакции");
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            try {
                statement.setLong(1, id);
                statement.executeUpdate();
            } finally {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.out.println("Не удалось закрыть подготовленное выражение");
                    System.out.println(ex.getMessage());
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Транзакция отменена");
                System.out.println(ex.getMessage());
            }
            System.out.println("Пользователь не удален. Пользователь с идентификатором " + id + " не найден");
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Не удалось сбросить автоматическое подтверждение транзакции");
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
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
        } catch (SQLException e) {
            System.out.println("Не удалось получить список пользователей");
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        try {
            connection.setAutoCommit(false);
            System.out.println("Очистка таблицы 'user' началась");
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "user", null);

            if (tables.next()) {
                connection.createStatement().execute("DELETE FROM user");
                System.out.println("Таблица 'user' очищена");
            } else {
                System.out.println("Таблица 'user' не существует. Ничего не произошло");
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Транзакция отменена");
                System.out.println(ex.getMessage());
            }
            System.out.println("Не удалось очистить таблицу 'user'");
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Не удалось сбросить автоматическое подтверждение транзакции");
                System.out.println(ex.getMessage());
            }
        }
    }
}
