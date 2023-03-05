package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

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

}
