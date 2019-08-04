package net.mignemi.portfolio.repository;

import net.mignemi.portfolio.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}