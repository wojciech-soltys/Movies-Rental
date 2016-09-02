package com.epam.katowice.dao;

import com.epam.katowice.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Wojciech_Soltys on 09.08.2016.
 */
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor {
    Film findById(Long id);
}
