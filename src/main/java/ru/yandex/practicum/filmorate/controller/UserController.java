package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>(userService.getAllUsers().values());
        log.debug("Количество пользователей: {}", usersList.size());
        return usersList;
    }
    private void validateUser(@Valid User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        if(user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        if (user.getLogin().contains(" ")){
            throw new ValidationException("Логин не может содержать пробелы");
        }
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (userService.getAllUsers().containsKey(user.getId())) {
            log.warn("Пользователь: {} не сохранён.", user);
            throw new ValidationException("Пользователь уже есть в базе");
        }
        validateUser(user);
        userService.setUserNameByLogin(user, "Добавлен");
        log.debug("Пользователь: {} сохранён.", user);
        userService.createUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!userService.getAllUsers().containsKey(user.getId())) {
            log.warn("Пользователь: {} не обновлён.", user);
            throw new ValidationException("Пользователя нет в базе");
        }
        userService.setUserNameByLogin(user, "Обновлен");
        log.debug("Пользователь: {} обновлён.", user);
        return userService.updateUser(user);
    }

}