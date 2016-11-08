package com.epam.katowice.controllers;

import com.epam.katowice.controllers.parameters.Filters;
import com.epam.katowice.dto.FilmForm;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wrappers.PageWrapper;

import java.util.Map;

/**
 * Created by Wojciech_Soltys on 08.08.2016.
 */

@Controller
public class MovieRentalController {

    static final String INDEX_ENDPOINT = "/";
    static final String NEW_MOVIE_ENDPOINT = "/addMovie";
    static final String DETAILS_ENDPOINT = "/details";
    static final String MOVIES_LIST_ENDPOINT = "/movies";

    static final String REDIRECT_PREFIX = "redirect:";
    static final String INDEX_VIEW = "index";
    static final String NEW_MOVIE_VIEW = "addMovie";
    static final String DETAILS_VIEW = "details";
    static final String MOVIES_LIST_VIEW = "films";

    static final String MOVIE_COUNT_PARAMETER = "movieCount";
    static final String PAGE_PARAMETER = "page";
    static final String PAGE_SIZE_PARAMETER = "size";
    static final String PAGE_SORT_PARAMETER = "sort";
    static final String ID_PARAMETER = "id";
    static final String FILM_PARAMETER = "film";
    static final String CATEGORIES_PARAMETER = "categories";
    static final String LANGUAGES_PARAMETER = "languages";
    static final String ACTORS_PARAMETER = "actors";


    @RequestMapping(INDEX_ENDPOINT)
    public String getFilmsCount(Model model) {
        model.addAttribute(MOVIE_COUNT_PARAMETER, filmService.getFilmsCount());
        return INDEX_VIEW;
    }

    @RequestMapping(MOVIES_LIST_ENDPOINT)
    public String getFilms(Model model, Pageable pageable, Filters filters, @RequestParam Map<String,String> allRequestParams) {
        PageWrapper<FilmForm> page = new PageWrapper<>(filmService.getByPredicate(filters, pageable),
                MOVIES_LIST_ENDPOINT + generateSearchLink(allRequestParams));

        model.addAttribute(PAGE_PARAMETER, page);
        addSortParameter(model, page);
        prepareDictionaries(model);
        return MOVIES_LIST_VIEW;
    }

    @RequestMapping(DETAILS_ENDPOINT)
    public String view(@RequestParam Long id, Model model) {
        Film film = filmService.findById(id);
        model.addAttribute(FILM_PARAMETER, film);
        return DETAILS_VIEW;
    }

    @RequestMapping(NEW_MOVIE_ENDPOINT)
    public String view(Model model, FilmForm filmForm) {
        prepareDictionaries(model);
        return NEW_MOVIE_VIEW;
    }


    @RequestMapping(value = NEW_MOVIE_ENDPOINT, method = RequestMethod.POST)
    public String addMovie(Model model, @Validated FilmForm filmForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            prepareDictionaries(model);
            return NEW_MOVIE_VIEW;
        }

        FilmForm filmOutput = filmService.save(filmForm);
        prepareDictionaries(model);
        redirectAttributes.addAttribute(ID_PARAMETER, filmOutput.getId());
        return REDIRECT_PREFIX + DETAILS_VIEW;
    }

    private void prepareDictionaries(Model model) {
        model.addAttribute(CATEGORIES_PARAMETER, categoryService.findAll());
        model.addAttribute(LANGUAGES_PARAMETER, languageService.findAll());
        model.addAttribute(ACTORS_PARAMETER, actorService.findAll());
    }

    private String generateSearchLink(Map<String,String> allRequestParams) {
        String link = "?";
            for (Map.Entry<String, String> entry : allRequestParams.entrySet())
            {
                if(!entry.getKey().equals(PAGE_PARAMETER) && !entry.getKey().equals(PAGE_SIZE_PARAMETER)
                        && !entry.getKey().equals(PAGE_SORT_PARAMETER)) {
                    link += entry.getKey() + "=" + entry.getValue();
                    link += "&";
                }
            }

        return link;
    }

    private void addSortParameter(Model model, PageWrapper<FilmForm> page) {
        if(page.getPage().getSort() != null) {
            model.addAttribute(PAGE_SORT_PARAMETER, page.getPage().getSort().toString().replaceAll(": ", ","));
        } else {
            model.addAttribute(PAGE_SORT_PARAMETER, "");
        }
    }

    @Autowired
    private FilmService filmService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private ActorService actorService;
}