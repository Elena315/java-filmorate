package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotations.CorrectReleaseDay;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Valid
public class Film {

    private int id;

    @NotBlank(message = "Имя должно содержать буквенные символы.")
    private String name;

    @Length(min = 1, max = 200, message = "Описание фильма не должно превышать 200 символов.")
    private String description;

    @CorrectReleaseDay(message = "Дата релиза не может быть в будущем.")
    private LocalDate releaseDate;

    @Min(value = 0, message = "Продолжительность фильма не может быть отрицательной.")
    private long duration;

    private Set<Integer> likes;

    public void addLike(Integer id) {
        if (likes == null) {
            likes = new HashSet<>();
        }
        likes.add(id);
    }

    public void deleteLike(Integer id) {
        likes.remove(id);
    }

}
