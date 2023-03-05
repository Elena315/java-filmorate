package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FilmServiceTest {
    @Autowired
    private FilmService service;

    @Test
    void shouldAddWhenAddValidFilmData() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Correct description.");
        film.setReleaseDate(LocalDate.of(1895,12,29));
        film.setDuration(100L);

        Film addedFilm = service.createFilm(film);
        assertNotEquals(0, addedFilm.getId());
    }

    @Test
    void shouldThrowExceptionWhenAddFailedFilmNameEmpty() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Correct description.");
        film.setReleaseDate(LocalDate.of(1895,12,29));
        film.setDuration(100L);

        ValidationException ex = assertThrows(ValidationException.class, () -> service.createFilm(film));
        assertEquals("Ошибка валидации Фильма", ex.getMessage());
    }


    @Test
    void shouldThrowExceptionWhenAddFailedFilmNameBlank() {
        Film film = new Film();
        film.setName("  ");
        film.setDescription("Correct description");
        film.setReleaseDate(LocalDate.of(1895,12,28));
        film.setDuration(100L);

        ValidationException ex = assertThrows(ValidationException.class, () -> service.createFilm(film));
        assertEquals("Ошибка валидации Фильма", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAddFailedFilmDuration() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Correct description");
        film.setReleaseDate(LocalDate.of(1995,5,26));
        film.setDuration(-100L);

        ValidationException ex = assertThrows(ValidationException.class, () -> service.createFilm(film));
        assertEquals("Ошибка валидации Фильма", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAddFailedFilmReleaseDate() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Correct description");
        film.setReleaseDate(LocalDate.of(1895,12,27));
        film.setDuration(100L);

        ValidationException ex = assertThrows(ValidationException.class, () -> service.createFilm(film));
        assertEquals("Дата релиза не может быть раньше 1895-12-28", ex.getMessage());
    }

    @Test
    void shouldAddWhenAddValidFilmReleaseDateBoundary() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Correct description.");
        film.setReleaseDate(LocalDate.of(1895,12,28));
        film.setDuration(100L);

        Film addedFilm = service.createFilm(film);
        assertNotEquals(0, addedFilm.getId());
    }

    @Test
    void shouldThrowExceptionWhenAddFailedFilmDescription() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Failed description. Failed description. Failed description. Failed description. " +
                "Failed description. Failed description. Failed description. Failed description. " +
                "Failed description. Failed description. F");
        film.setReleaseDate(LocalDate.of(1995,5,26));
        film.setDuration(100L);

        ValidationException ex = assertThrows(ValidationException.class, () -> service.createFilm(film));
        assertEquals("Ошибка валидации Фильма", ex.getMessage());
    }

    @Test
    void shouldAddWhenAddFilmDescriptionBoundary() {
        Film film = new Film();
        film.setName("Correct Name");
        film.setDescription("Correct description. Correct description. Correct description. Correct description. " +
                "Correct description. Correct description. Correct description. Correct description. " +
                "Correct description. Correct des");
        film.setReleaseDate(LocalDate.of(1995,5,26));
        film.setDuration(100L);

        Film addedFilm = service.createFilm(film);
        assertNotEquals(0, addedFilm.getId());
    }
}