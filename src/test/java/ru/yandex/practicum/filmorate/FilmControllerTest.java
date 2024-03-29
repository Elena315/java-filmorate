package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {
    private Film film;
    private FilmController filmController;
    private LikeStorage likeStorage;

    @BeforeEach
    public void beforeEach() {
        FilmStorage filmStorage = new InMemoryFilmStorage();
        UserStorage userStorage = new InMemoryUserStorage();

        filmController = new FilmController(new FilmService(filmStorage, userStorage, null));
        film = Film.builder()
                .name("Breakfast at Tiffany's")
                .description("American romantic comedy film directed by Blake Edwards, written by George Axelrod," +
                        " adapted from Truman Capote's 1958 novella of the same name.")
                .releaseDate(LocalDate.of(1961, 10, 5))
                .duration(114)
                .build();
    }

    // проверка контроллера при корректных атрибутах фильма
    @Test
    void shouldAddFilmWhenAllAttributeCorrect() {
        Film film1 = filmController.create(film);
        assertEquals(film, film1, "Переданный и полученный фильмы должны совпадать");
        assertEquals(1, filmController.getFilms().size(), "В списке должен быть один фильм");
    }

    // проверка контроллера при "пустом" названии у фильма
    @Test
    void shouldNoAddFilmWhenFilmNameIsEmpty() {
        film.setName("");
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }

    // проверка контроллера, когда максимальная длина описания больше 200 символов
    @Test
    void shouldNoAddFilmWhenFilmDescriptionMoreThan200Symbols() {
        film.setDescription(film.getDescription() + film.getDescription()); // длина описания 286 символов
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }

    // проверка контроллера, когда у фильма нет описания
    @Test
    void shouldNoAddFilmWhenFilmDescriptionIsEmpty() {
        film.setDescription("");
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }

    // проверка контроллера, когда дата релиза фильма раньше 28-12-1895
    @Test
    void shouldNoAddFilmWhenFilmReleaseDateIsBefore28121895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }

    // проверка контроллера, когда продолжительность фильма равна нулю
    @Test
    void shouldNoAddFilmWhenFilmDurationIsZero() {
        film.setDuration(0);
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }

    // проверка контроллера, когда продолжительность фильма отрицательная
    @Test
    void shouldNoAddFilmWhenFilmDurationIsNegative() {
        film.setDuration(-1);
        assertThrows(ValidationException.class, () -> filmController.create(film));
        assertEquals(0, filmController.getFilms().size(), "Список фильмов должен быть пустым");
    }
}