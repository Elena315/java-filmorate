package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

@RestController
@Slf4j
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final Map<String, User> users = new TreeMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.debug("Количество пользователей: {}", users.size());
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) throws ValidationException {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());

        if (users.get(user.getId())!= null){
            users.put(user.getEmail(), user);
            log.debug("Пользователь: {} добавлен.", user);
        } else {

        }
        }

        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws ValidationException {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getEmail(), user);
        log.debug("Пользователь: {} обновлён.", user);
        return user;
    }
}
