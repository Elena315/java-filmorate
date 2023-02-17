package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film {

    private int id;

    @NotBlank(message = "Имя должно содержать буквенные символы.")
    private String name;

    @Length(min = 1, max = 200, message = "Описание фильма не должно превышать 200 символов.")
    private String description;

    @NotNull
    @PastOrPresent(message = "Дата релиза не может быть в будущем. ")
    private LocalDate releaseDate;

    @Min(value = 0, message = "Продолжительность фильма не может быть отрицательной.")
    private long duration;

}
