package com.example.demo.controller;

import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
        Метод получаения всех пользователей.
        Без аннотации @ResponseBody он не работал.
     */
    @GetMapping("/getAll")
    @ResponseBody
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    /*
        Метод регистрации пользователя.
        В данном методе я использовал @RequestParam, так как мне кажется,
        что создавать объект пользователя до того, как он пройдёт все проверки, не совсем рационально
     */
    @PostMapping("/userRegistration")
    public ResponseEntity<String> userRegistration(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String repeatedPassword) {
        return ResponseEntity.ok(userService.userRegistration(name, password, repeatedPassword));
    }

    /*
        Метод входа в систему
     */
    @PostMapping("/userLogin")
    public ResponseEntity<String> userLogin(@ModelAttribute User user){
        return ResponseEntity.ok(userService.userLogin(user));
    }

    /*
        Метод изменения пароля
     */
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@ModelAttribute User user, @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.changePassword(user, newPassword));
    }

}
