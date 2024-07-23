package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionf = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = sessionf.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS user(id BIGINT AUTO_INCREMENT,name VARCHAR(45),lastName VARCHAR(45),age TINYINT,PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Таблица не создана");
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionf.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Таблица не удалена");
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionf.openSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Пользователь не добавлен");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionf.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Пользователь не удален. Пользователь с идентификатором " + id + " не найден");
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionf.openSession()) {
            return session.createQuery("FROM User").getResultList();
        } catch (Exception e) {
            System.out.println("Таблица пуста");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionf.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM user").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Таблица не очищена");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }
}
