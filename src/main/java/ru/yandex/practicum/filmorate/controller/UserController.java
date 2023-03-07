package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@Valid @PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Long id,
                                      @PathVariable("otherId") Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @ResponseBody
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен POST-запрос к эндпоинту: '/users' на добавление пользователя");
        if (isValidUser(user)) {
            userService.create(user);
        }
        return user;
    }

    @ResponseBody
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен PUT-запрос к эндпоинту: '/users' на обновление пользователя с ID={}", user.getId());
        if (isValidUser(user)) {
            userService.update(user);
        }
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Получен DELETE-запрос к эндпоинту: '/users' на удаление пользователя с ID={}", id);
        userService.delete(id);
    }

    private boolean isValidUser(User user) {
       if (!user.getEmail().contains("@")) {
        throw new ValidationException("Некорректный e-mail пользователя: " + user.getEmail());
    }
        if ((user.getLogin().isEmpty()) || (user.getLogin().contains(" "))) {
        throw new ValidationException("Некорректный логин пользователя: " + user.getLogin());
    }
        if (user.getBirthday().isAfter(LocalDate.now())) {
        throw new ValidationException("Некорректная дата рождения пользователя: " + user.getBirthday());
    }
        return true;
}
}