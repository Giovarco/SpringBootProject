package net.mignemi.portfolio.repository;

import net.mignemi.portfolio.model.Design;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignRepository extends JpaRepository<Design, Long> {}