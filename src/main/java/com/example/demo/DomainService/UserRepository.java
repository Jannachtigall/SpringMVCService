package com.example.demo.DomainService;

import com.example.demo.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    /*
        Метод получения всех пользователей.
     */
    public List<User> getAll() {
        return users;
    }

    /*
        Метод поиска пользователя по логину
     */
    public User findUserByUsername(String name) {
        for (User user:
                users) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }

    /*
        Метод добавления нового пользователя.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /*
        Метод определения нахождения пользователя в системе.
     */
    public boolean hasUser(User user) {
        return users.contains(user);
    }

    /*
        Я определил только такой набор методов, так как только такие методы понадобились мне
        для выполнения поставленных задач.
     */
}
