package com.example.demo.test;

import com.example.demo.consts.TextConsts;
import com.example.demo.dto.UserInfo;
import com.example.demo.entity.User;
import com.example.demo.exception.ChangePasswordException;
import com.example.demo.exception.PasswordsDontMatchException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.WrongUsernameOrPasswordException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    private List<User> users;

    @BeforeEach
    public void setup() {
        users = new ArrayList<>(getUsers());
    }

    @Test
    public void getAllTest() {
        userService.setUserMapper(new UserMapper());
        Mockito.when(repository.findAll()).thenReturn(users);

        List<UserInfo> result = userService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(users.get(0).getName(), result.get(0).getName());
    }

    private List<User> getUsers() {
        User user1 = new User("Leonel Messi", "Barcelona");
        User user2 = new User("Cristiano Ronaldo", "Me");
        return List.of(user1, user2);
    }

    @Test
    public void userRegistrationTest() {
        int userStartSize = users.size();

        Mockito.when(repository.findUserByName("Sergio Ramos")).thenReturn(null);
        Mockito.when(repository.save(new User("Sergio Ramos", "Real Madrid")))
                .thenReturn(new User("Sergio Ramos", "Real Madrid"));

        Assertions.assertNotNull(userService.userRegistration("Sergio Ramos", "Real Madrid", "Real Madrid"));
        users.add(repository.save(new User("Sergio Ramos", "Real Madrid")));
        Assertions.assertEquals(users.size(), userStartSize+1);
    }

    @Test
    public void wrongRegistrationTest() {
        Assertions.assertThrows(WrongUsernameOrPasswordException.class, () -> userService.userRegistration("", "123", "123"));
        Assertions.assertThrows(PasswordsDontMatchException.class, () -> userService.userRegistration("Mauro Icardi", "wife", "football"));
        Mockito.when(repository.findUserByName("Leonel Messi")).thenReturn(users.get(0));
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.userRegistration("Leonel Messi", "Barcelona", "Barcelona"));
    }

    @Test
    public void userLoginTest() {
        Mockito.when(repository.existsByNameAndPassword("Leonel Messi", "Barcelona")).thenReturn(true);
        Assertions.assertEquals(TextConsts.loginCompleted, userService.userLogin(new User("Leonel Messi", "Barcelona")));
    }

    @Test
    public void wrongLoginTest() {
        Mockito.when(repository.existsByNameAndPassword("Mario Balotelli", "Me")).thenReturn(false);
        Assertions.assertThrows(WrongUsernameOrPasswordException.class, () -> userService.userLogin(new User("Mario Balotelli", "Me")));
    }

    @Test
    public void changePasswordTest() {
        String mu = "Manchester United";
        String cr7 = "Cristiano Ronaldo";

        Mockito.when(repository.findUserByName(cr7)).thenReturn(users.get(1));
        doAnswer((InvocationOnMock invocationOnMock) -> {
            users.get(1).setPassword(mu);
            return null;
        }).when(repository).updatePasswordByName(cr7, mu);

        userService.changePassword(new User(cr7, "Me"), mu);
        Assertions.assertEquals(users.get(1).getPassword(), mu);
    }

    @Test
    public void wrongChangePasswordTest() {
        Mockito.when(repository.findUserByName("Steven Gerard")).thenReturn(null);
        Assertions.assertThrows(ChangePasswordException.class, () -> userService
                .changePassword(new User("Steven Gerard", "Liverpool"), "Aston Willa"));

        Mockito.when(repository.findUserByName("Leonel Messi")).thenReturn(users.get(1));
        Assertions.assertThrows(ChangePasswordException.class, () -> userService
                .changePassword(new User("Leonel Messi", "PSG"), "Inter Miami"));
    }

}
