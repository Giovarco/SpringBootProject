package net.mignemi.portfolio.repository;

import net.mignemi.portfolio.model.Design;
import net.mignemi.portfolio.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {}