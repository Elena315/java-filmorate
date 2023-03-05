package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    private Set<Integer> likes;

    public Film(String name, String description, LocalDate releaseDate, Integer duration, Set<Integer> likes) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = Objects.requireNonNullElseGet(likes, HashSet::new);
    }

    public Film() {

    }

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
