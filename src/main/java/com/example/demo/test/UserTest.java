package com.example.demo.test;

import com.example.demo.DomainService.UserRepository;
import com.example.demo.contsts.TextConsts;
import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserTest {

    private static final UserRepository repository = new UserRepository();
    private static final UserService userService = new UserService(repository);

    @BeforeAll
    public static void createServices(){
        repository.addUser(new User("Sergio Aguero", "123"));
        repository.addUser(new User("Cristiano Ronaldo", "345"));
        repository.addUser(new User("Leonel Messi", "333"));
    }

    @Test
    public void repositoryFindByUsernameTest() {
        Assertions.assertNotNull(repository.findUserByUsername("Sergio Aguero"));
    }

    @Test
    public void repositoryAddUserTest() {
        int listSize = repository.getAll().size();
        repository.addUser(new User("Erling Haaland", "432"));
        Assertions.assertEquals(repository.getAll().size(), listSize+1);
    }

    @Test
    public void getAllTest() {
        Assertions.assertArrayEquals(new List[]{repository.getAll()}, new List[]{userService.getAll()});
    }

    @Test
    public void firstRegistrationTest() {
        userService.userRegistration("Andre Onana", "123", "123");

        Assertions.assertNotNull(repository.findUserByUsername("Andre Onana"));
    }

    @Test
    public void secondRegistrationTest() {
        int listSize = repository.getAll().size();
        userService.userRegistration("", "123", "123");
        userService.userRegistration("Mike Maignan", "123", "321");
        userService.userRegistration("Leonel Messi", "123", "123");

        Assertions.assertEquals(repository.getAll().size(), listSize);
    }

    @Test
    public void firstLoginTest() {
        Assertions.assertEquals(
                userService.userLogin(new User("Leonel Messi", "333")),
                TextConsts.loginCompleted
        );

    }

    @Test
    public void secondLoginTest() {
        Assertions.assertEquals(
                userService.userLogin(new User("Leonel Messi", "123")),
                TextConsts.wrongUsernameOrPassword
        );

    }

    @Test
    public void firstChangePasswordTest() {
        Assertions.assertEquals(
                userService.changePassword(new User("Leonel Messi", "333"), "321"),
                TextConsts.theChangesWereMAdeSuccessfully
        );
    }

    @Test
    public void secondChangePasswordTest() {
        Assertions.assertEquals(
                userService.changePassword(new User("Cristiano Ronaldo", "543"), "321"),
                TextConsts.wrongUsernameOrPassword
        );
    }

    @Test
    public void thirdChangePasswordTest() {
        Assertions.assertEquals(
                userService.changePassword(new User("Kylian Mbappe", "123"), "321"),
                TextConsts.wrongUsernameOrPassword
        );
    }
}
