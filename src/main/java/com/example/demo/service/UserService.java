package com.example.demo.service;

import com.example.demo.DomainService.UserRepository;
import com.example.demo.contsts.TextConsts;
import com.example.demo.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /*
        Метод обработки запроса на получение всех пользователей в системе.
     */
    public List<User> getAll() {
        return repository.getAll();
    }

    /*
        Метод обработки запроса на регистрацию.
        Здесь проверяется значение логина на пустую строку, а также пароли на соответствие.
     */
    public String userRegistration(String name, String password, String repeatedPassword) {
        if (name.isEmpty()) return TextConsts.theUserNameConNotBeEmpty;
        else if (!password.equals(repeatedPassword)) return TextConsts.thePasswordsDontMatch;
        else if (repository.findUserByUsername(name) != null) return TextConsts.alreadyRegistered;
        repository.addUser(new User(name, password));
        return TextConsts.registrationCompleted;
    }

    /*
        Метод обработки запроса на вход в систему.
        Проверяется наличие в системе пользователя с указанным логином.
     */
    public String userLogin(User user) {
        if (repository.hasUser(user)) return TextConsts.loginCompleted;
        return TextConsts.wrongUsernameOrPassword;
    }

    /*
        Метод изменения пароля.
        Проверяется наличие в системе пользователя с указанным логином.
     */
    public String changePassword(User user, String newPassword) {
        User foundedUser = repository.findUserByUsername(user.getName());
        if (foundedUser == null || !foundedUser.getPassword().equals(user.getPassword())) return TextConsts.wrongUsernameOrPassword;
        foundedUser.setPassword(newPassword);
        return TextConsts.theChangesWereMAdeSuccessfully;
    }
}
