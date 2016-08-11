package com.epam.katowice.controllers;

import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/movies")
    public String getFilms(Model model) {
        List<FilmDto> filmsList = filmService.getAllFilms();
        model.addAttribute("films", filmsList);
        return "films";
    }
}