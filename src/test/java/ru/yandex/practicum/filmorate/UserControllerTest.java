package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    private User user;
    private UserController userController;

    @BeforeEach
    public void beforeEach() {
        UserStorage userStorage = new InMemoryUserStorage();
        userController = new UserController(new UserService(userStorage, null));
        user = User.builder()
                .name("MyName")
                .login("MaxPower")
                .email("1@ya.ru")
                .birthday(LocalDate.of(1980, 12, 23))
                .build();
    }

    // проверка контроллера при "пустой" электронной почте пользователя
    @Test
    void shouldNoAddUserWhenUserEmailIsEmpty() {
        user.setEmail("");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }

    // проверка контроллера, когда электронная почта не содержит символа @
    @Test
    void shouldNoAddUserWhenUserEmailIsNotContainsCommercialAt() {
        user.setEmail("notemail.ru");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }

    // проверка контроллера, когда у пользователя пустой логин
    @Test
    void shouldNoAddUserWhenUserLoginIsEmpty() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }

    // проверка контроллера, когда логин пользователя содержит пробелы
    @Test
    void shouldNoAddUserWhenUserLoginIsContainsSpaces() {
        user.setLogin("Max Power");
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }

    // проверка контроллера, когда дата рождения пользователя в будущем
    @Test
    void shouldAddUserWhenUserBirthdayInFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.create(user));
        assertEquals(0, userController.getUsers().size(), "Список пользователей должен быть пустым");
    }

    // проверка контроллера при корректных атрибутах пользователя
    @Test
    void shouldAddUserWhenAllAttributeCorrect() {
        User user1 = userController.create(user);
        assertEquals(user, user1, "Переданный и полученный пользователь должны совпадать");
        assertEquals(1, userController.getUsers().size(), "В списке должен быть один пользователь");
    }

    // проверка контроллера, когда имя пользователя пустое
    @Test
    void shouldAddUserWhenUserNameIsEmpty() {
        user.setName("");
        User user1 = userController.create(user);
        assertEquals(user1.getName(), user.getLogin(), "Имя и логин пользователя должны совпадать");
        assertEquals(1, userController.getUsers().size(), "В списке должен быть один пользователь");
    }
}