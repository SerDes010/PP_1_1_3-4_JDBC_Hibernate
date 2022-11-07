package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Transactional
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age TINYINT)";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            Assert.fail("При создании таблицы произошло исключение\n" + e);
        }
    }

    @Transactional
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            Assert.fail("При удалении таблицы произошло исключение\n" + e);
        }
    }

    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        User user_new = new User (name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(user_new);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            Assert.fail("При добавлении в таблицу User произошло исключение\n" + e);
        }
    }

    @Transactional
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e){
            Assert.fail("При удалении из таблицы User произошло исключение\n" + e);
        }
    }

    @Transactional
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            userList = session.createQuery("From User").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            Assert.fail("При запросе таблицы User произошло исключение\n" + e);
        }
        return userList;
    }

    @Transactional
    public void cleanUsersTable() {
        String sql = "TRUNCATE table user";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch(HibernateException e) {
        Assert.fail("При очистки таблицы произошло исключение\n" + e);
        }
    }
}
