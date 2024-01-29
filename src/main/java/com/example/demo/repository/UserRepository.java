package com.example.demo.repository;

import com.example.demo.config.SessionFactoryConfig;
import com.example.demo.entity.User;
import com.example.demo.exception.ChangePasswordException;
import com.example.demo.exception.WrongUsernameOrPasswordException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final SessionFactoryConfig sessionFactoryConfig;

    @Autowired
    public UserRepository(SessionFactoryConfig sessionFactoryConfig) {
        this.sessionFactoryConfig = sessionFactoryConfig;
    }

    public User findById(Long id){
        try(Session session = sessionFactoryConfig.getSession()) {
            return session.get(User.class, id);
        } catch (HibernateException e) {
            throw new WrongUsernameOrPasswordException();
        }
    }

    /*
        Метод получения всех пользователей.
     */
    public List<User> getAll() {
        try(Session session = sessionFactoryConfig.getSession()) {
            String hql = "FROM User";
            Query<User> query = session.createQuery(hql, User.class);
            return query.getResultList();
        } catch (HibernateException e) {
            return new ArrayList<>();
        }
    }

    /*
        Метод поиска пользователя по логину
     */
    public User findUserByUsername(String name) {
        try (Session session = sessionFactoryConfig.getSession()) {
            String hql = "FROM User WHERE name = :userName";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("userName", name);
            return query.uniqueResult();
        } catch (HibernateException e) {
            throw new WrongUsernameOrPasswordException();
        }
    }

    /*
        Метод добавления нового пользователя.
     */
    public void addUser(User user) {
        try (Session session = sessionFactoryConfig.getSession()){
            session.save(user);
        } catch (HibernateException e) {
            throw new WrongUsernameOrPasswordException();
        }
    }

    /*
        Метод определения нахождения пользователя в системе.
     */
    public boolean hasUser(User user) {
        try (Session session = sessionFactoryConfig.getSession()){
            String hql = "SELECT COUNT(*) FROM User WHERE name = :userName AND password = :userPassword";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("userName", user.getName());
            query.setParameter("userPassword", user.getPassword());

            Long count = query.uniqueResult();

            return count != null && count > 0;
        }
    }

    public void updateUserChangePassword(String name, String newPassword) {
        try (Session session = sessionFactoryConfig.getSession()) {
            Transaction transactional = session.beginTransaction();

            String hql = "UPDATE User SET password = :newPassword WHERE name = :userName";
            Query<User> query = session.createQuery(hql);
            query.setParameter("newPassword", newPassword);
            query.setParameter("userName", name);
            query.executeUpdate();

            transactional.commit();
        } catch (HibernateException e) {
            throw new ChangePasswordException();
        }
    }

    /*
        Я определил только такой набор методов, так как только такие методы понадобились мне
        для выполнения поставленных задач.
     */
}
