package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired(required = false)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>(userService.getAllUsers().values());
        log.debug("Поступил GET запрос. Количество пользователей: {}", usersList.size());
        return usersList;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        if (userService.getAllUsers().containsKey(user.getId())) {
            log.warn("Поступил POST запрос. Пользователь {} не добавлен", user.getName());
            throw new ValidationException("Пользователь уже есть в базе");
        }
        userService.setUserNameByLogin(user, "Добавлен");
        log.debug("Поступил POST запрос. Пользователь: {} сохранён.", user.getLogin());
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException{
        if (!userService.getAllUsers().containsKey(user.getId())) {
            log.warn("Поступил PUT запрос. Пользователь {} не найден", user.getName());
            throw new ValidationException("Пользователя нет в базе");
        }
        userService.setUserNameByLogin(user, "обновлен");
        log.debug("Поступил PUT запрос. Пользователь: {} обновлён.", user.getLogin());
        return userService.updateUser(user);
    }

}