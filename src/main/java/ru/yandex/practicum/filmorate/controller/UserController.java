package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>(userService.getAllUsers().values());
        log.debug("Поступил GET запрос. Количество пользователей: {}", usersList.size());
        return usersList;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userService.setUserNameByLogin(user, "Добавлен");
        log.debug("Поступил POST запрос. Пользователь: {} сохранён.", user.getLogin());
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        userService.setUserNameByLogin(user, "обновлен");
        log.debug("Поступил PUT запрос. Пользователь: {} обновлён.", user);
        return userService.updateUser(user);
    }

}