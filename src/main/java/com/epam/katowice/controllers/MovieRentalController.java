package com.epam.katowice.controllers;

import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.Film;
import com.epam.katowice.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wrappers.PageWrapper;

import java.util.List;

/**
 * Created by Wojciech_Soltys on 08.08.2016.
 */

@Controller
public class MovieRentalController {

    @Autowired
    private FilmService filmService;

    @RequestMapping("/")
    public String getFilmsCount(Model model) {
        model.addAttribute("movieCount", filmService.getFilmsCount());
        return "index";
    }

    @RequestMapping(value = "/movies")
    public String getFilms(Model model, Pageable pageable) {
        PageWrapper<Film> page = new PageWrapper<>(filmService.getPageOfFilms(pageable), "/movies");
        model.addAttribute("page", page);
        return "films";
    }
}