package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void shouldAddUserWhenValidUserData() {
        User user = new User();
        user.setName("Correct Name");
        user.setLogin("Correctlogin");
        user.setEmail("Correct.email@mail.ru");
        user.setBirthday( LocalDate.of(2002, 1, 1));
        User addedUser = service.createUser(user);

        assertNotEquals(0, addedUser.getId());
        assertTrue(service.getAllUsers().contains(addedUser));
    }

    @Test
    void shouldSetUserNameWhenEmptyUserName() {
        User user = new User();
        user.setName("");
        user.setLogin("Correctlogin");
        user.setEmail("Correct.email@mail.ru");
        user.setBirthday( LocalDate.of(2002, 1, 1));
        User addedUser = service.createUser(user);

        assertNotEquals(0, addedUser.getId());
        assertEquals(addedUser.getLogin(), addedUser.getName());
        assertTrue(service.getAllUsers().contains(addedUser));
    }

    @Test
    void shouldThrowExceptionWhenFailedUserLogin() {
        User user = new User();
        user.setLogin(" ");

        ValidationException ex = assertThrows(ValidationException.class, () -> service.createUser(user));
        assertEquals("Ошибка валидации Пользователя", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFailedUserEmail() {
        User user = new User();
        user.setEmail(" ");
        ValidationException ex = assertThrows(ValidationException.class, () -> service.createUser(user));
        assertEquals("Ошибка валидации Пользователя", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFailedUserBirthDate() {
        User user = new User();
        user.setBirthday(LocalDate.now().plusYears(-33));
        ValidationException ex = assertThrows(ValidationException.class, () -> service.createUser(user));
        assertEquals("Ошибка валидации Пользователя", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUpdateFailedUserId() {
        User user = new User();
        user.setId(99);

        ValidationException ex = assertThrows(ValidationException.class, () -> service.updateUser(user));
        assertEquals("Ошибка валидации Пользователя", ex.getMessage());
    }
}