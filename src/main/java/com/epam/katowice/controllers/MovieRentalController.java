package com.epam.katowice.controllers;

import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.dto.FilmDto;
import com.epam.katowice.entities.Film;
import com.epam.katowice.services.ActorService;
import com.epam.katowice.services.CategoryService;
import com.epam.katowice.services.FilmService;
import com.epam.katowice.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import wrappers.PageWrapper;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by Wojciech_Soltys on 08.08.2016.
 */

@Controller
public class MovieRentalController extends WebMvcConfigurerAdapter {

    @Autowired
    private FilmService filmService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private ActorService actorService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @RequestMapping("/")
    public String getFilmsCount(Model model) {
        model.addAttribute("movieCount", filmService.getFilmsCount());
        return "index";
    }

    @RequestMapping(value = "/movies")
    public String getFilms(Model model, Pageable pageable, Filters filters, @RequestParam Map<String,String> allRequestParams) {
        PageWrapper<FilmDto> page = new PageWrapper<>(filmService.getByPredicate(filters, pageable),
                "/movies" + generateSearchLink(allRequestParams));

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

    @RequestMapping("/movie/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        Film film = filmService.findById(id);
        model.addAttribute("film", film);
        return "details";
    }

    @RequestMapping("/viewAddMovie")
    public String view(Model model) {
        model.addAttribute("movie", new Film());
        prepareDictionaries(model);
        return "addMovie";
    }

    private void prepareDictionaries(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("actors", actorService.findAll());
    }

    @RequestMapping("/addMovie")
    public String addMovie(Model model, @Valid FilmDto film, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("movie", film);
            prepareDictionaries(model);
            return "addMovie";
        }

        FilmDto filmOutput = filmService.save(film);
        model.addAttribute("movie", filmOutput);
        prepareDictionaries(model);
        return "redirect:viewAddMovie";
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