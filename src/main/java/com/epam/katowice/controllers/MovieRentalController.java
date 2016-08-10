package com.epam.katowice.controllers;

import com.epam.katowice.dao.FilmRepository;
import com.epam.katowice.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}