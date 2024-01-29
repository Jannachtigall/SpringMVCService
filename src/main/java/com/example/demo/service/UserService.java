package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.contsts.TextConsts;
import com.example.demo.dto.UserInfo;
import com.example.demo.exception.ChangePasswordException;
import com.example.demo.exception.PasswordsDontMatchException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.WrongUsernameOrPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public UserInfo getById(Long id) {
        User user = repository.findById(id);
        return new UserInfo(user.getName());
    }

    /*
        Метод обработки запроса на получение всех пользователей в системе.
     */
    public List<UserInfo> getAll() {
        return repository.getAll()
                .stream().map(userMapper::userToUserInfo)
                .collect(Collectors.toList());
    }

    /*
        Метод обработки запроса на регистрацию.
        Здесь проверяется значение логина на пустую строку, а также пароли на соответствие.
     */
    public String userRegistration(String name, String password, String repeatedPassword) {
        if (name.isEmpty()) return TextConsts.theUserNameConNotBeEmpty;
        else if (!password.equals(repeatedPassword)) {
            throw new PasswordsDontMatchException();
        }
        else if (repository.findUserByUsername(name) != null) {
            throw new UserAlreadyExistsException();
        }
        repository.addUser(new User(name, password));
        return TextConsts.registrationCompleted;
    }

    /*
        Метод обработки запроса на вход в систему.
        Проверяется наличие в системе пользователя с указанным логином.
     */
    public String userLogin(User user) {
        if (repository.hasUser(user)) {
            return TextConsts.loginCompleted;
        } else {
            throw new WrongUsernameOrPasswordException();
        }
    }

    /*
        Метод изменения пароля.
        Проверяется наличие в системе пользователя с указанным логином.
     */
    public String changePassword(User user, String newPassword) {
        User foundedUser = repository.findUserByUsername(user.getName());
        if (foundedUser == null || !foundedUser.getPassword().equals(user.getPassword())) {
            throw new ChangePasswordException();
        }
        repository.updateUserChangePassword(foundedUser.getName(), newPassword);
        return TextConsts.theChangesWereMAdeSuccessfully;
    }
}
