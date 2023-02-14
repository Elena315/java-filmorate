package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    @PositiveOrZero(message = "id can not be negative")
    private int id;
    @NotBlank @Pattern(regexp = "\\S*")
    private String login;
    private String name;
    @NotNull @NotBlank @Email
    private String email;
    @NotNull @PastOrPresent
    private LocalDate birthday;

}
