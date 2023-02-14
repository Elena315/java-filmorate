package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.controller.UserController;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    @NotNull @Pattern(regexp = "\\S*")
    private String login;
    private String name;
    @NotNull @Email
    private String email;
    @NotNull @PastOrPresent
    private LocalDate birthday;

    public User(String login, String name, String email, LocalDate birthday) {
        this.id = new UserController().getIdGeneration();
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }
}
