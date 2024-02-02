package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.consts.TextConsts;
import com.example.demo.dto.UserInfo;
import com.example.demo.exception.ChangePasswordException;
import com.example.demo.exception.PasswordsDontMatchException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.WrongUsernameOrPasswordException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Setter
    private UserMapper userMapper;

    /*
        Метод обработки запроса на получение всех пользователей в системе.
     */
    public List<UserInfo> getAll() {
        return ((List<User>) repository.findAll())
                .stream().map(userMapper::userToUserInfo).collect(Collectors.toList());
    }

    /*
        Метод обработки запроса на регистрацию.
        Здесь проверяется значение логина на пустую строку, а также пароли на соответствие.
     */
    public String userRegistration(String name, String password, String repeatedPassword) {
        if (name.isEmpty()) {
            throw new WrongUsernameOrPasswordException();
        }
        else if (!password.equals(repeatedPassword)) {
            throw new PasswordsDontMatchException();
        }
        else if (repository.findUserByName(name) != null) {
            throw new UserAlreadyExistsException();
        }
        repository.save(new User(name, password));
        return TextConsts.registrationCompleted;
    }

    /*
        Метод обработки запроса на вход в систему.
        Проверяется наличие в системе пользователя с указанным логином.
     */
    public String userLogin(User user) {
        if (repository.existsByNameAndPassword(user.getName(), user.getPassword())) {
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
        User foundedUser = repository.findUserByName(user.getName());
        if (foundedUser == null || !foundedUser.getPassword().equals(user.getPassword())) {
            throw new ChangePasswordException();
        }
        repository.updatePasswordByName(foundedUser.getName(), newPassword);
        return TextConsts.theChangesWereMAdeSuccessfully;
    }
}
