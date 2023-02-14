package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @PositiveOrZero(message = "id can not be negative")
    private int id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Digits(integer = 2_147_483_647, fraction = 0) @Positive
    @PositiveOrZero(message = "duration can not be negative")
    private long duration;

}
