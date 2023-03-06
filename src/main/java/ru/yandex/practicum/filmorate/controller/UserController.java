package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private UserService userService;
    private UserStorage userStorage;

    @Autowired
    public UserController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @GetMapping
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@Valid @PathVariable("id") Long userId) {
        return userStorage.getUserById(userId);
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
        return userStorage.create(user);
    }

    @ResponseBody
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен PUT-запрос к эндпоинту: '/users' на обновление пользователя с ID={}", user.getId());
        return userStorage.update(user);
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
    public User delete(@PathVariable Long id) {
        log.info("Получен DELETE-запрос к эндпоинту: '/users' на удаление пользователя с ID={}", id);
        return userStorage.getUserById(id);
    }
}