package net.mignemi.portfolio.controller;

import net.mignemi.portfolio.model.Film;
import net.mignemi.portfolio.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/film")
public class Controller {

    @Autowired
    FilmRepository filmRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveFilm(@RequestBody Film film) {
        filmRepository.save(film);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

}
