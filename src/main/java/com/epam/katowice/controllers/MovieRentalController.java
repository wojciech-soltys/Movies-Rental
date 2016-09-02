package com.epam.katowice.controllers;

import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.entities.Film;
import com.epam.katowice.services.CategoryService;
import com.epam.katowice.services.FilmService;
import com.epam.katowice.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wrappers.PageWrapper;

import java.util.Map;

/**
 * Created by Wojciech_Soltys on 08.08.2016.
 */

@Controller
public class MovieRentalController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LanguageService languageService;

    @RequestMapping("/")
    public String getFilmsCount(Model model) {
        model.addAttribute("movieCount", filmService.getFilmsCount());
        return "index";
    }

    @RequestMapping(value = "/movies")
    public String getFilms(Model model, Pageable pageable, Filters filters, @RequestParam Map<String,String> allRequestParams) {
        PageWrapper<Film> page = new PageWrapper<Film>(filmService.getByPredicate(filters, pageable), "/movies" + generateSearchLink(allRequestParams));

        model.addAttribute("page", page);
        if(page.getPage().getSort() != null) {
            model.addAttribute("sort", page.getPage().getSort().toString().replaceAll(": ", ","));
        } else {
            model.addAttribute("sort", "");
        }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("languages", languageService.findAll());
        return "films";
    }

    private String generateSearchLink(Map<String,String> allRequestParams) {
        String link = "?";
            for (Map.Entry<String, String> entry : allRequestParams.entrySet())
            {
                if(!entry.getKey().equals("page") && !entry.getKey().equals("size") && !entry.getKey().equals("sort")) {
                    link += entry.getKey() + "=" + entry.getValue();
                    link += "&";
                }
            }

        return link;
    }
}