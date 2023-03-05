package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private int id;

    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "\\S*", message = "Логин не может содержать пробелы.")
    private String login;
    private String name;

    @NotBlank(message = "email не может быть пустым.")
    @Email (message = "Введенное значение не является адресом электронной почты.")
    private String email;

    @PastOrPresent (message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;

    private Set<Integer> friends;

    public void addFriend(Integer id) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(id);
    }

    public Set<Integer> getFriendsId() {
        if (friends == null) {
            friends = new HashSet<>();
        }
        return friends;
    }

}
